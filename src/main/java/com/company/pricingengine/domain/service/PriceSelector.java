package com.company.pricingengine.domain.service;

import com.company.pricingengine.domain.model.Price;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class PriceSelector {

    public Price selectHighestPriority(List<Price> prices) {
        return prices.stream()
                .max(Comparator.comparing(Price::getPriority))
                .orElseThrow(() -> new IllegalStateException("No price found"));
    }
}