package com.virtuslab.workshops.kotlin.user;

import com.virtuslab.workshops.kotlin.user.model.User;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
