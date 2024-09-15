package pricewatcher.app.dto.price;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PriceFilterDTO {
    private String product;
    private String brand;
    private String priceList;
    private String priceDate;
}
