package pl.marcin.stockmanagerapi.mapper;

import org.mapstruct.Mapper;
import pl.marcin.stockmanagerapi.dto.ProductDto;
import pl.marcin.stockmanagerapi.entity.Product;

@Mapper
public interface ProductMapper {

    ProductDto productToProductDto(Product product);
}
