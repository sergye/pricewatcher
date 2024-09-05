package pricewatcher.app.controller;

import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;

import pricewatcher.app.component.DefaultBrandProperties;
import pricewatcher.app.dto.BrandUpdateDTO;
import pricewatcher.app.exception.ResourceNotFoundException;
import pricewatcher.app.model.Brand;
import pricewatcher.app.repository.BrandRepository;
import pricewatcher.app.util.ModelGenerator;

import net.datafaker.Faker;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTest {

    private static final Faker FAKER = new Faker();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private DefaultBrandProperties defaultBrandProperties;

    private Brand brand;

    @BeforeEach
    public void setUp() {
        brand = Instancio.of(modelGenerator.getBrandModel()).create();
    }


    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/welcome"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Price Watcher!");
    }

    @Test
    public void testIndex() throws Exception {
        brandRepository.save(brand);
        var result = mockMvc.perform(get("/api/brands"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    @Test
    public void testShow() throws Exception {
        brandRepository.save(brand);

        var result = mockMvc.perform(get("/api/brands/" + brand.getId()))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();

        assertThatJson(body).and(
                a -> a.node("name").isEqualTo(brand.getName())
        );
    }

    @Test
    public void testCreate() throws Exception {
        var brandData = Instancio.of(modelGenerator.getBrandModel()).create();

        mockMvc.perform(post("/api/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(brandData)))
                .andExpect(status().isCreated());

        var brand = brandRepository.findByName(brandData.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

        assertThat(brand.getName()).isEqualTo((brandData.getName()));
    }

    @Test
    public void testUpdate() throws Exception {
        brandRepository.save(brand);

        BrandUpdateDTO brandData = new BrandUpdateDTO();
        brandData.setName(FAKER.name().name());

        var request = put("/api/brands/" + brand.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(brandData));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var updatedBrand = brandRepository.findById(brand.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

        assertThat(updatedBrand.getName()).isEqualTo((brandData.getName()));
    }

    @Test
    public void testDelete() throws Exception {
        brandRepository.save(brand);

        mockMvc.perform(delete("/api/brands/" + brand.getId()))
                .andExpect(status().isNoContent());

        assertThat(brandRepository.findById(brand.getId())).isEmpty();
    }

    @Test
    public void testCreateDefaultBrand() {
        String name = defaultBrandProperties.getName();
        Optional<Brand> brandOptional = brandRepository.findByName(name);

        assertThat(brandOptional).isPresent();
        Brand brand = brandOptional.get();

        assertThat(brand.getName()).isEqualTo(defaultBrandProperties.getName());
    }
}
