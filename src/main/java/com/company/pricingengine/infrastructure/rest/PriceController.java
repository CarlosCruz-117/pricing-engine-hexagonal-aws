package com.company.pricingengine.infrastructure.rest;

import com.company.pricingengine.application.usecase.GetPriceUseCase;
import com.company.pricingengine.domain.model.Price;
import com.company.pricingengine.infrastructure.dto.PriceResponseDTO;
import com.company.pricingengine.infrastructure.rest.mapper.PriceDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/prices")
@Tag(name = "Prices", description = "Operations related to price calculation")
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;
    private final PriceDtoMapper priceDtoMapper;

    public PriceController(GetPriceUseCase getPriceUseCase, PriceDtoMapper priceDtoMapper) {
        this.getPriceUseCase = getPriceUseCase;
        this.priceDtoMapper = priceDtoMapper;
    }

    @GetMapping
    @Operation(
            summary = "Get price",
            description = "Calculates the final price for a product based on brand and application date"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price calculated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "404", description = "Price not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })

    public ResponseEntity<PriceResponseDTO> getPrice(
            @Parameter(description = "Application date in ISO format (yyyy-MM-ddTHH:mm:ss)", required = true)
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime applicationDate,

            @Parameter(description = "Product ID", example = "35455", required = true)
            @RequestParam
            Long productId,

            @Parameter(description = "Brand ID", example = "1", required = true)
            @RequestParam
            Long brandId
    ) {
        Price price = getPriceUseCase.execute(productId, brandId, applicationDate);
        return ResponseEntity.ok(priceDtoMapper.toDto(price));
    }

}