package pl.marcin.stockmanagerapi;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.marcin.stockmanagerapi.dto.ProductDto;
import pl.marcin.stockmanagerapi.dto.StockDto;
import pl.marcin.stockmanagerapi.entity.Product;
import pl.marcin.stockmanagerapi.entity.Stock;
import pl.marcin.stockmanagerapi.mapper.ProductMapper;
import pl.marcin.stockmanagerapi.repository.ProductRepository;
import pl.marcin.stockmanagerapi.services.ProductService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;
    @Mock
    ProductMapper productMapper;
    @InjectMocks
    ProductService productService;

    @Disabled
    @Test
    public void shouldFindAllProducts() {
        //given
        List<Product> products = Arrays.asList(
                new Product(1L, "Product 1", BigDecimal.TEN, List.of()),
                new Product(2L, "Product 2", BigDecimal.valueOf(20), List.of())
        );
        when(productRepository.findAll()).thenReturn(products);
        ProductDto productDto1 = new ProductDto(1L, "Product 1",BigDecimal.TEN, new StockDto());
        ProductDto productDto2 = new ProductDto(2L, "Product 2", BigDecimal.valueOf(20), new StockDto());
        when(productMapper.productToProductDto(any(Product.class))).thenReturn(productDto1, productDto2);
        //when
        List<ProductDto> result = productService.findAllProducts();

        //then
        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).name());
//        assertEquals(10, result.get(0).quantity());
        assertEquals("Product 2", result.get(1).name());
//        assertEquals(20, result.get(1).quantity());
    }

    @Disabled
    @Test
    public void shouldSaveProduct() {
        //given
        ProductDto productDto = new ProductDto(1L, "Product 1", BigDecimal.TEN, new StockDto());
        Product product = new Product(1L, "Product 1", BigDecimal.valueOf(10), List.of());
        when(productMapper.productDtoToProduct(productDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.productToProductDto(product)).thenReturn(productDto);

        //when
        ProductDto result = productService.saveProduct(productDto);

        //then
        assertEquals(1L, result.id());
        assertEquals("Product 1", result.name());
//        assertEquals(10, result.quantity());
    }

    @Disabled
    @Test
    public void shouldUpdateProduct() {
        //given
        ProductDto productDto = new ProductDto(null, "Product 1 update", BigDecimal.valueOf(20), new StockDto());
        Product expectedProduct = new Product(1L, "Product 1", BigDecimal.TEN, List.of());
        when(productRepository.findById(1L)).thenReturn(Optional.of(expectedProduct));
        expectedProduct.setName("Product 1 update");
        expectedProduct.setPrice(BigDecimal.valueOf(20));
//        expectedProduct.setQuantity(20L);
        lenient().when(productRepository.save(any())).thenReturn(expectedProduct);
        ProductDto expectedProductDto = new ProductDto(1L, "Product 1 update",BigDecimal.valueOf(20), new StockDto());
        when(productMapper.productToProductDto(any())).thenReturn(expectedProductDto);

        //when
        ProductDto actualProductDto = productService.updateProduct(productDto, 1L);

        //then
        assertEquals(expectedProductDto, actualProductDto);
    }

    @Disabled
    @Test
    public void shouldDeleteProduct() {
        //given
        Long productId = 1L;

        //when
        productService.deleteProductById(productId);

        //then
        assertThat(!productRepository.existsById(1L));
    }
}
