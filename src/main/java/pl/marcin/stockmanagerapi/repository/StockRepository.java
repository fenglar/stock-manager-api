package pl.marcin.stockmanagerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcin.stockmanagerapi.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByProduct(Long productId);
}
