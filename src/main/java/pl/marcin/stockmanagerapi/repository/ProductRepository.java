package pl.marcin.stockmanagerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcin.stockmanagerapi.entity.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
