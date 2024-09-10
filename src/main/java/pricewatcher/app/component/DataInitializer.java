package pricewatcher.app.component;

import pricewatcher.app.dto.brand.BrandCreateDTO;
import pricewatcher.app.dto.pricedate.PriceDateCreateDTO;
import pricewatcher.app.dto.product.ProductCreateDTO;
import pricewatcher.app.dto.user.UserCreateDTO;
import pricewatcher.app.mapper.BrandMapper;
import pricewatcher.app.mapper.PriceDateMapper;
import pricewatcher.app.mapper.ProductMapper;
import pricewatcher.app.mapper.UserMapper;
import pricewatcher.app.model.User;
import pricewatcher.app.repository.BrandRepository;
import pricewatcher.app.repository.PriceDateRepository;
import pricewatcher.app.repository.ProductRepository;
import pricewatcher.app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;


import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private PriceDateRepository priceDateRepository;

    @Autowired
    private PriceDateMapper priceDateMapper;

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
            BrandCreateDTO zaraBrandDTO = new BrandCreateDTO();
            zaraBrandDTO.setName("Zara");

            BrandCreateDTO mangoBrandDTO = new BrandCreateDTO();
            mangoBrandDTO.setName("Mango");

            brandRepository.save(brandMapper.map(zaraBrandDTO));
            brandRepository.save(brandMapper.map(mangoBrandDTO));
        }
    }

    private void createDefaultProducts() {
        if (productRepository.findAll().isEmpty()) {
            ProductCreateDTO shirtProductDTO = new ProductCreateDTO();
            shirtProductDTO.setName("Shirt");

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
}
