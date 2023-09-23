package pl.marcin.stockmanagerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.marcin.stockmanagerapi.entity.Stock;

import javax.persistence.LockModeType;
import java.util.Optional;


public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByProductId(Long productId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("FROM Stock WHERE productId = :productId")
    Optional<Stock> findByProductIdPessimisticWriterLock(@Param("productId") Long productId);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("FROM Stock WHERE productId = :productId")
    Optional<Stock> findByProductIdOptimisticLock(@Param("productId") Long productId);
}
