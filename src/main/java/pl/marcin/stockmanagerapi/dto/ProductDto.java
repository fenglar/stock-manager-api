package pl.marcin.stockmanagerapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.marcin.stockmanagerapi.entity.Product;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private Long quantity;

    public ProductDto(Product updatedProduct) {

    }
}
