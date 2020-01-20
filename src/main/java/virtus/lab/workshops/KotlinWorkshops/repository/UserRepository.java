package virtus.lab.workshops.KotlinWorkshops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import virtus.lab.workshops.KotlinWorkshops.model.jpa.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
}
