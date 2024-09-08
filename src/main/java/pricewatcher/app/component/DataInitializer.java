package pricewatcher.app.component;

import pricewatcher.app.dto.brand.BrandCreateDTO;
import pricewatcher.app.dto.product.ProductCreateDTO;
import pricewatcher.app.dto.user.UserCreateDTO;
import pricewatcher.app.mapper.BrandMapper;
import pricewatcher.app.mapper.ProductMapper;
import pricewatcher.app.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import pricewatcher.app.model.User;
import pricewatcher.app.repository.BrandRepository;
import pricewatcher.app.repository.ProductRepository;
import pricewatcher.app.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandMapper brandMapper;

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
}
