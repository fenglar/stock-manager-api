package pl.marcin.stockmanagerapi.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pl.marcin.stockmanagerapi.entity.Stock;
import pl.marcin.stockmanagerapi.repository.StockRepository;

@Service
@Slf4j
public class FileProcessorService {

    private final StockRepository stockRepository;

    public FileProcessorService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    //async
//    @Async
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void processCSVLine(Long productId, Long quantity) {
        try {
            log.info("Starting thread [{}], with productId [{}] and quantity [{}]", Thread.currentThread().getName(), productId, quantity);
            Stock stock = stockRepository.findByProductId(productId).orElse(
                    Stock.builder()
                            .productId(productId)
                            .currentQuantity(0L)
                            .build());
            stock.setCurrentQuantity(stock.getCurrentQuantity() + quantity);
            stockRepository.save(stock);
            log.info("Finishing thread [{}], with productId [{}] and quantity [{}]", Thread.currentThread().getName(), productId, quantity);
        } catch (Exception e) {
            log.error("############# Error on process stock Message [{}].", e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }
}
