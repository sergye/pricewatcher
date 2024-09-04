package pricewatcher.app.util;

import pricewatcher.app.model.Brand;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ModelGenerator {
    private static final Faker FAKER = new Faker();

    private Model<Brand> brandModel;

    @PostConstruct
    private void init() {
        brandModel = Instancio.of(Brand.class)
                .ignore(Select.field(Brand::getId))
                .supply(Select.field(Brand::getName), () -> FAKER.name().name())
                .toModel();
    }
}
