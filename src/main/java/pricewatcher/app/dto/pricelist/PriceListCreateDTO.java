package pricewatcher.app.dto.pricelist;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PriceListCreateDTO {
    @NotBlank
    private String name;
}
