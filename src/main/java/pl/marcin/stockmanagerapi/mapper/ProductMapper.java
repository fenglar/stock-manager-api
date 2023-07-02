package pl.marcin.stockmanagerapi.mapper;

import org.mapstruct.Mapper;
import pl.marcin.stockmanagerapi.dto.ProductDto;
import pl.marcin.stockmanagerapi.entity.OrderReservation;
import pl.marcin.stockmanagerapi.entity.Product;
import pl.marcin.stockmanagerapi.entity.Stock;


@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    public abstract Product productDtoToProduct(ProductDto productDto);

    abstract ProductDto productToProductDto(Product product,
                                            Long currentQuantity,
                                            Long availableQuantity,
                                            Long reservedQuantity);

    public ProductDto productToProductDto(Product product) {
        Long currentQuantity = product.getStock().stream().mapToLong(Stock::getCurrentQuantity).sum();
        Long reservedQuantity = product.getOrderReservations().stream().mapToLong(OrderReservation::getReservedQuantity).sum();
        Long availableQuantity = currentQuantity - reservedQuantity;
        return productToProductDto(product, currentQuantity, availableQuantity, reservedQuantity);
    }
}