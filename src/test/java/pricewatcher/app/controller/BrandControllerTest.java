package pricewatcher.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import pricewatcher.app.component.DefaultUserProperties;
import pricewatcher.app.dto.brand.BrandUpdateDTO;
import pricewatcher.app.exception.ResourceNotFoundException;
import pricewatcher.app.model.Brand;
import pricewatcher.app.model.User;
import pricewatcher.app.repository.BrandRepository;
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

class BrandControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private DefaultUserProperties defaultUserProperties;

    private JwtRequestPostProcessor token;

    private User user;
    private Brand brand;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .apply(springSecurity())
                .build();

        token = jwt().jwt(builder -> builder.subject(defaultUserProperties.getEmail()));
        brand = Instancio.of(modelGenerator.getBrandModel()).create();
        brandRepository.save(brand);
    }

    @Test
    public void testUnauthenticatedAccess() throws Exception {
        mockMvc.perform(get("/api/brands"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/api/brands").with(token))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }

    @Test
    public void testShow() throws Exception {

        var result = mockMvc.perform(get("/api/brands/" + brand.getId()).with(token))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();

        assertThatJson(body).and(
                a -> a.node("id").isEqualTo(brand.getId()),
                a -> a.node("name").isEqualTo(brand.getName())
        );
    }

    @Test
    public void testCreate() throws Exception {
        var brandData = Instancio.of(modelGenerator.getBrandModel()).create();

        var request = post("/api/brands")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(brandData));

        mockMvc.perform(request)
                .andExpect(status().isCreated());

        var brand = brandRepository.findByName(brandData.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

        assertThat(brand.getName()).isEqualTo((brandData.getName()));
    }

    @Test
    public void testUpdate() throws Exception {

        BrandUpdateDTO brandData = new BrandUpdateDTO();
        brandData.setName(JsonNullable.of("brand-name"));

        var request = put("/api/brands/" + brand.getId())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(brandData));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var updatedBrand = brandRepository.findById(brand.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

        assertThat(updatedBrand.getName()).isEqualTo((brandData.getName().get()));
    }

    @Test
    public void testDelete() throws Exception {

        mockMvc.perform(delete("/api/brands/" + brand.getId()).with(token))
                .andExpect(status().isNoContent());

        assertThat(brandRepository.findById(brand.getId())).isEmpty();
    }

    @Test
    public void testDefaultBrands() {
        assertThat(brandRepository.findByName("Zara")).isPresent();
        assertThat(brandRepository.findByName("Mango")).isPresent();
    }
}
