package pricewatcher.app.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductCreateDTO {

    @NotBlank
    private String name;
}
