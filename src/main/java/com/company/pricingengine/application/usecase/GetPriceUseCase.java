package com.company.pricingengine.application.usecase;

import com.company.pricingengine.application.port.out.PriceRepositoryPort;
import com.company.pricingengine.domain.exceptions.PriceNotFoundException;
import com.company.pricingengine.domain.model.Price;

import java.time.LocalDateTime;

public class GetPriceUseCase {

    private final PriceRepositoryPort repository;

    public GetPriceUseCase(PriceRepositoryPort repository) {
        this.repository = repository;
    }

    public Price execute(Long productId, Long brandId, LocalDateTime applicationDate) {
        return repository.findApplicablePrice(productId, brandId, applicationDate)
                .orElseThrow(() -> new PriceNotFoundException("Price not found"));
    }
}