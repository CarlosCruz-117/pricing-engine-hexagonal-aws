package com.company.pricingengine.infrastructure.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceResponseDTO(
        Long productId,
        Long brandId,
        Long priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price,
        String currency
) {}
