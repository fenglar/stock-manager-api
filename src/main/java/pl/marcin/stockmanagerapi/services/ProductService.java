package pl.marcin.stockmanagerapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.stockmanagerapi.entity.Product;
import pl.marcin.stockmanagerapi.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Iterable<Product> findAllProducts(){
        return productRepository.findAll();
    }
}
