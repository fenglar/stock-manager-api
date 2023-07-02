package pl.marcin.stockmanagerapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.marcin.stockmanagerapi.dto.StockDto;
import pl.marcin.stockmanagerapi.entity.Product;
import pl.marcin.stockmanagerapi.entity.Stock;
import pl.marcin.stockmanagerapi.mapper.StockMapper;
import pl.marcin.stockmanagerapi.repository.StockRepository;
import pl.marcin.stockmanagerapi.services.StockService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockMapper stockMapper;

    @InjectMocks
    private StockService stockService;

    @Test
    public void shouldGetStockById() {
        //given
        Long productId = 1L;
        Stock stock = new Stock(1L, 10L, null);
        StockDto stockDto = new StockDto(1L, productId, 10L);

        when(stockRepository.findByProductId(productId)).thenReturn(stock);
        when(stockMapper.stockToStockDto(stock)).thenReturn(stockDto);

        //when
        StockDto result = stockService.getStock(productId);

        //then
        assertEquals(stockDto, result);
        verify(stockRepository, times(1)).findByProductId(productId);
        verify(stockMapper, times(1)).stockToStockDto(stock);
    }

    @Test
    void shouldGetStocks() {
        //given
        Stock stock1 = new Stock(1L, 10L, new Product());
        Stock stock2 = new Stock(2L, 5L, new Product());
        List<Stock> stocks = List.of(stock1, stock2);

        StockDto stockDto1 = new StockDto(1L, null, 10L);
        StockDto stockDto2 = new StockDto(2L, null, 5L);
        List<StockDto> expectedStockDtos = List.of(stockDto1, stockDto2);

        when(stockRepository.findAll()).thenReturn(stocks);
        when(stockMapper.stocksToStockDto(stocks)).thenReturn(expectedStockDtos);

        //when
        List<StockDto> actualStockDtos = stockService.getStocks();

        //then
        assertEquals(expectedStockDtos, actualStockDtos);
        verify(stockRepository, times(1)).findAll();
        verify(stockMapper, times(1)).stocksToStockDto(stocks);
    }

    @Test
    void shouldCreateStock() {
        // given
        StockDto stockDto = new StockDto(1L, 2L, 10L);

        Stock stock = new Stock(1L, 10L, null);
        Stock savedStock = new Stock(1L, 10L, null);

        when(stockMapper.stockDtoToStock(stockDto)).thenReturn(stock);
        when(stockRepository.save(stock)).thenReturn(savedStock);
        when(stockMapper.stockToStockDto(savedStock)).thenReturn(stockDto);

        // when
        StockDto actualStockDto = stockService.createStock(stockDto);

        // then
        assertEquals(stockDto, actualStockDto);
        verify(stockMapper, times(1)).stockDtoToStock(stockDto);
        verify(stockRepository, times(1)).save(stock);
        verify(stockMapper, times(1)).stockToStockDto(savedStock);
    }

    @Test
    void testUpdateStock() {
        // given
        Long productId = 1L;
        Long quantity = 20L;

        Stock stock = new Stock(1L, 10L, null);

        Stock updatedStock = new Stock(1L, quantity, null);

        StockDto expectedStockDto = new StockDto(1L, productId, quantity);

        when(stockRepository.findByProductId(productId)).thenReturn(stock);
        when(stockRepository.save(stock)).thenReturn(updatedStock);
        when(stockMapper.stockToStockDto(updatedStock)).thenReturn(expectedStockDto);

        // when
        StockDto actualStockDto = stockService.updateStock(productId, quantity);

        // then
        assertEquals(expectedStockDto, actualStockDto);
        verify(stockRepository, times(1)).findByProductId(productId);
        verify(stockRepository, times(1)).save(stock);
        verify(stockMapper, times(1)).stockToStockDto(updatedStock);
    }

    @Test
    void shouldDeleteStock() {
        //given
        Long productId = 1L;

        //when
        stockService.deleteStock(1L);
        //then
        assertThat(!stockRepository.existsById(1L));
    }
}
