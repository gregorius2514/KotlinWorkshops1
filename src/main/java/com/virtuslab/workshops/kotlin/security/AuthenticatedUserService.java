package com.virtuslab.workshops.kotlin.security;

import com.virtuslab.workshops.kotlin.user.UserService;
import com.virtuslab.workshops.kotlin.user.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthenticatedUserService {
    private final UserService userService;

    public AuthenticatedUserService(UserService userService) {
        Objects.requireNonNull(userService);
        this.userService = userService;
    }

    public Optional<User> authenticatedUser() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) principal;
            return userService.getByEmail(user.getUsername());
        }
        throw new IllegalStateException("Problem with authentication");
    }
}