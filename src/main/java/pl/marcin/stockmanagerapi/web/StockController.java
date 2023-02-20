package pl.marcin.stockmanagerapi.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.marcin.stockmanagerapi.dto.StockDto;
import pl.marcin.stockmanagerapi.services.StockService;

import java.util.Map;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockService stockService;

    StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<StockDto> getStockByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(stockService.getStock(productId));
    }

    @PostMapping("/{productId}/{quantity}")
    public ResponseEntity<StockDto> reserveQuantityOfProduct(@PathVariable Long productId, @PathVariable Long quantity) {
        return ResponseEntity.ok(stockService.reserveQuantity(productId, quantity));
    }

    @PatchMapping("/cancel")
    public ResponseEntity<String> updateQuantity(@RequestBody Map<Long, Long> cancelledProducts) {
        stockService.updateQuantity(cancelledProducts);
        return ResponseEntity.ok("Quantity of products that have been cancelled are back again in stock!");
    }

}
