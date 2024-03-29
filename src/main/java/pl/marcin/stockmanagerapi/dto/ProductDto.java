package pl.marcin.stockmanagerapi.dto;


import java.math.BigDecimal;

public record ProductDto(Long id, String name, BigDecimal price, Long currentQuantity,
         Long reservedQuantity, Long availableQuantity) {
}
