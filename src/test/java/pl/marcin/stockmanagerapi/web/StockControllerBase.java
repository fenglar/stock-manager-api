package pl.marcin.stockmanagerapi.web;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import pl.marcin.stockmanagerapi.StockManagerApiApplication;
import pl.marcin.stockmanagerapi.config.TestContainerInitializer;
import pl.marcin.stockmanagerapi.dto.StockDto;
import pl.marcin.stockmanagerapi.services.StockService;
import wiremock.org.apache.hc.client5.http.auth.AuthStateCacheable;


@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = {StockManagerApiApplication.class}, initializers = {TestContainerInitializer.class})
abstract class StockControllerBase {

    @MockBean
    StockService stockService;
    @Autowired
    StockController stockController;

    @BeforeEach
    public void setupTest() {
        Mockito.when(stockService.getStock(1L))
                .thenReturn(StockDto.builder()
                        .id(123L)
                        .productId(1L)
                        .currentQuantity(2000L).build());

        RestAssuredMockMvc.standaloneSetup(stockController);
    }



}