package pricewatcher.app.dto.brand;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BrandDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
