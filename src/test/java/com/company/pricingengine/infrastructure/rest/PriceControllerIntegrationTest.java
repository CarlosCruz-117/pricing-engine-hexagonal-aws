package com.company.pricingengine.infrastructure.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    CacheManager cacheManager;

    @BeforeEach
    void clearCache() {
        Objects.requireNonNull(cacheManager.getCache("prices")).clear();
    }

    static Stream<Object[]> priceScenarios() {
        return Stream.of(
                new Object[]{"2020-06-14T10:00:00", 35455L, 1L, 1, 35.50},
                new Object[]{"2020-06-14T16:00:00", 35455L, 1L, 2, 25.45},
                new Object[]{"2020-06-14T21:00:00", 35455L, 1L, 1, 35.50},
                new Object[]{"2020-06-15T10:00:00", 35455L, 1L, 3, 30.50},
                new Object[]{"2020-06-16T21:00:00", 35455L, 1L, 4, 38.95}
        );
    }

    /**
     * Testea a partir de prices-test-data.csv los casos de HTTP 200 OK sobre la bbdd H2 local.
     * Si se desea añadir más casos a probar OK, se deben añadir al fichero CSV
     * @param applicationDate
     * @param productId
     * @param brandId
     * @param expectedPrice
     */
    @ParameterizedTest(name = "Test {index}: {0} → product={1}, brand={2}, priceList={3}, price={4}")
    @CsvFileSource(resources = "/prices-test-data.csv", numLinesToSkip = 1)
    void shouldReturnCorrectPriceForEachScenario(
            String applicationDate,
            Long productId,
            Long brandId,
            Integer expectedPriceList,
            Double expectedPrice
    ) throws Exception {

        MvcResult result = mockMvc.perform(get("/v1/prices")
                        .param("applicationDate", applicationDate)
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString()))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode json = objectMapper.readTree(
                result.getResponse().getContentAsString(StandardCharsets.UTF_8)
        );

        assertEquals(productId, json.get("productId").asLong());
        assertEquals(brandId, json.get("brandId").asLong());
        assertEquals(expectedPriceList, json.get("priceList").asInt());
        assertEquals(expectedPrice, json.get("price").asDouble());

        assertNotNull(json.get("startDate"),  "startDate no debe ser null");
        assertNotNull(json.get("endDate"),    "endDate no debe ser null");
        assertNotNull(json.get("currency"),   "currency no debe ser null");
        assertEquals("EUR", json.get("currency").asText());
    }
}