package pl.marcin.stockmanagerapi.it;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import pl.marcin.stockmanagerapi.StockManagerApiApplication;
import pl.marcin.stockmanagerapi.config.TestContainerInitializer;
import pl.marcin.stockmanagerapi.entity.Product;
import pl.marcin.stockmanagerapi.entity.Stock;
import pl.marcin.stockmanagerapi.repository.ProductRepository;
import pl.marcin.stockmanagerapi.repository.StockRepository;
import pl.marcin.stockmanagerapi.services.DocumentReaderService;
import pl.marcin.stockmanagerapi.services.FileProcessorService;

import java.io.IOException;
import java.io.InputStream;

@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = {StockManagerApiApplication.class}, initializers = {TestContainerInitializer.class})
public class DocumentReaderServiceIntegrationTest {
    @Autowired
    private DocumentReaderService documentReaderService;

    @Autowired
    private FileProcessorService fileProcessorService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    public void tearDown() {
        stockRepository.deleteAll();
    }

    @Test
    public void testReadCSVAndProcess() throws IOException {
        //given
        ClassPathResource resource = new ClassPathResource("csv-mocks/stock-update.csv");
        InputStream inputStream = resource.getInputStream();

        //when
        documentReaderService.readCSV(inputStream);

        //then
        Stock stock1 = stockRepository.findByProductId(1L).orElse(null);
        Assertions.assertNotNull(stock1);
        Assertions.assertEquals(15L, stock1.getCurrentQuantity());

        Stock stock2 = stockRepository.findByProductId(2L).orElse(null);
        Assertions.assertNotNull(stock2);
        Assertions.assertEquals(23L, stock2.getCurrentQuantity());

        Stock stock3 = stockRepository.findByProductId(3L).orElse(null);
        Assertions.assertNotNull(stock3);
        Assertions.assertEquals(18L, stock3.getCurrentQuantity());
    }

}
