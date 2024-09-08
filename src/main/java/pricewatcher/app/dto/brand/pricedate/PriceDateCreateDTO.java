package pricewatcher.app.dto.brand.pricedate;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class PriceDateCreateDTO {
    @NotBlank
    private LocalDate priceDate;
}
