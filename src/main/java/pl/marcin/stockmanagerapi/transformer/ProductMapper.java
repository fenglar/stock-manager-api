package pl.marcin.stockmanagerapi.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.marcin.stockmanagerapi.dto.ProductDto;
import pl.marcin.stockmanagerapi.entity.Product;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper( ProductMapper.class );

    ProductDto productToProductDto(Product product);

}
