package com.company.pricingengine.infrastructure.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Testea a partir de un CSV los casos de HTTP 200 OK sobre la bbdd H2 local.
     * Si se desea añadir más casos a probar OK, se deben añadir al fichero CSV
     * @param applicationDate
     * @param productId
     * @param brandId
     * @param expectedPrice
     */
    @ParameterizedTest(name = "Test {index}: Fecha {0}, Producto {1}, Cadena {2} => Esperado {3}€")
    @CsvFileSource(resources = "/prices-test-data.csv", numLinesToSkip = 1)
    void shouldReturnCorrectPriceForEachTestScenario(
            String applicationDate,
            Long productId,
            Long brandId,
            Double expectedPrice) throws Exception {

        MvcResult result = mockMvc.perform(get("/v1/prices")
                        .param("applicationDate", applicationDate)
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.brandId").value(brandId))
                .andExpect(jsonPath("$.price").value(expectedPrice))
                .andReturn();

        // Extraer el JSON de response
        String responseBody = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        JsonNode json = objectMapper.readTree(responseBody);
        // Log por consola
        System.out.println("\n*** PRODUCTO (SALIDA) ***");
        System.out.println("Producto: " + json.get("productId"));
        System.out.println("Brand: " + json.get("brandId"));
        System.out.println("PriceList: " + json.get("priceList"));
        System.out.println("StartDate: " + json.get("startDate"));
        System.out.println("EndDate: " + json.get("endDate"));
        System.out.println("Price: " + json.get("price"));
        System.out.println("___________________________\n");
    }

    /**
     * Testea HTTP 404 -> productos no encontrados en bbdd H2 local
     * @throws Exception
     */
    @Test
    void shouldReturn404WhenNoPriceFound() throws Exception {

        String applicationDate = "2025-01-01T10:00:00"; // fecha fuera de rango
        long productId = 99999L; // product id inexistente
        long brandId = 1L;

        MvcResult result = mockMvc.perform(get("/v1/prices")
                        .param("applicationDate", applicationDate)
                        .param("productId", Long.toString(productId))
                        .param("brandId", Long.toString(brandId)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("PRICE_NOT_FOUND"))
                .andExpect(jsonPath("$.message").exists())
                .andReturn();

        // Extraer el JSON de response
        String responseBody = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        // Log por consola
        System.out.println("\n*** ERROR 404 (SALIDA) ***");
        System.out.println(responseBody);
        System.out.println("___________________________\n");
    }
}