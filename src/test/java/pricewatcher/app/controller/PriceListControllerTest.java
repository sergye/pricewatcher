package pricewatcher.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import pricewatcher.app.component.DefaultUserProperties;
import pricewatcher.app.dto.pricelist.PriceListUpdateDTO;
import pricewatcher.app.exception.ResourceNotFoundException;
import pricewatcher.app.model.PriceList;
import pricewatcher.app.repository.PriceListRepository;
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

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc

class PriceListControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private DefaultUserProperties defaultUserProperties;

    private JwtRequestPostProcessor token;

    private PriceList priceList;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .apply(springSecurity())
                .build();

        token = jwt().jwt(builder -> builder.subject(defaultUserProperties.getEmail()));
        priceList = Instancio.of(modelGenerator.getPriceListModel()).create();
        priceListRepository.save(priceList);
    }

    @Test
    public void testUnauthenticatedAccess() throws Exception {
        mockMvc.perform(get("/api/pricelists"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/api/pricelists").with(token))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }

    @Test
    public void testShow() throws Exception {

        var result = mockMvc.perform(get("/api/pricelists/" + priceList.getId()).with(token))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();

        assertThatJson(body).and(
                a -> a.node("id").isEqualTo(priceList.getId()),
                a -> a.node("name").isEqualTo(priceList.getName())
        );
    }

    @Test
    public void testCreate() throws Exception {
        var priceListData = Instancio.of(modelGenerator.getPriceListModel()).create();

        var request = post("/api/pricelists")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(priceListData));

        mockMvc.perform(request)
                .andExpect(status().isCreated());

        var priceList = priceListRepository.findByName(priceListData.getName())
                .orElseThrow(() -> new ResourceNotFoundException("PriceList not found"));

        assertThat(priceList.getName()).isEqualTo((priceListData.getName()));
    }

    @Test
    public void testUpdate() throws Exception {

        PriceListUpdateDTO priceListData = new PriceListUpdateDTO();
        priceListData.setName(JsonNullable.of("priceList-name"));

        var request = put("/api/pricelists/" + priceList.getId())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(priceListData));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var updatedPriceList = priceListRepository.findById(priceList.getId())
                .orElseThrow(() -> new ResourceNotFoundException("PriceList not found"));

        assertThat(updatedPriceList.getName()).isEqualTo((priceListData.getName().get()));
    }

    @Test
    public void testDelete() throws Exception {

        mockMvc.perform(delete("/api/pricelists/" + priceList.getId()).with(token))
                .andExpect(status().isNoContent());

        assertThat(priceListRepository.findById(priceList.getId())).isEmpty();
    }

    @Test
    public void testDefaultPriceLists() {
        assertThat(priceListRepository.findByName("All-year")).isPresent();
        assertThat(priceListRepository.findByName("Afternoon")).isPresent();
        assertThat(priceListRepository.findByName("Morning")).isPresent();
        assertThat(priceListRepository.findByName("Evening")).isPresent();
    }
}
