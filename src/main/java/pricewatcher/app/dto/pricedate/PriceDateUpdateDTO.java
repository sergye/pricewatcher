package pricewatcher.app.dto.pricedate;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class PriceDateUpdateDTO {
    private JsonNullable<String> priceDate;
}
