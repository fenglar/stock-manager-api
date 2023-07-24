package pl.marcin.stockmanagerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import pl.marcin.stockmanagerapi.entity.CSVData;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;


public interface DocumentDataRepository extends JpaRepository<CSVData, Long> {

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("SELECT csv FROM CSVData csv WHERE csv.productId = :productId")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "5000")})
     CSVData findByProductId(Long productId, LockModeType lockMode);



}
