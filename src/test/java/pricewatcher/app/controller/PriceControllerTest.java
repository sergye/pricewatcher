package pricewatcher.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import pricewatcher.app.component.DefaultUserProperties;
import pricewatcher.app.dto.price.PriceCreateDTO;
import pricewatcher.app.dto.price.PriceUpdateDTO;
import pricewatcher.app.exception.ResourceNotFoundException;
import pricewatcher.app.mapper.PriceMapper;
import pricewatcher.app.model.PriceList;
import pricewatcher.app.model.Product;
import pricewatcher.app.model.Price;
import pricewatcher.app.model.Brand;
import pricewatcher.app.repository.PriceListRepository;
import pricewatcher.app.repository.ProductRepository;
import pricewatcher.app.repository.PriceRepository;
import pricewatcher.app.repository.BrandRepository;
import pricewatcher.app.repository.UserRepository;
import pricewatcher.app.util.ModelGenerator;
import net.datafaker.Faker;
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

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pricewatcher.app.component.DataInitializer.AFTERNOON;
import static pricewatcher.app.component.DataInitializer.ALL_YEAR;
import static pricewatcher.app.component.DataInitializer.EUR;
import static pricewatcher.app.component.DataInitializer.EVENING;
import static pricewatcher.app.component.DataInitializer.HAT;
import static pricewatcher.app.component.DataInitializer.MANGO;
import static pricewatcher.app.component.DataInitializer.MORNING;
import static pricewatcher.app.component.DataInitializer.SHIRT;
import static pricewatcher.app.component.DataInitializer.ZARA;
import static pricewatcher.app.service.PriceDateService.FORMATTER;


