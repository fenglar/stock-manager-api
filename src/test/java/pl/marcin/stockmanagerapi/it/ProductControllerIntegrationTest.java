package pl.marcin.stockmanagerapi.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import pl.marcin.stockmanagerapi.StockManagerApiApplication;
import pl.marcin.stockmanagerapi.config.TestContainerInitializer;
import pl.marcin.stockmanagerapi.dto.ProductDto;
import pl.marcin.stockmanagerapi.entity.Product;
import pl.marcin.stockmanagerapi.repository.ProductRepository;
import pl.marcin.stockmanagerapi.services.ProductService;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {StockManagerApiApplication.class},
        initializers = {TestContainerInitializer.class})
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
@Transactional
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testCreateNewProduct() throws Exception {
        ProductDto productDto = new ProductDto(3L, "Test Product 3", BigDecimal.TEN, 30L, 10L, 20L);

        ResultActions resultActions = mockMvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)));

        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(productDto.name()))
                .andExpect(jsonPath("$.price").value(productDto.price()));

    }

    @Test
    void testGetProductById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(10L));
    }

    @Test
    void testGetAllProducts() throws Exception {
        List<Product> allProducts = productRepository.findAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(allProducts.size())))
                .andExpect(jsonPath("$[0].name").value("Test Product"))
                .andExpect(jsonPath("$[0].price").value(10L))
                .andExpect(jsonPath("$[1].name").value("Test Product 2"))
                .andExpect(jsonPath("$[1].price").value(20L));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/product/2"))
                .andExpect(status().isOk());
    }
}
