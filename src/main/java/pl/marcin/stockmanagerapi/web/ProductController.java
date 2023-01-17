package pl.marcin.stockmanagerapi.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.marcin.stockmanagerapi.dto.ProductDto;
import pl.marcin.stockmanagerapi.entity.Product;
import pl.marcin.stockmanagerapi.services.ProductService;

import java.util.List;


@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<ProductDto> createNewProduct(@RequestBody ProductDto productDto) {
        Product product = new Product(productDto.getId(), productDto.getName(), productDto.getQuantity());
        product = productService.saveProduct(product);
        productDto = new ProductDto(product.getId(), product.getName(), product.getQuantity());
        return new ResponseEntity(productDto, HttpStatus.CREATED);
    }


    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {
        Product product = productService.getByProductId(productId);
        ProductDto productDto = new ProductDto(product.getId(), product.getName(), product.getQuantity());
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> allProducts = productService.findAllProducts();
        return ResponseEntity.ok(allProducts);
    }

    @PatchMapping("{/productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable Long productId) {
        Product updatedProduct = productService.updateProduct(new Product(productDto), productId);
        ProductDto result = new ProductDto(updatedProduct);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity deleteProduct(@PathVariable Long productId) {
        productService.deleteProductById(productId);
        return new ResponseEntity("Product with ID" + productId + "was deleted", HttpStatus.OK);
    }


}

