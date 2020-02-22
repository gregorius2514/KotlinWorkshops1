package com.virtuslab.workshops.kotlin.registration;


import com.virtuslab.workshops.kotlin.RoleBuilder;
import com.virtuslab.workshops.kotlin.UserBuilder;
import com.virtuslab.workshops.kotlin.user.RoleRepository;
import com.virtuslab.workshops.kotlin.user.UserRepository;
import com.virtuslab.workshops.kotlin.user.dto.AcceptableUserTypes;
import com.virtuslab.workshops.kotlin.user.dto.UserDto;
import com.virtuslab.workshops.kotlin.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.virtuslab.workshops.kotlin.user.dto.AcceptableUserTypes.ORGANIZER;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RegistrationServiceTest {

    private RegistrationService registrationService;
    private UserRepository userRepository;
    private List<User> inMemoryUsersDatabase = new ArrayList<>();

    @BeforeEach
    public void before() {
        inMemoryUsersDatabase.clear();
        userRepository = mock(UserRepository.class);

        RoleRepository roleRepository = mock(RoleRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

        registrationService = new RegistrationService(
                userRepository,
                roleRepository,
                passwordEncoder
        );

        mockUserSave();
        mockUserFindAll();
        mockUserFindByName(roleRepository);
        mockPasswordEncode(passwordEncoder);
    }

    @Test
    public void shouldCreateNewUser() {
        // given 
        UserDto user =
                createUserDto(
                        "test@example.com",
                        "Jan",
                        "Kowalski",
                        "111111",
                        ORGANIZER
                );

        List<User> expectedUsers = asList(
                UserBuilder.INSTANCE
                        .id(1)
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .roles(new HashSet(
                                RoleBuilder.INSTANCE
                                        .id(1)
                                        .name(ORGANIZER.name())
                                        .buildAsList()
                        ))
                        .build()
        );

        // when
        registrationService.createAccount(user);

        // then
        List<User> actualUsers = userRepository.findAll();
        assertEquals(expectedUsers, actualUsers);
    }

    private UserDto createUserDto(
            String email,
            String firstName,
            String lastName,
            String password,
            AcceptableUserTypes userType
    ) {
        UserDto user = new UserDto();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setUserType(userType);

        return user;
    }

    private void mockUserSave() {
        when(userRepository.save(any(User.class)))
                .thenAnswer(answer -> {
                    User user = answer.getArgument(0);
                    user.setId(1);
                    inMemoryUsersDatabase.add(user);

                    return user;
                });
    }

    private void mockUserFindAll() {
        when(userRepository.findAll()).thenReturn(inMemoryUsersDatabase);
    }

    private void mockUserFindByName(RoleRepository roleRepository) {
        when(roleRepository.findByName(any(String.class)))
                .thenReturn(RoleBuilder.INSTANCE
                        .id(1)
                        .name("TEST_ROLE")
                        .buildAsList());
    }

    private void mockPasswordEncode(PasswordEncoder passwordEncoder) {
        when(passwordEncoder.encode(any(String.class)))
                .thenReturn("password1234");
    }
}