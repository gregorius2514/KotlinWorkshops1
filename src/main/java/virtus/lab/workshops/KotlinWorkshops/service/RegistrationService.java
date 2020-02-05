package virtus.lab.workshops.KotlinWorkshops.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import virtus.lab.workshops.KotlinWorkshops.model.dto.UserDto;
import virtus.lab.workshops.KotlinWorkshops.model.jpa.User;
import virtus.lab.workshops.KotlinWorkshops.repository.RoleRepository;
import virtus.lab.workshops.KotlinWorkshops.repository.UserRepository;

import java.util.HashSet;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void createAccount(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findByName(userDto.getUserType().name())));
        userRepository.save(user);
    }
}
