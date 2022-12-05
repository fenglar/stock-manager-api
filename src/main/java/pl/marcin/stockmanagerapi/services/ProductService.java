package pl.marcin.stockmanagerapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.stockmanagerapi.dto.ProductDto;
import pl.marcin.stockmanagerapi.entity.Product;
import pl.marcin.stockmanagerapi.repository.ProductRepository;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Iterable<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product newProduct) {
        return productRepository.save(newProduct);
    }

    public ProductDto updateProduct(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId);
        product.setName(productDto.getName());
        product.setQuantity(productDto.getQuantity());

        productRepository.save(product);
        return productDto;
    }

    public void deleteProductById(String productId){
        productRepository.delete(getByProductId(productId));
    }

    public Product getByProductId(String productId) {
        return productRepository.findById(productId);
    }

}
