package com.company.pricingengine.domain.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PriceNotFoundException extends RuntimeException {

    private final Long productId;
    private final Long brandId;
    private final LocalDateTime applicationDate;

    public PriceNotFoundException(Long productId, Long brandId, LocalDateTime applicationDate) {
        super(buildMessage(productId, brandId, applicationDate));
        this.productId = productId;
        this.brandId = brandId;
        this.applicationDate = applicationDate;
    }

    private static String buildMessage(Long productId, Long brandId, LocalDateTime date) {
        return "Price not found for productId=%d, brandId=%d, applicationDate=%s"
                .formatted(productId, brandId, date);
    }

}
