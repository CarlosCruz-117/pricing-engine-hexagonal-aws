package com.company.pricingengine.infrastructure.repository.jpa;

import com.company.pricingengine.application.port.out.PriceRepositoryPort;
import com.company.pricingengine.domain.model.Price;
import com.company.pricingengine.infrastructure.repository.mapper.PriceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    private final SpringDataPriceRepository repository;
    private final PriceMapper mapper;

    @Override
    public Optional<Price> findApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate) {
        return repository
                .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        productId,
                        brandId,
                        applicationDate,
                        applicationDate
                )
                .map(mapper::toDomainModel);
    }
}