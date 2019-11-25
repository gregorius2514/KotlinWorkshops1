package virtus.lab.workshops.KotlinWorkshops.user;

import java.util.Objects;

class UserDto {

    private final String login;
    private final char[] password;
    private final boolean active;

    UserDto(String login, char[] password, boolean active) {
        Objects.requireNonNull(login, "Expected not null login");
        Objects.requireNonNull(password, "Expected not null login");

        // TODO [szymczuch] I would leave it here as it's, because in kotlin that
        //  data class will look much, much better.
        //  Potential task to refactor on workshops
        if (password.length == 0) {
            throw new IllegalStateException("password cannot be empty");
        }
        if (login.isEmpty()) {
            throw new IllegalStateException("login cannot be empty");
        }

        this.login = login;
        this.password = password;
        this.active = active;
    }
}
