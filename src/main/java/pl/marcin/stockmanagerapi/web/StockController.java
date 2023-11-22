package pl.marcin.stockmanagerapi.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.marcin.stockmanagerapi.dto.StockDto;
import pl.marcin.stockmanagerapi.entity.Stock;
import pl.marcin.stockmanagerapi.services.StockService;

import java.util.List;
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

    @Secured({"ROLE_USER"})
    @GetMapping("/allstocks")
    public ResponseEntity<List<StockDto>> getAllStocks() {
        return ResponseEntity.ok(stockService.getStocks());
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping()
    public ResponseEntity<StockDto> createNewStock(@RequestBody StockDto stockDto) {
        return ResponseEntity.ok(stockService.createStock(stockDto));
    }

    @PutMapping("/{productId}/{quantity}")
    public ResponseEntity<StockDto> updateStock(@PathVariable Long productId, @PathVariable Long quantity) {
        StockDto updatedStockDto = stockService.updateStock(productId, quantity);
        return ResponseEntity.ok(updatedStockDto);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long productId) {
        stockService.deleteStock(productId);
        return ResponseEntity.noContent().build();
    }
}
