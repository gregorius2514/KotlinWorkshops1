package com.virtuslab.workshops.kotlin.user;

import com.virtuslab.workshops.kotlin.user.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
