package virtus.lab.workshops.KotlinWorkshops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import virtus.lab.workshops.KotlinWorkshops.model.jpa.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findByName(String name);
}
