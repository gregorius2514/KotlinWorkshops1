package virtus.lab.workshops.KotlinWorkshops.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import virtus.lab.workshops.KotlinWorkshops.model.jpa.User;
import virtus.lab.workshops.KotlinWorkshops.repository.RoleRepository;
import virtus.lab.workshops.KotlinWorkshops.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
