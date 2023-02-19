package pl.marcin.stockmanagerapi.dto;


import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProductDto(Long id, String name, BigDecimal price, @NotNull Long quantity) {
}
