package pricewatcher.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import pricewatcher.app.component.DefaultUserProperties;
import pricewatcher.app.dto.product.ProductUpdateDTO;
import pricewatcher.app.exception.ResourceNotFoundException;
import pricewatcher.app.model.Product;
import pricewatcher.app.repository.ProductRepository;
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

class ProductControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DefaultUserProperties defaultUserProperties;

    private JwtRequestPostProcessor token;

    private Product product;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .apply(springSecurity())
                .build();

        token = jwt().jwt(builder -> builder.subject(defaultUserProperties.getEmail()));
        product = Instancio.of(modelGenerator.getProductModel()).create();
        productRepository.save(product);
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

        var result = mockMvc.perform(get("/api/products/" + product.getId()).with(token))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();

        assertThatJson(body).and(
                a -> a.node("id").isEqualTo(product.getId()),
                a -> a.node("name").isEqualTo(product.getName())
        );
    }

    @Test
    public void testCreate() throws Exception {
        var productData = Instancio.of(modelGenerator.getProductModel()).create();

        var request = post("/api/products")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(productData));

        mockMvc.perform(request)
                .andExpect(status().isCreated());

        var product = productRepository.findByName(productData.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        assertThat(product.getName()).isEqualTo((productData.getName()));
    }

    @Test
    public void testUpdate() throws Exception {

        ProductUpdateDTO productData = new ProductUpdateDTO();
        productData.setName(JsonNullable.of("product-name"));

        var request = put("/api/products/" + product.getId())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(productData));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var updatedProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        assertThat(updatedProduct.getName()).isEqualTo((productData.getName().get()));
    }

    @Test
    public void testDelete() throws Exception {

        mockMvc.perform(delete("/api/products/" + product.getId()).with(token))
                .andExpect(status().isNoContent());

        assertThat(productRepository.findById(product.getId())).isEmpty();
    }

    @Test
    public void testDefaultProducts() {
        assertThat(productRepository.findByName("Shirt")).isPresent();
        assertThat(productRepository.findByName("Hat")).isPresent();
    }
}
