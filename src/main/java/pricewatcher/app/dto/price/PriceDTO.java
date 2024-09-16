package pricewatcher.app.dto.price;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Setter
@Getter
public class PriceDTO {
    private Long id;
    private String product;
    private String brand;
    private String priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal price;
    private Currency curr;
    private Long priority;
    private LocalDateTime createdAt;
}
