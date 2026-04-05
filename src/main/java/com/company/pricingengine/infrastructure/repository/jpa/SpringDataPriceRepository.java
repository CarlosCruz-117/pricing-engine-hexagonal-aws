package com.company.pricingengine.infrastructure.repository.jpa;

import com.company.pricingengine.infrastructure.repository.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, Long> {

    /** OrderByPriority para devolver 1 solo resultado*/
    Optional<PriceEntity> findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            Long productId,
            Long brandId,
            LocalDateTime date1,
            LocalDateTime date2
    );
}

