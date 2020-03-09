package com.virtuslab.workshops.kotlin.user;

import com.virtuslab.workshops.kotlin.UserBuilder;
import com.virtuslab.workshops.kotlin.user.model.Role;
import com.virtuslab.workshops.kotlin.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDetailsServiceTest {

    private List<User> inMemoryUsersDatabase = new ArrayList<>();
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void before() {
        inMemoryUsersDatabase.clear();

        UserRepository userRepository = mock(UserRepository.class);
        userDetailsService = new UserDetailsServiceImpl(userRepository);

        mockUserFindByEmail(userRepository);
    }

    @Test
    public void shouldLoadUserByUsername() {
        // given
        String expectedUserEmail = "admin@test.com";
        User user = UserBuilder.getInstance()
                .id(1)
                .email(expectedUserEmail)
                .password("test1234")
                .build();

        createDummyTestData(user);
        UserDetails expectedUserDetails = convertToUserDetails(user);

        // when
        UserDetails actualUserDetails = userDetailsService.loadUserByUsername(expectedUserEmail);

        // then
        assertEquals(expectedUserDetails, actualUserDetails);
    }

    private void createDummyTestData(User expectedUser) {
        List<User> testUsers = Arrays.asList(
                UserBuilder.getInstance()
                        .email("test1@test.com")
                        .password("test1234")
                        .build(),
                UserBuilder.getInstance()
                        .email("test2@test.com")
                        .password("test1234")
                        .build(),
                UserBuilder.getInstance()
                        .email("test3@test.com")
                        .password("test1234")
                        .build(),
                UserBuilder.getInstance()
                        .email("test4@test.com")
                        .password("test1234")
                        .build(),
                UserBuilder.getInstance()
                        .email("test5@test.com")
                        .password("test1234")
                        .build()
        );

        inMemoryUsersDatabase.addAll(testUsers);
        inMemoryUsersDatabase.add(expectedUser);
    }

    private UserDetails convertToUserDetails(User user) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }

    private void mockUserFindByEmail(UserRepository userRepository) {
        when(userRepository.findByEmail(any(String.class)))
                .thenAnswer(answer -> {
                    String queryEmail = answer.getArgument(0);

                    return inMemoryUsersDatabase
                            .stream()
                            .filter(user -> user.getEmail().equals(queryEmail))
                            .findFirst();
                });
    }
}
