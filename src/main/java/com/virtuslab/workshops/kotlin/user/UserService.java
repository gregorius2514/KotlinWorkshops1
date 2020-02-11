package com.virtuslab.workshops.kotlin.user;

import com.virtuslab.workshops.kotlin.user.model.User;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        Objects.requireNonNull(userRepository);
        Objects.requireNonNull(roleRepository);
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
