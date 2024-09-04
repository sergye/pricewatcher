package pricewatcher.app.mapper;

import pricewatcher.app.dto.BrandCreateDTO;
import pricewatcher.app.dto.BrandDTO;
import pricewatcher.app.dto.BrandUpdateDTO;
import pricewatcher.app.model.Brand;
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
public abstract class BrandMapper {
    public abstract Brand map(BrandCreateDTO dto);
    public abstract BrandDTO map(Brand model);
    public abstract void update(BrandUpdateDTO dto, @MappingTarget Brand model);
}
