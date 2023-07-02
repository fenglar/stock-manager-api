package pl.marcin.stockmanagerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcin.stockmanagerapi.entity.Stock;

import java.util.List;


public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByProductId(Long productId);
    List<Stock> findAll();
}
