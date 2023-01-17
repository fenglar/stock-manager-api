package pl.marcin.stockmanagerapi.services;

import org.springframework.stereotype.Service;
import pl.marcin.stockmanagerapi.dto.ProductDto;
import pl.marcin.stockmanagerapi.entity.Product;
import pl.marcin.stockmanagerapi.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> findAllProducts() {
           return productRepository.findAll().stream()
                .map(product -> new ProductDto(product.getId(), product.getName(), product.getQuantity()))
                .collect(Collectors.toList());
    }

    public Product saveProduct(Product newProduct) {
        return productRepository.save(newProduct);
    }

    public ProductDto updateProduct(ProductDto productDto, Long productId) {
        Product product = productRepository.findById(productId);
        product.setName(productDto.getName());
        product.setQuantity(productDto.getQuantity());

        return new ProductDto(productRepository.save(product));
    }

    public void deleteProductById(Long productId) {
        productRepository.delete(getByProductId(productId));
    }

    public ProductDto getByProductId(Long productId) {
        Product product = productRepository.findById(productId);
        return new ProductDto(product.getId(), product.getName(), product.getQuantity());
    }

}
