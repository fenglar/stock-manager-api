package pl.marcin.stockmanagerapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.stockmanagerapi.entity.Product;
import pl.marcin.stockmanagerapi.repository.ProductRepository;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Iterable<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product newProduct) {
        newProduct.setName(newProduct.getName());
        return productRepository.save(newProduct);
    }

    public Product updateProduct(Product updatedProduct, String productId){
        Product product = findProductById(productId);
        product = updatedProduct;
        return saveProduct(product);
    }

    public void deleteProductById(String productId){
        productRepository.delete(findProductById(productId));
    }

    public Product findProductById(String productId) {
        Product product = productRepository.findByProductId(productId.toUpperCase());
        return product;
    }

}
