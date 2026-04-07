package com.company.pricingengine.infrastructure.rest.mapper;

import com.company.pricingengine.domain.model.Price;
import com.company.pricingengine.infrastructure.dto.PriceResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper con MapStruct
 */
@Mapper(componentModel = "spring")
public interface PriceDtoMapper {

    /** Domain → DTO */
    @Mapping(source = "priceList", target = "priceList")
    @Mapping(source = "brandId", target = "brandId")
    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "currency", target = "currency")
    PriceResponseDTO toDto(Price price);
}