package virtus.lab.workshops.KotlinWorkshops.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import virtus.lab.workshops.KotlinWorkshops.model.dto.UserDto;
import virtus.lab.workshops.KotlinWorkshops.model.jpa.User;
import virtus.lab.workshops.KotlinWorkshops.repository.RoleRepository;
import virtus.lab.workshops.KotlinWorkshops.repository.UserRepository;
import virtus.lab.workshops.KotlinWorkshops.service.RegistrationService;

import java.util.HashSet;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void createAccount(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
    }
}
