package pl.marcin.stockmanagerapi.job;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class StockUpdateProcessor implements ItemProcessor<StockUpdate, StockUpdate> {

    private Map<Long, Long> productQuantityMap = new HashMap<>();

    @Override
    public StockUpdate process(StockUpdate item) {
        Long productId = item.getProductId();
        Long quantity = item.getQuantity();

        if (productQuantityMap.containsKey(productId)) {
            Long totalQuantity = productQuantityMap.get(productId) + quantity;
            productQuantityMap.put(productId, totalQuantity);
        } else {
            productQuantityMap.put(productId, quantity);
        }

        item.setQuantity(productQuantityMap.get(productId));

        return item;
    }


}

