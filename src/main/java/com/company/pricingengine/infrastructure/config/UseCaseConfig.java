package com.company.pricingengine.infrastructure.config;

import com.company.pricingengine.application.port.out.PriceRepositoryPort;
import com.company.pricingengine.application.usecase.GetPriceUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    /** Declaramos Bean fuera de capa "application" -> Inversión de dependencias (SOLID - D) */
    @Bean
    public GetPriceUseCase getPriceUseCase(
            PriceRepositoryPort repository
    ) {
        return new GetPriceUseCase(repository);
    }
}