package pricewatcher.app.component;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pricewatcher.app.dto.brand.BrandCreateDTO;
import pricewatcher.app.dto.price.PriceCreateDTO;
import pricewatcher.app.dto.pricedate.PriceDateCreateDTO;
import pricewatcher.app.dto.pricelist.PriceListCreateDTO;
import pricewatcher.app.dto.product.ProductCreateDTO;
import pricewatcher.app.dto.user.UserCreateDTO;
import pricewatcher.app.mapper.BrandMapper;
import pricewatcher.app.mapper.PriceDateMapper;
import pricewatcher.app.mapper.PriceListMapper;
import pricewatcher.app.mapper.PriceMapper;
import pricewatcher.app.mapper.ProductMapper;
import pricewatcher.app.mapper.UserMapper;
import pricewatcher.app.model.User;
import pricewatcher.app.repository.BrandRepository;
import pricewatcher.app.repository.PriceDateRepository;
import pricewatcher.app.repository.PriceListRepository;
import pricewatcher.app.repository.PriceRepository;
import pricewatcher.app.repository.ProductRepository;
import pricewatcher.app.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Currency EUR = Currency.getInstance("EUR");
    private static final String SHIRT = "Shirt";
    private static final String ZARA = "Zara";
    private static final String ALL_YEAR = "All-year";
    private static final String AFTERNOON = "Afternoon";
    private static final String MORNING = "Morning";
    private static final String EVENING = "Evening";

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private PriceDateRepository priceDateRepository;

    @Autowired
    private PriceDateMapper priceDateMapper;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private PriceMapper priceMapper;

    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private PriceListMapper priceListMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DefaultUserProperties defaultUserProperties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createDefaultUser();
        createDefaultBrands();
        createDefaultProducts();
        createDefaultPriceDates();
        createDefaultPriceLists();
        createDefaultPrices();
    }

    private void createDefaultUser() {
        if (userRepository.findAll().isEmpty()) {
            String email = defaultUserProperties.getEmail();

            if (userRepository.findByEmail(email).isEmpty()) {
                UserCreateDTO userData = new UserCreateDTO();

                userData.setFirstName("Jorge");
                userData.setLastName("Lorenzo");
                userData.setEmail(email);

                String password = defaultUserProperties.getPassword();
                String passwordDigest = passwordEncoder.encode(password);

                userData.setPassword(passwordDigest);

                User user = userMapper.map(userData);
                userRepository.save(user);
            }
        }
    }

    private void createDefaultBrands() {
        if (brandRepository.findAll().isEmpty()) {
            BrandCreateDTO allYearBrandDTO = new BrandCreateDTO();
            allYearBrandDTO.setName(ZARA);

            BrandCreateDTO afternoonBrandDTO = new BrandCreateDTO();
            afternoonBrandDTO.setName("Mango");

            brandRepository.save(brandMapper.map(allYearBrandDTO));
            brandRepository.save(brandMapper.map(afternoonBrandDTO));
        }
    }

    private void createDefaultProducts() {
        if (productRepository.findAll().isEmpty()) {
            ProductCreateDTO shirtProductDTO = new ProductCreateDTO();
            shirtProductDTO.setName(SHIRT);

            ProductCreateDTO hatProductDTO = new ProductCreateDTO();
            hatProductDTO.setName("Hat");

            productRepository.save(productMapper.map(shirtProductDTO));
            productRepository.save(productMapper.map(hatProductDTO));
        }
    }

    private void createDefaultPriceDates() {
        if (priceDateRepository.findAll().isEmpty()) {
            PriceDateCreateDTO date1CreateDTO = new PriceDateCreateDTO();
            var date1 = LocalDateTime.parse("2020-06-14 10:00:00", FORMATTER);
            date1CreateDTO.setPriceDate(date1);

            PriceDateCreateDTO date2CreateDTO = new PriceDateCreateDTO();
            var date2 = LocalDateTime.parse("2020-06-14 16:00:00", FORMATTER);
            date2CreateDTO.setPriceDate(date2);

            priceDateRepository.save(priceDateMapper.map(date1CreateDTO));
            priceDateRepository.save(priceDateMapper.map(date2CreateDTO));
        }
    }

    private void createDefaultPriceLists() {
        if (priceListRepository.findAll().isEmpty()) {
            PriceListCreateDTO allYearPriceListDTO = new PriceListCreateDTO();
            allYearPriceListDTO.setName(ALL_YEAR);

            PriceListCreateDTO afternoonPriceListDTO = new PriceListCreateDTO();
            afternoonPriceListDTO.setName(AFTERNOON);

            PriceListCreateDTO morningPriceListDTO = new PriceListCreateDTO();
            morningPriceListDTO.setName(MORNING);

            PriceListCreateDTO eveningPriceListDTO = new PriceListCreateDTO();
            eveningPriceListDTO.setName(EVENING);

            priceListRepository.save(priceListMapper.map(allYearPriceListDTO));
            priceListRepository.save(priceListMapper.map(afternoonPriceListDTO));
            priceListRepository.save(priceListMapper.map(morningPriceListDTO));
            priceListRepository.save(priceListMapper.map(eveningPriceListDTO));
        }
    }

    private void createDefaultPrices() {
        if (priceRepository.findAll().isEmpty()) {
            PriceCreateDTO price1DTO = new PriceCreateDTO();
            price1DTO.setProduct(SHIRT);
            price1DTO.setBrand(ZARA);
            price1DTO.setPriceList(ALL_YEAR);
            price1DTO.setStartDate(LocalDateTime.parse("2020-06-14 10:00:00", FORMATTER));
            price1DTO.setEndDate(LocalDateTime.parse("2020-12-31 23:59:59", FORMATTER));
            price1DTO.setPrice(BigDecimal.valueOf(35.50));
            price1DTO.setCurr(EUR);
            price1DTO.setPriority(0L);

            PriceCreateDTO price2DTO = new PriceCreateDTO();
            price2DTO.setProduct(SHIRT);
            price2DTO.setBrand(ZARA);
            price2DTO.setPriceList(AFTERNOON);
            price2DTO.setStartDate(LocalDateTime.parse("2020-06-14 15:00:00", FORMATTER));
            price2DTO.setEndDate(LocalDateTime.parse("2020-06-14 18:30:00", FORMATTER));
            price2DTO.setPrice(BigDecimal.valueOf(25.45));
            price2DTO.setCurr(EUR);
            price2DTO.setPriority(1L);

            PriceCreateDTO price3DTO = new PriceCreateDTO();
            price3DTO.setProduct(SHIRT);
            price3DTO.setBrand(ZARA);
            price3DTO.setPriceList(MORNING);
            price3DTO.setStartDate(LocalDateTime.parse("2020-06-15 00:00:00", FORMATTER));
            price3DTO.setEndDate(LocalDateTime.parse("2020-06-15 11:00:00", FORMATTER));
            price3DTO.setPrice(BigDecimal.valueOf(30.50));
            price3DTO.setCurr(EUR);
            price3DTO.setPriority(1L);

            PriceCreateDTO price4DTO = new PriceCreateDTO();
            price4DTO.setProduct(SHIRT);
            price4DTO.setBrand(ZARA);
            price4DTO.setPriceList(EVENING);
            price4DTO.setStartDate(LocalDateTime.parse("2020-06-15 16:00:00", FORMATTER));
            price4DTO.setEndDate(LocalDateTime.parse("2020-12-31 23:59:59", FORMATTER));
            price4DTO.setPrice(BigDecimal.valueOf(38.95));
            price4DTO.setCurr(EUR);
            price4DTO.setPriority(1L);

            priceRepository.save(priceMapper.map(price1DTO));
            priceRepository.save(priceMapper.map(price2DTO));
            priceRepository.save(priceMapper.map(price3DTO));
            priceRepository.save(priceMapper.map(price4DTO));
        }
    }
}
