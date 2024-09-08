package pricewatcher.app.dto.brand.pricedate;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class PriceDateDTO {
    private Long id;
    private LocalDate priceDate;
    private LocalDate createdAt;
}
