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


@Service
@RequiredArgsConstructor
public class StockService {
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final ProductMapper productMapper;
    private final StockMapper stockMapper;

    public StockDto getStock(Long productId) {

        Stock stock = stockRepository.findByProduct(productId);
        return stockMapper.StockToStockDto(stock);
    }

    @Transactional
    public StockDto reserveQuantity(Long productId, Long quantity) {
        Stock stockOfProduct = stockRepository.findByProduct(productId);
        stockOfProduct.setReservedQuantity(stockOfProduct.getReservedQuantity() + quantity);

        return stockMapper.StockToStockDto(stockOfProduct);
    }
}