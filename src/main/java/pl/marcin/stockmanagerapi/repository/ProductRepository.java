package pl.marcin.stockmanagerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcin.stockmanagerapi.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAll();
}
