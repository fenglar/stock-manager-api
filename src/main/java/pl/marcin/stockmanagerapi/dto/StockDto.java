package pl.marcin.stockmanagerapi.dto;

import lombok.Data;

@Data
public class StockDto {
    Long id;
    Long availableQuantity;
    Long reservedQuantity;
    ProductDto productDto;

}
