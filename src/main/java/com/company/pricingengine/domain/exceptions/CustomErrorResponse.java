package com.company.pricingengine.domain.exceptions;

public record CustomErrorResponse(
        String code,
        String message
) {}