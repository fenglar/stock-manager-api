package pl.marcin.stockmanagerapi.dto;

import lombok.Data;

@Data
public class StockDto {
    Long id;
    Long currentQuantity;
    Long reservedQuantity;
    Long availableQuantity;

}
