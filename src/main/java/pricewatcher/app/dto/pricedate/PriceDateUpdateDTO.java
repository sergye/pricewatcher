package pricewatcher.app.dto.pricedate;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDateTime;

@Getter
@Setter
public class PriceDateUpdateDTO {
    private JsonNullable<LocalDateTime> priceDate;
}
