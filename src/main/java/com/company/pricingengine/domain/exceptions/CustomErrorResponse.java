package com.company.pricingengine.domain.exceptions;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CustomErrorResponse(
        String code,
        String message,
        LocalDateTime timestamp
) {}