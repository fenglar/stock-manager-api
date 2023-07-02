package pl.marcin.stockmanagerapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockDto {
    Long id;
    Long productId;
    Long currentQuantity;

}
