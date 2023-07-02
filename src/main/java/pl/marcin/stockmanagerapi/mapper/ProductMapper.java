package pl.marcin.stockmanagerapi.mapper;

import org.mapstruct.Mapper;
import pl.marcin.stockmanagerapi.dto.ProductDto;
import pl.marcin.stockmanagerapi.entity.OrderReservation;
import pl.marcin.stockmanagerapi.entity.Product;
import pl.marcin.stockmanagerapi.entity.Stock;

import java.util.List;
import java.util.Optional;


@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    public abstract Product productDtoToProduct(ProductDto productDto);

    abstract ProductDto productToProductDto(Product product,
                                            Long currentQuantity,
                                            Long availableQuantity,
                                            Long reservedQuantity);

    public ProductDto productToProductDto(Product product) {
        Long currentQuantity = Optional.ofNullable(product.getStock())
                .stream()
                .flatMap(List::stream)
                .mapToLong(Stock::getCurrentQuantity)
                .sum();
        Long reservedQuantity = Optional.ofNullable(product.getOrderReservations())
                .stream()
                .flatMap(List::stream)
                .mapToLong(OrderReservation::getReservedQuantity)
                .sum();
        Long availableQuantity = currentQuantity - reservedQuantity;
        return productToProductDto(product, currentQuantity, availableQuantity, reservedQuantity);
    }
}