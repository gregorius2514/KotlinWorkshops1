package virtus.lab.workshops.KotlinWorkshops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import virtus.lab.workshops.KotlinWorkshops.model.jpa.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
