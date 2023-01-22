package pl.marcin.stockmanagerapi.it;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.marcin.stockmanagerapi.repository.ProductRepository;
import pl.marcin.stockmanagerapi.services.ProductService;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    @Test
    public void testCreateNewProduct() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.name", is("Test Product")))
                .andExpect((ResultMatcher) jsonPath("$.quantity", is(10L)));
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

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/all"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$[0].name", is("Test Product 1")))
                .andExpect((ResultMatcher) jsonPath("$[0].quantity", is(10L)))
                .andExpect((ResultMatcher) jsonPath("$[1].name", is("Test Product 2")))
                .andExpect((ResultMatcher) jsonPath("$[1].quantity", is(20L)));
    }
}
