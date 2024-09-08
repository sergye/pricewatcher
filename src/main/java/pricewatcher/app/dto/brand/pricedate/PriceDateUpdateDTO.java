package pricewatcher.app.dto.brand.pricedate;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDate;

@Getter
@Setter
public class PriceDateUpdateDTO {
    private JsonNullable<LocalDate> priceDate;
}
