package pl.marcin.stockmanagerapi.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.marcin.stockmanagerapi.StockManagerApiApplication;
import pl.marcin.stockmanagerapi.config.TestContainerInitializer;
import pl.marcin.stockmanagerapi.dto.ProductDto;
import pl.marcin.stockmanagerapi.entity.Product;
import pl.marcin.stockmanagerapi.repository.ProductRepository;
import pl.marcin.stockmanagerapi.services.ProductService;

import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {StockManagerApiApplication.class},
        initializers = {TestContainerInitializer.class})
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
//explain profiles
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
    public void testCreateNewProduct() throws Exception {

        ResultActions resultActions = mockMvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ProductDto(3L, "Test Product 3", 30L))));

        resultActions.andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.name", is("Test Product3")))
                .andExpect((ResultMatcher) jsonPath("$.quantity", is(30L)));

    }

    @Test
    public void testGetProductById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.name", is("Test Product")))
                .andExpect((ResultMatcher) jsonPath("$.quantity", is(10L)));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        List<Product> allProducts = productRepository.findAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/all"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$", hasSize(allProducts.size())))
                .andExpect((ResultMatcher) jsonPath("$[0].name", is("Test Product 1")))
                .andExpect((ResultMatcher) jsonPath("$[0].quantity", is(10L)))
                .andExpect((ResultMatcher) jsonPath("$[1].name", is("Test Product 2")))
                .andExpect((ResultMatcher) jsonPath("$[1].quantity", is(20L)));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/2"))
                .andExpect(status().isOk());
    }
}
