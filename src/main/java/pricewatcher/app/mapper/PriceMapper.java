package pricewatcher.app.mapper;

import pricewatcher.app.dto.price.PriceCreateDTO;
import pricewatcher.app.dto.price.PriceDTO;
import pricewatcher.app.dto.price.PriceUpdateDTO;
import pricewatcher.app.model.Price;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        uses = { JsonNullableMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class PriceMapper {
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "priceList", ignore = true)
    public abstract Price map(PriceCreateDTO dto);

    @Mapping(source = "product.name", target = "product")
    @Mapping(source = "brand.name", target = "brand")
    @Mapping(source = "priceList.name", target = "priceList")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    public abstract PriceDTO map(Price model);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "priceList", ignore = true)
    public abstract void update(PriceUpdateDTO dto, @MappingTarget Price model);
}
