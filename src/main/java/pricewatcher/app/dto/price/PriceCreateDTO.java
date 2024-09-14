package pricewatcher.app.dto.price;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Setter
@Getter
public class PriceCreateDTO {
    @NotBlank
    private String product;

    @NotBlank
    private String brand;

    @NotBlank
    private String priceList;

    @NotBlank
    private LocalDateTime startDate;

    @NotBlank
    private LocalDateTime endDate;

    @NotBlank
    private BigDecimal price;

    @NotBlank
    private Currency curr;

    @NotBlank
    private Long priority;
}
