package pricewatcher.app.dto.price;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Getter
@Setter
public class PriceUpdateDTO {
    private JsonNullable<String> product;
    private JsonNullable<String> brand;
    private JsonNullable<String> priceList;
    private JsonNullable<LocalDateTime> startDate;
    private JsonNullable<LocalDateTime> endDate;
    private JsonNullable<BigDecimal> price;
    private JsonNullable<Currency> curr;
    private JsonNullable<Long> priority;
}
