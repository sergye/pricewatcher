package pricewatcher.app.dto.pricedate;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class PriceDateDTO {
    private Long id;
    private LocalDateTime priceDate;
    private LocalDate createdAt;
}
