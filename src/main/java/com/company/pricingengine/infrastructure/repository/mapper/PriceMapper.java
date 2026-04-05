package com.company.pricingengine.infrastructure.repository.mapper;

import com.company.pricingengine.domain.model.Price;
import com.company.pricingengine.infrastructure.repository.PriceEntity;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {

    /** Entity → Domain */
    public Price toDomainModel(PriceEntity e) {
        return Price.builder()
                .brandId(e.getBrandId())
                .startDate(e.getStartDate())
                .endDate(e.getEndDate())
                .priceList(e.getPriceList())
                .productId(e.getProductId())
                .priority(e.getPriority())
                .price(e.getPrice())
                .currency(e.getCurrency())
                .build();
    }
}
