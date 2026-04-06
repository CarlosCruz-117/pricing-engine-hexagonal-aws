package com.company.pricingengine.infrastructure.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Prueba CTs de forma unitaria
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CacheManager cacheManager;

    @BeforeEach
    void clearCache() {
        Objects.requireNonNull(cacheManager.getCache("prices")).clear();
    }

    /**
     * Testea HTTP 404 -> productos no encontrados en bbdd H2 local
     *
     * @throws Exception
     */
    @Test
    void shouldReturn404WhenNoPriceFound() throws Exception {

        mockMvc.perform(get("/v1/prices")
                        .param("applicationDate", "2025-01-01T10:00:00")
                        .param("productId", "99999")
                        .param("brandId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("PRICE_NOT_FOUND"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldReturn400WhenInvalidParameterFormat() throws Exception {

        mockMvc.perform(get("/v1/prices")
                        .param("applicationDate", "06-14-2020T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_PARAMETER"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldReturn400WhenMissingParameter() throws Exception {

        mockMvc.perform(get("/v1/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("MISSING_PARAMETER"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
