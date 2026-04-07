package com.company.pricingengine.infrastructure.repository;

import com.company.pricingengine.application.port.out.PriceRepositoryPort;
import com.company.pricingengine.domain.model.Price;
import com.company.pricingengine.infrastructure.repository.jpa.PriceRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Decorador de caché de PriceRepositoryAdapter.
 * Aislamos lógica de caché para no sobrecargar de responsabilidades al Adapter
 */
@Primary
@Component
@RequiredArgsConstructor
public class CachedPriceRepositoryAdapter implements PriceRepositoryPort {
    private final PriceRepositoryAdapter delegate;

    @Cacheable(
            value = "prices",
            key = "#productId + '-' + #brandId + '-' + #date",
            unless = "#result == null"
    )
    @Override
    public Optional<Price> findTopPrice(Long productId, Long brandId, LocalDateTime date) {
        return delegate.findTopPrice(productId, brandId, date);
    }
}

