package pl.marcin.stockmanagerapi.job;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.marcin.stockmanagerapi.entity.Stock;
import pl.marcin.stockmanagerapi.repository.StockRepository;

import java.util.List;

@Component
public class StockUpdateWriter implements ItemWriter<StockUpdate> {
    @Autowired
    private StockRepository stockRepository;

    @Override
    public void write(List<? extends StockUpdate> stockUpdates) {
        for (StockUpdate item : stockUpdates) {
            Long productId = item.getId();
            Long calculatedQuantity = item.getQuantity();

            Stock stock = stockRepository.findByProductId(productId).orElse(null);
            if (stock == null) {
                stock = new Stock();
                stock.setProductId(productId);
                stock.setCurrentQuantity(calculatedQuantity);
            } else {
                Long currentQuantity = stock.getCurrentQuantity();
                Long newStock = currentQuantity + calculatedQuantity;
                stock.setCurrentQuantity(newStock);
            }
            stockRepository.save(stock);

        }
    }
}
