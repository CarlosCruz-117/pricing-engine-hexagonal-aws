package com.company.pricingengine.application.port.out;

import com.company.pricingengine.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepositoryPort {

    Optional<Price> findTopPrice(Long productId, Long brandId, LocalDateTime date);
}