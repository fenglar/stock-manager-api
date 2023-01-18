package pl.marcin.stockmanagerapi.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.marcin.stockmanagerapi.dto.ProductDto;
import pl.marcin.stockmanagerapi.entity.Product;
import pl.marcin.stockmanagerapi.mapper.ProductMapper;
import pl.marcin.stockmanagerapi.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private ProductMapper productMapper;

    ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> findAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::productToProductDto)
                .collect(Collectors.toList());
    }

    public Product saveProduct(ProductDto productDto) {
        Product newProduct = productMapper.productDtoToProduct(productDto);

        return productRepository.save(newProduct);
    }

    public ProductDto updateProduct(ProductDto productDto, Long productId) {
        Product product = productMapper.productDtoToProduct(productDto);
        Product updatedProduct = productRepository.save(product);

        return productMapper.productToProductDto(updatedProduct);
    }

    @Transactional
    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }

    public ProductDto getByProductId(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return productMapper.productToProductDto(product.get());
    }

}
