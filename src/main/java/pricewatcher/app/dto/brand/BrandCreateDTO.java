package pricewatcher.app.dto.brand;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BrandCreateDTO {
    @NotBlank
    private String name;
}
