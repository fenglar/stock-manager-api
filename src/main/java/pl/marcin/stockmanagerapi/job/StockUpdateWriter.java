package pl.marcin.stockmanagerapi.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import pl.marcin.stockmanagerapi.entity.Stock;
import pl.marcin.stockmanagerapi.repository.StockRepository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Slf4j
public class StockUpdateWriter implements ItemWriter<StockUpdate> {
    private Map<Long, Long> productQuantityMap = new HashMap<>();

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private DataSource dataSource;


    @Override
    public void write(List<? extends StockUpdate> stockUpdates) {
        for (StockUpdate item : stockUpdates) {
            Long productId = item.getProductId();
            Long quantity = item.getQuantity();

            if (productQuantityMap.containsKey(productId)) {
                Long totalQuantity = productQuantityMap.get(productId) + quantity;
                productQuantityMap.put(productId, totalQuantity);
            } else {
                productQuantityMap.put(productId, quantity);
            }

            Stock stock = stockRepository.findByProductId(productId).orElse(null);
            if (stock == null) {
                stock = new Stock();
                stock.setProductId(productId);
                stock.setCurrentQuantity(productQuantityMap.get(productId));
            } else {
                Long currentQuantity = stock.getCurrentQuantity();
                Long newStock = currentQuantity + productQuantityMap.get(productId);
                stock.setCurrentQuantity(newStock);
            }
            stockRepository.save(stock);
        }
        deleteProcessedRecords(stockUpdates);
    }

    private void deleteProcessedRecords(List<? extends StockUpdate> stockUpdates) {
        if (!stockUpdates.isEmpty()) {
            List<Long> idsToDelete = stockUpdates.stream()
                    .map(StockUpdate::getId)
                    .toList();

            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

            String deleteSql = "DELETE FROM stock_updates WHERE id IN (:ids)";
            Map<String, Object> paramMap = Collections.singletonMap("ids", idsToDelete);

            jdbcTemplate.update(deleteSql, paramMap);
        }
    }
}