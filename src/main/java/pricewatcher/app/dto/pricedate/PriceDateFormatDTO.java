package pricewatcher.app.dto.pricedate;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class PriceDateFormatDTO {
    @NotBlank
    private LocalDate priceDate;
}
