package pl.marcin.stockmanagerapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.marcin.stockmanagerapi.dto.StockDto;
import pl.marcin.stockmanagerapi.entity.Stock;
import pl.marcin.stockmanagerapi.mapper.ProductMapper;
import pl.marcin.stockmanagerapi.mapper.StockMapper;
import pl.marcin.stockmanagerapi.repository.ProductRepository;
import pl.marcin.stockmanagerapi.repository.StockRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StockService {
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final ProductMapper productMapper;
    private final StockMapper stockMapper;

    public StockDto getStock(Long productId) {
        Stock stock = stockRepository.findByProductId(productId).orElse(
        null);
        return stockMapper.stockToStockDto(stock);
    }

    public List<StockDto> getStocks() {
        List<Stock> stocks = stockRepository.findAll();
        return stockMapper.stocksToStockDto(stocks);
    }

    @Transactional
    public StockDto createStock(StockDto stockDto) {
        Stock stock = stockMapper.stockDtoToStock(stockDto);
        Stock savedStock = stockRepository.save(stock);

        return stockMapper.stockToStockDto(savedStock);
    }

    @Transactional
    public StockDto updateStock(Long productId, Long quantity) {
        Stock stock = stockRepository.findByProductId(productId).orElse(null);
        stock.setCurrentQuantity(quantity);
        Stock updatedStock = stockRepository.save(stock);

        return stockMapper.stockToStockDto(updatedStock);
    }

    @Transactional
    public void deleteStock(Long productId) {
        Stock stock = stockRepository.findByProductId(productId).orElse(null);
        stockRepository.delete(stock);
    }
}
