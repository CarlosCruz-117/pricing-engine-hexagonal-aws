package com.company.pricingengine.infrastructure.rest;

import org.junit.jupiter.api.Test;
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
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

    @Test
    void shouldReturn400WhenInvalidParameterFormat() throws Exception {

        mockMvc.perform(get("/v1/prices")
                        .param("applicationDate", "06-14-2020T10:00:00") // fecha con formato erróneo
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_PARAMETER"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void shouldReturn400WhenMissingParameter() throws Exception {

        mockMvc.perform(get("/v1/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        //.param("productId", "35455") quitamos productId para provocar param no informado
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("MISSING_PARAMETER"))
                .andExpect(jsonPath("$.message").exists());
    }
}
