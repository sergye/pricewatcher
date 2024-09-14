package pricewatcher.app.dto.price;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class PriceFilterDTO {
    private String product;
    private String brand;
    private String priceList;
    private LocalDateTime priceDate;
}
