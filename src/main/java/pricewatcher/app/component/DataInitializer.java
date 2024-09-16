package pricewatcher.app.component;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pricewatcher.app.dto.brand.BrandCreateDTO;
import pricewatcher.app.dto.pricedate.PriceDateCreateDTO;
import pricewatcher.app.dto.pricelist.PriceListCreateDTO;
import pricewatcher.app.dto.product.ProductCreateDTO;
import pricewatcher.app.dto.user.UserCreateDTO;
import pricewatcher.app.exception.ResourceNotFoundException;
import pricewatcher.app.mapper.BrandMapper;
import pricewatcher.app.mapper.PriceDateMapper;
import pricewatcher.app.mapper.PriceListMapper;
import pricewatcher.app.mapper.PriceMapper;
import pricewatcher.app.mapper.ProductMapper;
import pricewatcher.app.mapper.UserMapper;
import pricewatcher.app.model.Brand;
import pricewatcher.app.model.Price;
import pricewatcher.app.model.PriceList;
import pricewatcher.app.model.Product;
import pricewatcher.app.model.User;
import pricewatcher.app.repository.BrandRepository;
import pricewatcher.app.repository.PriceDateRepository;
import pricewatcher.app.repository.PriceListRepository;
import pricewatcher.app.repository.PriceRepository;
import pricewatcher.app.repository.ProductRepository;
import pricewatcher.app.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

import static pricewatcher.app.service.PriceDateService.FORMATTER;


@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {
    public static final String SHIRT = "Shirt";
    public static final String HAT = "Hat";
    public static final String ZARA = "ZARA";
    public static final String MANGO = "MANGO";
    public static final String ALL_YEAR = "All-year";
    public static final String AFTERNOON = "Afternoon";
    public static final String MORNING = "Morning";
    public static final String EVENING = "Evening";
    public static final Currency EUR = Currency.getInstance("EUR");
    public static final String PRICE_LIST_NOT_FOUND = "Price list not found";


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
    private List<Price> items;

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
            afternoonBrandDTO.setName(MANGO);

            brandRepository.save(brandMapper.map(allYearBrandDTO));
            brandRepository.save(brandMapper.map(afternoonBrandDTO));
        }
    }

    private void createDefaultProducts() {
        if (productRepository.findAll().isEmpty()) {
            ProductCreateDTO shirtProductDTO = new ProductCreateDTO();
            shirtProductDTO.setName(SHIRT);

            ProductCreateDTO hatProductDTO = new ProductCreateDTO();
            hatProductDTO.setName(HAT);

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

            PriceDateCreateDTO date3CreateDTO = new PriceDateCreateDTO();
            var date3 = LocalDateTime.parse("2020-06-14 21:00:00", FORMATTER);
            date3CreateDTO.setPriceDate(date3);

            PriceDateCreateDTO date4CreateDTO = new PriceDateCreateDTO();
            var date4 = LocalDateTime.parse("2020-06-15 10:00:00", FORMATTER);
            date4CreateDTO.setPriceDate(date4);

            PriceDateCreateDTO date5CreateDTO = new PriceDateCreateDTO();
            var date5 = LocalDateTime.parse("2020-06-16 21:00:00", FORMATTER);
            date5CreateDTO.setPriceDate(date5);

            priceDateRepository.save(priceDateMapper.map(date1CreateDTO));
            priceDateRepository.save(priceDateMapper.map(date2CreateDTO));
            priceDateRepository.save(priceDateMapper.map(date3CreateDTO));
            priceDateRepository.save(priceDateMapper.map(date4CreateDTO));
            priceDateRepository.save(priceDateMapper.map(date5CreateDTO));
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
            Product product = productRepository.findByName(SHIRT)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            Brand brand  = brandRepository.findByName(ZARA)
                    .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

            PriceList priceList1  = priceListRepository.findByName(ALL_YEAR)
                    .orElseThrow(() -> new ResourceNotFoundException(PRICE_LIST_NOT_FOUND));

            PriceList priceList2  = priceListRepository.findByName(AFTERNOON)
                    .orElseThrow(() -> new ResourceNotFoundException(PRICE_LIST_NOT_FOUND));

            PriceList priceList3  = priceListRepository.findByName(MORNING)
                    .orElseThrow(() -> new ResourceNotFoundException(PRICE_LIST_NOT_FOUND));

            PriceList priceList4  = priceListRepository.findByName(EVENING)
                    .orElseThrow(() -> new ResourceNotFoundException(PRICE_LIST_NOT_FOUND));

            Price price1 = new Price();
            price1.setProduct(product);
            price1.setBrand(brand);
            price1.setPriceList(priceList1);
            price1.setStartDate(LocalDateTime.parse("2020-06-14 10:00:00", FORMATTER));
            price1.setEndDate(LocalDateTime.parse("2020-12-31 23:59:59", FORMATTER));
            price1.setPrice(BigDecimal.valueOf(35.50));
            price1.setCurr(EUR);
            price1.setPriority(0L);

            Price price2 = new Price();
            price2.setProduct(product);
            price2.setBrand(brand);
            price2.setPriceList(priceList2);
            price2.setStartDate(LocalDateTime.parse("2020-06-14 15:00:00", FORMATTER));
            price2.setEndDate(LocalDateTime.parse("2020-06-14 18:30:00", FORMATTER));
            price2.setPrice(BigDecimal.valueOf(25.45));
            price2.setCurr(EUR);
            price2.setPriority(1L);

            Price price3 = new Price();
            price3.setProduct(product);
            price3.setBrand(brand);
            price3.setPriceList(priceList3);
            price3.setStartDate(LocalDateTime.parse("2020-06-15 00:00:00", FORMATTER));
            price3.setEndDate(LocalDateTime.parse("2020-06-15 11:00:00", FORMATTER));
            price3.setPrice(BigDecimal.valueOf(30.50));
            price3.setCurr(EUR);
            price3.setPriority(1L);

            Price price4 = new Price();
            price4.setProduct(product);
            price4.setBrand(brand);
            price4.setPriceList(priceList4);
            price4.setStartDate(LocalDateTime.parse("2020-06-15 16:00:00", FORMATTER));
            price4.setEndDate(LocalDateTime.parse("2020-12-31 23:59:59", FORMATTER));
            price4.setPrice(BigDecimal.valueOf(38.95));
            price4.setCurr(EUR);
            price4.setPriority(1L);

            priceRepository.save(price1);
            priceRepository.save(price2);
            priceRepository.save(price3);
            priceRepository.save(price4);

            items = priceRepository.findAll();
        }
    }
}
