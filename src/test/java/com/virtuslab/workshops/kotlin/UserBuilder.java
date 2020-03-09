package com.virtuslab.workshops.kotlin;

import com.virtuslab.workshops.kotlin.run.Run;
import com.virtuslab.workshops.kotlin.user.model.Role;
import com.virtuslab.workshops.kotlin.user.model.User;

import java.util.HashSet;
import java.util.Set;

public class UserBuilder {

    public static UserBuilder INSTANCE = new UserBuilder();

    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Set<Role> roles = new HashSet<>();
    private Set<Run> runsInWhichParticipates = new HashSet<>();
    private Set<Run> ownedRuns = new HashSet<>();

    private UserBuilder() {
    }

    public UserBuilder id(int id) {
        this.id = id;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public UserBuilder runsInWhichParticipates(Set<Run> runsInWhichParticipates) {
        this.runsInWhichParticipates = runsInWhichParticipates;
        return this;
    }

    public UserBuilder ownedRuns(Set<Run> ownedRuns) {
        this.ownedRuns = ownedRuns;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setRoles(roles);
        user.setOwnedRuns(ownedRuns);
        user.setRunsInWhichParticipates(runsInWhichParticipates);

        return user;
    }
}
