package pl.marcin.stockmanagerapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.marcin.stockmanagerapi.dto.ProductDto;
import pl.marcin.stockmanagerapi.entity.OrderReservation;
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

    @Test
    void shouldFindAllProducts() {
        //given

        List<Product> products = Arrays.asList(
                new Product(1L, "Product 1", BigDecimal.TEN, List.of(), List.of()),
                new Product(2L, "Product 2", BigDecimal.valueOf(20), List.of(), List.of())
        );

        List<Stock> stock1 = Arrays.asList(
                new Stock(1L, 5L, products.get(0)),
                new Stock(2L, 5L, products.get(0))
        );
        List<Stock> stock2 = Arrays.asList(
                new Stock(3L, 10L, products.get(1)),
                new Stock(4L, 10L, products.get(1))
        );
        products.get(0).setStock(stock1);
        products.get(1).setStock(stock2);

        List<OrderReservation> orderReservations1 = Arrays.asList(
                new OrderReservation(1L, 2L, products.get(0)),
                new OrderReservation(2L, 3L, products.get(0))
        );
        List<OrderReservation> orderReservations2 = Arrays.asList(
                new OrderReservation(3L, 5L, products.get(1)),
                new OrderReservation(4L, 7L, products.get(1))
        );
        products.get(0).setOrderReservations(orderReservations1);
        products.get(1).setOrderReservations(orderReservations2);

        when(productRepository.findAll()).thenReturn(products);

        ProductDto productDto1 = new ProductDto(1L, "Product 1", BigDecimal.TEN, 10L, 5L, 5L);
        ProductDto productDto2 = new ProductDto(2L, "Product 2", BigDecimal.valueOf(20), 20L, 12L, 8L);
        when(productMapper.productToProductDto(any(Product.class))).thenReturn(productDto1, productDto2);
        //when
        List<ProductDto> result = productService.findAllProducts();

        //then
        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).name());
        assertEquals(10L, result.get(0).currentQuantity());
        assertEquals(5L, result.get(0).reservedQuantity());
        assertEquals(5L, result.get(0).availableQuantity());
        assertEquals("Product 2", result.get(1).name());
        assertEquals(20L, result.get(1).currentQuantity());
        assertEquals(12L, result.get(1).reservedQuantity());
        assertEquals(8L, result.get(1).availableQuantity());
    }

    @Test
    void shouldSaveProduct() {
        //given
        ProductDto productDto = new ProductDto(1L, "Product 1", BigDecimal.TEN, 10L, 5L, 5L);
        Product product = new Product(1L, "Product 1", BigDecimal.valueOf(10), List.of(), List.of());

        List<Stock> stock = Arrays.asList(
                new Stock(1L, 5L, product),
                new Stock(2L, 5L, product)
        );
        product.setStock(stock);

        List<OrderReservation> orderReservations = Arrays.asList(
                new OrderReservation(1L, 2L, product),
                new OrderReservation(2L, 3L, product)
        );

        product.setOrderReservations(orderReservations);

        when(productMapper.productDtoToProduct(productDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.productToProductDto(product)).thenReturn(productDto);

        //when
        ProductDto result = productService.saveProduct(productDto);

        //then
        assertEquals(1L, result.id());
        assertEquals("Product 1", result.name());
        assertEquals(10, result.currentQuantity());
        assertEquals(5, result.reservedQuantity());
        assertEquals(5, result.availableQuantity());
    }

    @Test
    void shouldUpdateProduct() {
        //given
        ProductDto productDto = new ProductDto(null, "Product 1 update", BigDecimal.valueOf(20), 20L, 5L, 15L);
        Product expectedProduct = new Product(1L, "Product 1", BigDecimal.TEN, List.of(), List.of());

        List<Stock> stock = Arrays.asList(
                new Stock(1L, 10L, expectedProduct),
                new Stock(2L, 10L, expectedProduct)
        );
        expectedProduct.setStock(stock);

        List<OrderReservation> orderReservations = Arrays.asList(
                new OrderReservation(1L, 2L, expectedProduct),
                new OrderReservation(2L, 3L, expectedProduct)
        );
        expectedProduct.setOrderReservations(orderReservations);


        when(productRepository.findById(1L)).thenReturn(Optional.of(expectedProduct));
        expectedProduct.setName("Product 1 update");
        expectedProduct.setPrice(BigDecimal.valueOf(20));
        lenient().when(productRepository.save(any())).thenReturn(expectedProduct);
        ProductDto expectedProductDto = new ProductDto(1L, "Product 1 update", BigDecimal.valueOf(20), 20L, 5L, 15L);
        when(productMapper.productToProductDto(any())).thenReturn(expectedProductDto);

        //when
        ProductDto actualProductDto = productService.updateProduct(productDto, 1L);

        //then
        assertEquals(expectedProductDto, actualProductDto);
    }

    @Test
    void shouldDeleteProduct() {
        //given
        Long productId = 1L;

        //when
        productService.deleteProductById(productId);

        //then
        assertThat(!productRepository.existsById(1L));
    }
}