@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerTest {

    private static final Faker FAKER = new Faker();

    private static final String DATE_1 = "2020-06-14 10:00:00";
    private static final String DATE_2 = "2020-06-14 16:00:00";
    private static final String DATE_3 = "2020-06-14 21:00:00";
    private static final String DATE_4 = "2020-06-15 10:00:00";
    private static final String DATE_5 = "2020-06-16 21:00:00";

    @Autowired
    private ProductRepository labelRepository;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PriceMapper priceMapper;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DefaultUserProperties defaultUserProperties;

    private JwtRequestPostProcessor token;

    private Price price;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .apply(springSecurity())
                .build();

        token = jwt().jwt(builder -> builder.subject(defaultUserProperties.getEmail()));

        Product product = productRepository.findByName(SHIRT)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Brand brand  = brandRepository.findByName(ZARA)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

        PriceList priceList  = priceListRepository.findByName(ALL_YEAR)
                .orElseThrow(() -> new ResourceNotFoundException("Price list not found"));

        price = Instancio.of(modelGenerator.getPriceModel()).create();

        price.setProduct(product);
        price.setBrand(brand);
        price.setPriceList(priceList);

        priceRepository.save(price);

        var items = priceRepository.findAll();
    }

    @Test
    public void testUnauthenticatedAccess() throws Exception {
        mockMvc.perform(get("/api/prices"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/api/prices").with(token))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }

    @Test
    public void testFilter1() throws Exception {
        var result = mockMvc.perform(get("/api/prices").with(token)
                        .param("product", SHIRT)
                        .param("brand", ZARA)
                        .param("priceDate", DATE_1))
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
                a -> a.node("[0].priceList").isEqualTo(ALL_YEAR),
                a -> a.node("[0].price").isEqualTo("35.50")
        );
    }

    @Test
    public void testFilter2() throws Exception {
        var result = mockMvc.perform(get("/api/prices").with(token)
                        .param("product", SHIRT)
                        .param("brand", ZARA)
                        .param("priceDate", DATE_2))
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
                a -> a.node("[0].priceList").isEqualTo(ALL_YEAR),
                a -> a.node("[0].price").isEqualTo("35.50"),
                a -> a.node("[1].priceList").isEqualTo(AFTERNOON),
                a -> a.node("[1].price").isEqualTo("25.45")
        );
    }

    @Test
    public void testFilter3() throws Exception {
        var result = mockMvc.perform(get("/api/prices").with(token)
                        .param("product", SHIRT)
                        .param("brand", ZARA)
                        .param("priceDate", DATE_3))
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
                a -> a.node("[0].priceList").isEqualTo(ALL_YEAR),
                a -> a.node("[0].price").isEqualTo("35.50")
        );
    }

    @Test
    public void testFilter4() throws Exception {
        var result = mockMvc.perform(get("/api/prices").with(token)
                        .param("product", SHIRT)
                        .param("brand", ZARA)
                        .param("priceDate", DATE_4))
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
                a -> a.node("[0].priceList").isEqualTo(ALL_YEAR),
                a -> a.node("[0].price").isEqualTo("35.50"),
                a -> a.node("[1].priceList").isEqualTo(MORNING),
                a -> a.node("[1].price").isEqualTo("30.50")
        );
    }

    @Test
    public void testFilter5() throws Exception {
        var result = mockMvc.perform(get("/api/prices").with(token)
                        .param("product", SHIRT)
                        .param("brand", ZARA)
                        .param("priceDate", DATE_5))
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
                a -> a.node("[0].priceList").isEqualTo(ALL_YEAR),
                a -> a.node("[0].price").isEqualTo("35.50"),
                a -> a.node("[1].priceList").isEqualTo(EVENING),
                a -> a.node("[1].price").isEqualTo("38.95")
        );
    }

    @Test
    public void testShow() throws Exception {

        var result = mockMvc.perform(get("/api/prices/" + price.getId()).with(token))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();

        assertThatJson(body).and(
                a -> a.node("priority").isEqualTo(price.getPriority()),
                a -> a.node("price").isEqualTo(price.getPrice())
        );
    }

    @Test
    public void testCreate() throws Exception {
        PriceCreateDTO priceCreateDTO = new PriceCreateDTO();

        priceCreateDTO.setProduct(HAT);
        priceCreateDTO.setBrand(MANGO);
        priceCreateDTO.setPriceList(ALL_YEAR);
        priceCreateDTO.setStartDate(LocalDateTime.parse("2021-06-14 10:00:00", FORMATTER));
        priceCreateDTO.setEndDate(LocalDateTime.parse("2021-12-31 23:59:59", FORMATTER));
        priceCreateDTO.setPrice(BigDecimal.valueOf(150.50));
        priceCreateDTO.setCurr(EUR);
        priceCreateDTO.setPriority(0L);

        var request = post("/api/prices")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(priceCreateDTO));

        mockMvc.perform(request)
                .andExpect(status().isCreated());

        Optional<Price> priceOptional = priceRepository.findByPrice(priceCreateDTO.getPrice());
        assertThat(priceOptional).isPresent();

        Price price = priceOptional.get();
        assertThat(price.getBrand().getName()).isEqualTo(priceCreateDTO.getBrand());
    }

    @Test
    public void testUpdate() throws Exception {

        PriceUpdateDTO priceData = new PriceUpdateDTO();
        priceData.setBrand(JsonNullable.of("MANGO"));
        priceData.setProduct(JsonNullable.of("Hat"));

        var request = put("/api/prices/" + price.getId())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(priceData));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        Optional<Price> priceOptional = priceRepository.findById(price.getId());
        assertThat(priceOptional).isPresent();

        Price updatedPrice = priceOptional.get();

        assertThat(updatedPrice.getBrand().getName()).isEqualTo(priceData.getBrand().get());
        assertThat(updatedPrice.getProduct().getName()).isEqualTo(priceData.getProduct().get());
    }

    @Test
    public void testDelete() throws Exception {

        mockMvc.perform(delete("/api/prices/" + price.getId()).with(token))
                .andExpect(status().isNoContent());

        assertThat(priceRepository.findById(price.getId())).isEmpty();
    }
}
