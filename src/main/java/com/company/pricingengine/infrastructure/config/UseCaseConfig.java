package com.company.pricingengine.infrastructure.config;

import com.company.pricingengine.application.port.in.GetPriceQuery;
import com.company.pricingengine.application.port.out.PriceRepositoryPort;
import com.company.pricingengine.domain.service.PriceSelector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public GetPriceQuery getPriceQuery(PriceRepositoryPort repository) {
        return new PriceSelector(repository);
    }
}
