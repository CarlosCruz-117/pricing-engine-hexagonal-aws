package com.company.pricingengine.infrastructure.repository.jpa;

import com.company.pricingengine.domain.model.Price;
import com.company.pricingengine.infrastructure.repository.mapper.PriceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PriceRepositoryAdapter {
    private final SpringDataPriceRepository repository;
    private final PriceMapper mapper;

    public Optional<Price> findTopPrice(Long productId, Long brandId, LocalDateTime date) {
        return repository
                .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        productId, brandId, date, date
                )
                .map(mapper::toDomainModel);
    }
}
