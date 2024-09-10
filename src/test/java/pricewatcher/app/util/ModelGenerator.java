package pricewatcher.app.util;

import pricewatcher.app.model.Brand;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.springframework.stereotype.Component;
import pricewatcher.app.model.PriceDate;
import pricewatcher.app.model.Product;
import pricewatcher.app.model.User;

@Getter
@Component
public class ModelGenerator {
    private static final Faker FAKER = new Faker();

    private Model<User> userModel;
    private Model<Brand> brandModel;
    private Model<Product> productModel;
    private Model<PriceDate> priceDateModel;

    @PostConstruct
    private void init() {
        userModel = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .supply(Select.field(User::getFirstName), () -> FAKER.name().firstName())
                .supply(Select.field(User::getLastName), () -> FAKER.name().lastName())
                .supply(Select.field(User::getEmail), () -> FAKER.internet().emailAddress())
                .supply(Select.field(User::getPassword), () -> FAKER.internet().password(5, 30))
                .toModel();

        brandModel = Instancio.of(Brand.class)
                .ignore(Select.field(Brand::getId))
                .supply(Select.field(Brand::getName), () -> FAKER.name().name())
                .toModel();

        productModel = Instancio.of(Product.class)
                .ignore(Select.field(Product::getId))
                .supply(Select.field(Product::getName), () -> FAKER.name().name())
                .toModel();

        priceDateModel = Instancio.of(PriceDate.class)
                .ignore(Select.field(PriceDate::getId))
                .supply(Select.field(PriceDate::getPriceDate), () -> FAKER.date().birthday()
                        .toLocalDateTime())
                .toModel();
    }
}
