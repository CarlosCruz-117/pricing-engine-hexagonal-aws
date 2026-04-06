package com.company.pricingengine.infrastructure.repository.jpa;

import com.company.pricingengine.infrastructure.repository.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, Long> {

    List<PriceEntity> findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long productId,
            Long brandId,
            LocalDateTime date1,
            LocalDateTime date2
    );
}

