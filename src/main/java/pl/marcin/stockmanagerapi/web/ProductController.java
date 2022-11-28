package pl.marcin.stockmanagerapi.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.marcin.stockmanagerapi.entity.Product;
import pl.marcin.stockmanagerapi.services.ProductService;


@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@RequestBody Product product) {
        Product newProduct = productService.saveProduct(product);
        return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
    }


    @GetMapping("/{productId}")
    public ResponseEntity<?> getProjectById(@PathVariable String productId) {
        Product product = productService.findProductById(productId);

        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Product> getAllProducts() {
        return productService.findAllProducts();
    }

    @PatchMapping("{/productId}")
    public ResponseEntity<?> updateProduct(@RequestBody Product product, @PathVariable String productId) {
        Product updatedProduct = productService.updateProduct(product, productId);
        return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProject(@PathVariable String productId) {
        productService.deleteProductById(productId);
        return new ResponseEntity<String>("Product with ID" + productId + "was deleted", HttpStatus.OK);
    }


}

