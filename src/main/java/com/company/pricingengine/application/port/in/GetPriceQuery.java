package com.company.pricingengine.application.port.in;

import com.company.pricingengine.domain.model.Price;

import java.time.LocalDateTime;

public interface GetPriceQuery {

    Price execute(Long productId, Long brandId, LocalDateTime applicationDate);
}
