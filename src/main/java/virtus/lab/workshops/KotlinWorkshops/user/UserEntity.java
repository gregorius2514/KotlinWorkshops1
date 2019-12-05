package virtus.lab.workshops.KotlinWorkshops.user;

import java.util.Objects;

class UserEntity {

    private String login;
    private char[] password;
    private boolean active;

    public UserEntity(String login, char[] password, boolean active) {
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
