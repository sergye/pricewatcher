package pricewatcher.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import pricewatcher.app.component.DefaultUserProperties;
import pricewatcher.app.dto.pricedate.PriceDateUpdateDTO;
import pricewatcher.app.exception.ResourceNotFoundException;
import pricewatcher.app.model.PriceDate;
import pricewatcher.app.repository.PriceDateRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pricewatcher.app.util.ModelGenerator;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pricewatcher.app.service.PriceDateService.FORMATTER;


@SpringBootTest
@AutoConfigureMockMvc

class PriceDateControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PriceDateRepository priceDateRepository;

    @Autowired
    private DefaultUserProperties defaultUserProperties;

    private JwtRequestPostProcessor token;

    private PriceDate priceDate;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .apply(springSecurity())
                .build();

        token = jwt().jwt(builder -> builder.subject(defaultUserProperties.getEmail()));
        priceDate = Instancio.of(modelGenerator.getPriceDateModel()).create();
        priceDateRepository.save(priceDate);

    }

    @Test
    public void testUnauthenticatedAccess() throws Exception {
        mockMvc.perform(get("/api/dates"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/api/dates").with(token))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }

    @Test
    public void testShow() throws Exception {

        var result = mockMvc.perform(get("/api/dates/" + priceDate.getId()).with(token))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();

        assertThatJson(body).and(
                a -> a.node("id").isEqualTo(priceDate.getId()),
                a -> a.node("priceDate").isEqualTo(priceDate.getPriceDate().format(FORMATTER))
        );
    }

    @Test
    public void testCreate() throws Exception {
        var priceDateData = Instancio.of(modelGenerator.getPriceDateModel()).create();

        var request = post("/api/dates")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(priceDateData));

        mockMvc.perform(request)
                .andExpect(status().isCreated());

        var pricedates = priceDateRepository.findAll();

        var priceDate = priceDateRepository.findByPriceDate(priceDateData.getPriceDate())
                .orElseThrow(() -> new ResourceNotFoundException("PriceDate not found"));

        assertThat(priceDate.getPriceDate()).isEqualTo((priceDateData.getPriceDate()));
    }

    @Test
    public void testUpdate() throws Exception {

        PriceDateUpdateDTO priceDateData = new PriceDateUpdateDTO();
        var date = LocalDateTime.parse("2022-06-14 10:00:00", FORMATTER);
        priceDateData.setPriceDate(JsonNullable.of(date));

        var request = put("/api/dates/" + priceDate.getId())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(priceDateData));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var updatedPriceDate = priceDateRepository.findById(priceDate.getId())
                .orElseThrow(() -> new ResourceNotFoundException("PriceDate not found"));

        assertThat(updatedPriceDate.getPriceDate()).isEqualTo((priceDateData.getPriceDate().get()));
    }

    @Test
    public void testDelete() throws Exception {

        mockMvc.perform(delete("/api/dates/" + priceDate.getId()).with(token))
                .andExpect(status().isNoContent());

        assertThat(priceDateRepository.findById(priceDate.getId())).isEmpty();
    }

    @Test
    public void testDefaultPriceDates() {
        assertThat(priceDateRepository.findById(1L)).isPresent();
    }
}
