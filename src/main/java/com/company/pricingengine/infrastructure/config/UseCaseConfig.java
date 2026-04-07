package com.company.pricingengine.infrastructure.config;

import com.company.pricingengine.application.port.in.GetPriceQuery;
import com.company.pricingengine.domain.service.PriceSelector;
import com.company.pricingengine.infrastructure.repository.CachedPriceRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public GetPriceQuery getPriceQuery(CachedPriceRepositoryAdapter repository) {
        return new PriceSelector(repository);
    }
}
