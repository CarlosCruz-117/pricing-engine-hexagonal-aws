package com.company.pricingengine.infrastructure.rest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test de integración parametrizado por CSV que valida los 5 casos propuestos
     * Si se desea añadir más casos o modificar algún dato de los existentes, solo hay que modificar el CSV
     */
    @ParameterizedTest(name = "Test {index}: Fecha {0}, Producto {1}, Cadena {2} => Esperado {3}€")
    @CsvFileSource(resources = "/prices-test-data.csv", numLinesToSkip = 1)
    void shouldReturnCorrectPriceForEachTestScenario(
            String applicationDate,
            Long productId,
            Long brandId,
            Double expectedPrice) throws Exception {

        mockMvc.perform(get("/prices")
                        .param("applicationDate", applicationDate)
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.brandId").value(brandId))
                .andExpect(jsonPath("$.price").value(expectedPrice));
    }
}