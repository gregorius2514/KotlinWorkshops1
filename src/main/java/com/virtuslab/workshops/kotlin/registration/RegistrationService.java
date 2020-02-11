package com.virtuslab.workshops.kotlin.registration;

import com.virtuslab.workshops.kotlin.user.RoleRepository;
import com.virtuslab.workshops.kotlin.user.UserRepository;
import com.virtuslab.workshops.kotlin.user.dto.UserDto;
import com.virtuslab.workshops.kotlin.user.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegistrationService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        Objects.requireNonNull(userRepository);
        Objects.requireNonNull(roleRepository);
        Objects.requireNonNull(bCryptPasswordEncoder);
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
