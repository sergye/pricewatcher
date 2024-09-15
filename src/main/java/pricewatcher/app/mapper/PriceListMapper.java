package pricewatcher.app.mapper;

import pricewatcher.app.dto.pricelist.PriceListCreateDTO;
import pricewatcher.app.dto.pricelist.PriceListDTO;
import pricewatcher.app.dto.pricelist.PriceListUpdateDTO;
import pricewatcher.app.model.PriceList;
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
public abstract class PriceListMapper {
    public abstract PriceList map(PriceListCreateDTO dto);
    public abstract PriceListDTO map(PriceList model);
    public abstract void update(PriceListUpdateDTO dto, @MappingTarget PriceList model);
}
