package pricewatcher.app.dto;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class BrandUpdateDTO {
    private JsonNullable<String> firstName;
}
