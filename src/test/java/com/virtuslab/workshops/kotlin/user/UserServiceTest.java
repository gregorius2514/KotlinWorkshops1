package com.virtuslab.workshops.kotlin.user;

import com.virtuslab.workshops.kotlin.RoleBuilder;
import com.virtuslab.workshops.kotlin.UserBuilder;
import com.virtuslab.workshops.kotlin.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    private void before() {
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        userService = new UserService(
                userRepository,
                roleRepository
        );

        final User adminUser = createAdminUser();
        mockUserFindByEmail(userRepository, adminUser);
        mockRoleAdminWithAttachedAdminUser(roleRepository, adminUser);
        mockRoleFindByName(101, "PARTICIPANT", roleRepository);
        mockRoleFindByName(102, "ORGANIZER", roleRepository);
    }


    @Test
    public void shouldReturnOneUserByEmail() {
        // given
        String expectedUserEmail = "admin@test.pl";
        User expectedUser = UserBuilder.INSTANCE
                .email(expectedUserEmail)
                .firstName("Jan")
                .lastName("Kowalski")
                .build();

        // when
        User actualUser = userService.getByEmail(expectedUserEmail).get();

        // then
        assertEquals(expectedUser, actualUser);
    }

    private User createAdminUser() {
        return UserBuilder.INSTANCE
                .id(1)
                .firstName("Jan")
                .lastName("Kowalski")
                .email("admin@test.pl")
                .build();
    }

    private void mockUserFindByEmail(UserRepository userRepository, User adminUser) {
        when(userRepository.findByEmail(adminUser.getEmail()))
                .thenReturn(
                        Optional.of(adminUser)
                );
    }

    private void mockRoleAdminWithAttachedAdminUser(RoleRepository roleRepository, User adminUser) {
        when(roleRepository.findByName("ADMIN"))
                .thenReturn(RoleBuilder.INSTANCE
                        .id(100)
                        .name("ADMIN")
                        .users(new HashSet(Arrays.asList(adminUser)))
                        .buildAsList());
    }

    private void mockRoleFindByName(int roleId, String participant, RoleRepository roleRepository) {
        when(roleRepository.findByName(participant))
                .thenReturn(RoleBuilder.INSTANCE
                        .id(roleId)
                        .name(participant)
                        .buildAsList());
    }
}

