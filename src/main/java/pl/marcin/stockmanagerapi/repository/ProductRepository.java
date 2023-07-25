package pl.marcin.stockmanagerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import pl.marcin.stockmanagerapi.entity.Product;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
