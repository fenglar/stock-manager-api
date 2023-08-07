package pl.marcin.stockmanagerapi.services;


import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.marcin.stockmanagerapi.entity.Stock;
import pl.marcin.stockmanagerapi.repository.StockRepository;

@EnableAsync
@Transactional
@Service
public class FileProcessorService {

    private final StockRepository stockRepository;

    public FileProcessorService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

//async
    public void processCSVLine(Long productId, Long quantity) {
        Stock stock = stockRepository.findByProductId(productId).orElse(
                Stock.builder()
                        .productId(productId)
                        .currentQuantity(0L)
                        .build());
        stock.setCurrentQuantity(stock.getCurrentQuantity() + quantity);
        stockRepository.save(stock);
    }
}
