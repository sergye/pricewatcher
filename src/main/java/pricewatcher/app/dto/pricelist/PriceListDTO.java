package pricewatcher.app.dto.pricelist;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class PriceListDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
