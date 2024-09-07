package pricewatcher.app.component;

import pricewatcher.app.dto.brand.BrandCreateDTO;
import pricewatcher.app.mapper.BrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


import pricewatcher.app.model.Brand;
import pricewatcher.app.repository.BrandRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private final BrandRepository brandRepository;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private DefaultBrandProperties defaultBrandProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createDefaultBrand();
    }

    private void createDefaultBrand() {
        if (brandRepository.findAll().isEmpty()) {
            String name = defaultBrandProperties.getName();
            if (brandRepository.findByName(name).isEmpty()) {
                BrandCreateDTO brandData = new BrandCreateDTO();
                brandData.setName(name);
                Brand brand = brandMapper.map(brandData);
                brandRepository.save(brand);
            }
        }
    }
}
