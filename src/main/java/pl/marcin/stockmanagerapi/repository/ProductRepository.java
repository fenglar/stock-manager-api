package pl.marcin.stockmanagerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.marcin.stockmanagerapi.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findById(Long productId);
}
