package pricewatcher.app.dto.pricelist;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class PriceListUpdateDTO {
    private JsonNullable<String> name;
}
