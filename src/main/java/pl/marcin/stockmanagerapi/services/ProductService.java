package pl.marcin.stockmanagerapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.marcin.stockmanagerapi.dto.ProductDto;
import pl.marcin.stockmanagerapi.entity.Product;
import pl.marcin.stockmanagerapi.mapper.ProductMapper;
import pl.marcin.stockmanagerapi.repository.ProductRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public List<ProductDto> findAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::productToProductDto)
                .toList();
    }

    public ProductDto saveProduct(ProductDto productDto) {
        Product newProduct = productMapper.productDtoToProduct(productDto);

        return productMapper.productToProductDto(productRepository.save(newProduct));
    }

    public ProductDto updateProduct(ProductDto productDto, Long productId) {
        Product productToUpdate = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));
        productToUpdate.setName(productDto.name());
        productToUpdate.setPrice(productDto.price());
//        productToUpdate.setQuantity(productDto.quantity());

        return productMapper.productToProductDto(productToUpdate);
    }

    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }

    public ProductDto getByProductId(Long productId) {
        return productRepository.findById(productId)
                .map(productMapper::productToProductDto)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not exists"));
    }

}
