package pl.marcin.stockmanagerapi.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
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
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(maxAttemptsExpression = "${app.retry.count:5}", backoff = @Backoff(delay = 0))
    public void processCSVLineRepeatableRead(Long productId, Long quantity) {
        log.trace("Starting thread [{}], with productId [{}] and quantity [{}]", Thread.currentThread().getName(), productId, quantity);
        Stock stock = stockRepository.findByProductId(productId).orElse(
                Stock.builder()
                        .productId(productId)
                        .currentQuantity(0L)
                        .build());
        stock.setCurrentQuantity(stock.getCurrentQuantity() + quantity);
        stockRepository.save(stock);
        log.trace("Finishing thread [{}], with productId [{}] and quantity [{}]", Thread.currentThread().getName(), productId, quantity);
    }

    @Transactional
    public void processCSVLinePessimisticWriterLock(Long productId, Long quantity) {
        try {
            log.trace("Starting thread [{}], with productId [{}] and quantity [{}]", Thread.currentThread().getName(), productId, quantity);
            Stock stock = stockRepository.findByProductIdPessimisticWriterLock(productId).orElse(
                    Stock.builder()
                            .productId(productId)
                            .currentQuantity(0L)
                            .build());
            stock.setCurrentQuantity(stock.getCurrentQuantity() + quantity);
            stockRepository.save(stock);
            log.trace("Finishing thread [{}], with productId [{}] and quantity [{}]", Thread.currentThread().getName(), productId, quantity);
        } catch (Exception e) {
            log.trace("############# Error on process stock Message [{}].", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void processCSVLineOptimisticLock(Long productId, Long quantity) {
        try {
            log.trace("Starting thread [{}], with productId [{}] and quantity [{}]", Thread.currentThread().getName(), productId, quantity);
            Stock stock = stockRepository.findByProductIdOptimisticLock(productId).orElse(
                    Stock.builder()
                            .productId(productId)
                            .currentQuantity(0L)
                            .build());
            stock.setCurrentQuantity(stock.getCurrentQuantity() + quantity);
            stockRepository.save(stock);
            log.trace("Finishing thread [{}], with productId [{}] and quantity [{}]", Thread.currentThread().getName(), productId, quantity);
        } catch (Exception e) {
            log.trace("############# Error on process stock Message [{}].", e.getMessage(), e);
            throw e;
        }
    }
}
