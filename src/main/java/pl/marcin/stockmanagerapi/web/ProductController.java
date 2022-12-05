package pl.marcin.stockmanagerapi.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.marcin.stockmanagerapi.dto.ProductDto;
import pl.marcin.stockmanagerapi.entity.Product;
import pl.marcin.stockmanagerapi.services.ProductService;


@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<Product> createNewProject(@RequestBody Product product) {
        Product newProduct = productService.saveProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }


    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProjectById(@PathVariable String productId) {
        Product product = productService.getByProductId(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Product> getAllProducts() {
        return productService.findAllProducts();
    }

    @PatchMapping("{/productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable String productId) {
        ProductDto updatedProduct = productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProject(@PathVariable String productId) {
        productService.deleteProductById(productId);
        return new ResponseEntity<String>("Product with ID" + productId + "was deleted", HttpStatus.OK);
    }


}

