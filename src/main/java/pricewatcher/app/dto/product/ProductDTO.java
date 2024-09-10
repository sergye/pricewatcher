package pricewatcher.app.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ProductDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
