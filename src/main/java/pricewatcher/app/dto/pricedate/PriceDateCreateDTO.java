package pricewatcher.app.dto.pricedate;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PriceDateCreateDTO {
    @NotBlank
    private String priceDate;
}
