package pl.marcin.stockmanagerapi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.marcin.stockmanagerapi.entity.Product;
import pl.marcin.stockmanagerapi.services.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public Iterable<Product> getAllProducts(){
        return productService.findAllProducts();
    }
}
