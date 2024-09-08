package pricewatcher.app.mapper;

import pricewatcher.app.dto.product.ProductCreateDTO;
import pricewatcher.app.dto.product.ProductDTO;
import pricewatcher.app.dto.product.ProductUpdateDTO;
import pricewatcher.app.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        uses = { JsonNullableMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ProductMapper {
    public abstract Product map(ProductCreateDTO dto);
    public abstract ProductDTO map(Product model);
    public abstract void update(ProductUpdateDTO dto, @MappingTarget Product model);
}
