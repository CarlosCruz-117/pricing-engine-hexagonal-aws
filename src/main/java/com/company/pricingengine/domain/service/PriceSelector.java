package com.company.pricingengine.domain.service;

import com.company.pricingengine.application.port.in.GetPriceQuery;
import com.company.pricingengine.application.port.out.PriceRepositoryPort;
import com.company.pricingengine.domain.exception.PriceNotFoundException;
import com.company.pricingengine.domain.model.Price;

import java.time.LocalDateTime;

public class PriceSelector implements GetPriceQuery {

    private final PriceRepositoryPort repository;

    public PriceSelector(PriceRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Price execute(Long productId, Long brandId, LocalDateTime date) {
        return repository.findTopPrice(productId, brandId, date)
                .orElseThrow(() -> new PriceNotFoundException(productId, brandId, date));
    }
}