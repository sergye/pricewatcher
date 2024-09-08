package pricewatcher.app.dto.product;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class ProductUpdateDTO {
    private JsonNullable<String> name;
}
