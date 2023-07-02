package pl.marcin.stockmanagerapi.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.marcin.stockmanagerapi.dto.StockDto;
import pl.marcin.stockmanagerapi.services.StockService;
import pl.marcin.stockmanagerapi.web.StockController;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StockController.class)
@AutoConfigureMockMvc
public class StockControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StockService stockService;


    @Test
    void testGetStockByProductId() throws Exception {
        StockDto stockDto = new StockDto(1L, 1L, 10L);

        when(stockService.getStock(anyLong())).thenReturn(stockDto);

        mockMvc.perform(get("/api/stock/{productId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(stockDto.getId()))
                .andExpect(jsonPath("$.productId").value(stockDto.getProductId()))
                .andExpect(jsonPath("$.currentQuantity").value(stockDto.getCurrentQuantity()));

        verify(stockService, times(1)).getStock(anyLong());
    }

    @Test
    void testGetAllStocks() throws Exception {
        StockDto stockDto1 = new StockDto(1L, 1L, 10L);
        StockDto stockDto2 = new StockDto(2L, 2L, 20L);
        List<StockDto> stockList = Arrays.asList(stockDto1, stockDto2);

        when(stockService.getStocks()).thenReturn(stockList);

        mockMvc.perform(get("/api/stock/allstocks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(stockDto1.getId()))
                .andExpect(jsonPath("$[0].productId").value(stockDto1.getProductId()))
                .andExpect(jsonPath("$[0].currentQuantity").value(stockDto1.getCurrentQuantity()))
                .andExpect(jsonPath("$[1].id").value(stockDto2.getId()))
                .andExpect(jsonPath("$[1].productId").value(stockDto2.getProductId()))
                .andExpect(jsonPath("$[1].currentQuantity").value(stockDto2.getCurrentQuantity()));

        verify(stockService, times(1)).getStocks();
    }

    @Test
    void testCreateNewStock() throws Exception {
        StockDto stockDto = new StockDto(1L, 1L, 10L);

        when(stockService.createStock(any(StockDto.class))).thenReturn(stockDto);

        mockMvc.perform(post("/api/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stockDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(stockDto.getId()))
                .andExpect(jsonPath("$.productId").value(stockDto.getProductId()))
                .andExpect(jsonPath("$.currentQuantity").value(stockDto.getCurrentQuantity()));

        verify(stockService, times(1)).createStock(any(StockDto.class));
    }

    @Test
    void testUpdateStock() throws Exception {
        Long productId = 1L;
        Long quantity = 20L;
        StockDto updatedStockDto = new StockDto(1L, 1L, 20L);

        when(stockService.updateStock(anyLong(), anyLong())).thenReturn(updatedStockDto);

        mockMvc.perform(put("/api/stock/{productId}/{quantity}", productId, quantity))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedStockDto.getId()))
                .andExpect(jsonPath("$.productId").value(updatedStockDto.getProductId()))
                .andExpect(jsonPath("$.currentQuantity").value(updatedStockDto.getCurrentQuantity()));

        verify(stockService, times(1)).updateStock(anyLong(), anyLong());
    }

    @Test
    void testDeleteStock() throws Exception {
        Long productId = 1L;

        mockMvc.perform(delete("/api/stock/{productId}", productId))
                .andExpect(status().isNoContent());

        verify(stockService, times(1)).deleteStock(anyLong());
    }
}