package pl.marcin.stockmanagerapi.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcin.stockmanagerapi.security.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
