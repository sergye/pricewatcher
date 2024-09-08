package pricewatcher.app.dto.brand;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class BrandDTO {
    private Long id;
    private String name;
    private LocalDate createdAt;
}
