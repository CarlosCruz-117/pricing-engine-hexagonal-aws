package com.company.pricingengine.application.usecase;

import com.company.pricingengine.application.port.in.GetPriceQuery;
import com.company.pricingengine.application.port.out.PriceRepositoryPort;
import com.company.pricingengine.domain.exceptions.PriceNotFoundException;
import com.company.pricingengine.domain.model.Price;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDateTime;
import java.util.Comparator;

public class GetPriceUseCase implements GetPriceQuery {

    private final PriceRepositoryPort repository;

    public GetPriceUseCase(PriceRepositoryPort repository) {
        this.repository = repository;
    }

    @Cacheable(
            value = "prices",
            key = "#productId + '-' + #brandId + '-' + #applicationDate",
            unless = "#result == null" // quitamos cacheo de valores nulos
    )
    @Override
    public Price execute(Long productId, Long brandId, LocalDateTime date) {
        return repository.findPrices(productId, brandId, date)
                .stream()
                .max(Comparator.comparingInt(Price::getPriority))
                .orElseThrow(() -> new PriceNotFoundException(productId, brandId, date));
    }
}