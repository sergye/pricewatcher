package pricewatcher.app.util;

import pricewatcher.app.model.Brand;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.springframework.stereotype.Component;
import pricewatcher.app.model.Price;
import pricewatcher.app.model.PriceDate;
import pricewatcher.app.model.PriceList;
import pricewatcher.app.model.Product;
import pricewatcher.app.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static pricewatcher.app.service.PriceDateService.FORMATTER;

@Getter
@Component
public class ModelGenerator {
    private static final Faker FAKER = new Faker();

    private Model<User> userModel;
    private Model<Brand> brandModel;
    private Model<Product> productModel;
    private Model<PriceDate> priceDateModel;
    private Model<PriceList> priceListModel;
    private Model<Price> priceModel;

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

        priceListModel = Instancio.of(PriceList.class)
                .ignore(Select.field(PriceList::getId))
                .supply(Select.field(PriceList::getName), () -> FAKER.name().name())
                .toModel();

        priceModel = Instancio.of(Price.class)
                .ignore(Select.field(Price::getId))
                .supply(Select.field(Price::getProduct), this::getProductModel)
                .supply(Select.field(Price::getBrand), this::getBrandModel)
                .supply(Select.field(Price::getPriceList), this::getProductModel)
                .supply(Select.field(Price::getStartDate),
                        () -> LocalDateTime.parse("2020-06-18 10:00:00", FORMATTER))
                .supply(Select.field(Price::getEndDate),
                        () -> LocalDateTime.parse("2020-06-18 23:59:59", FORMATTER))
                .supply(Select.field(Price::getPrice), () -> BigDecimal.valueOf(130L))
                .supply(Select.field(Price::getCurr), () -> Currency.getInstance("EUR"))
                .supply(Select.field(Price::getPriority), () -> 1L)
                .toModel();
    }
}
