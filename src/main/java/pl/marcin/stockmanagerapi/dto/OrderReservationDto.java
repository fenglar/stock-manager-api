package pl.marcin.stockmanagerapi.dto;

import lombok.Data;

@Data
public class OrderReservationDto {
    Long orderReservationId;
    Long reservedQuantity;
    ProductDto productDto;

}
