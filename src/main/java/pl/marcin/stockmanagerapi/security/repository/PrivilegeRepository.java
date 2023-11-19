package pl.marcin.stockmanagerapi.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcin.stockmanagerapi.security.entity.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);
}
