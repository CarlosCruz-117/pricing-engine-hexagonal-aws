package com.company.pricingengine.domain.service;

import com.company.pricingengine.application.port.out.PriceRepositoryPort;
import com.company.pricingengine.domain.exceptions.PriceNotFoundException;
import com.company.pricingengine.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceSelectorTest {

    @Mock
    private PriceRepositoryPort repository;

    @InjectMocks
    private PriceSelector priceSelector;

    @Test
    void shouldReturnPriceWhenFound() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        Price expected = Price.builder()
                .productId(35455L).brandId(1L).priceList(2L)
                .price(BigDecimal.valueOf(25.45)).currency("EUR")
                .startDate(date).endDate(date)
                .build();

        when(repository.findTopPrice(35455L, 1L, date)).thenReturn(Optional.of(expected));

        Price result = priceSelector.execute(35455L, 1L, date);

        assertThat(result).isEqualTo(expected);
        verify(repository, times(1)).findTopPrice(35455L, 1L, date);
    }

    @Test
    void shouldThrowPriceNotFoundWhenNoPrice() {
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 10, 0);
        when(repository.findTopPrice(99999L, 1L, date)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> priceSelector.execute(99999L, 1L, date))
                .isInstanceOf(PriceNotFoundException.class);
    }
}