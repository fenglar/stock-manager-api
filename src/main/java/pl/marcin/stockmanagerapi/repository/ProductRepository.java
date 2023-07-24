package pl.marcin.stockmanagerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import pl.marcin.stockmanagerapi.entity.Product;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;


public interface ProductRepository extends JpaRepository<Product, Long> {

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("SELECT product FROM Product product WHERE product.productId = :productId")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "5000")})
    Product findByProductId(Long productId, LockModeType lockMode);
}
