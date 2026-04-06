package com.company.pricingengine.application.port.out;

import com.company.pricingengine.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepositoryPort {

    List<Price> findPrices(
            Long productId,
            Long brandId,
            LocalDateTime applicationDate
    );
}