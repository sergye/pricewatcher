package pricewatcher.app.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ProductDTO {
    private Long id;
    private String name;
    private LocalDate createdAt;
}
