package virtus.lab.workshops.KotlinWorkshops.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import virtus.lab.workshops.KotlinWorkshops.model.dto.UserDto;
import virtus.lab.workshops.KotlinWorkshops.model.jpa.Role;
import virtus.lab.workshops.KotlinWorkshops.model.jpa.User;
import virtus.lab.workshops.KotlinWorkshops.repository.RoleRepository;
import virtus.lab.workshops.KotlinWorkshops.repository.UserRepository;
import virtus.lab.workshops.KotlinWorkshops.service.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void createAccount(UserDto userDto) {
        User user = new User();
        user.setPassword(userDto.getPassword());
//        user.setUsername(userDto.getEmail());
//        user.setEnabled(true);
        userRepository.save(user);
        Role entity = new Role();
        entity.setId(2);
//        entity.setAuthority("ADMIN");
//        entity.setUsername(userDto.getEmail());
        roleRepository.save(entity);
    }
}
