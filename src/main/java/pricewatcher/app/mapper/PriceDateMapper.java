package pricewatcher.app.mapper;

import pricewatcher.app.dto.pricedate.PriceDateCreateDTO;
import pricewatcher.app.dto.pricedate.PriceDateDTO;
import pricewatcher.app.dto.pricedate.PriceDateUpdateDTO;
import pricewatcher.app.model.PriceDate;
import org.mapstruct.Mapper;
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
public abstract class PriceDateMapper {
    public abstract PriceDate map(PriceDateCreateDTO dto);
    public abstract PriceDateDTO map(PriceDate model);
    public abstract void update(PriceDateUpdateDTO dto, @MappingTarget PriceDate model);
}
