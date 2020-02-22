package com.virtuslab.workshops.kotlin;

import com.virtuslab.workshops.kotlin.user.model.Role;
import com.virtuslab.workshops.kotlin.user.model.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoleBuilder {

    public static RoleBuilder INSTANCE = new RoleBuilder();

    private Integer id;
    private String name;
    private Set<User> users = new HashSet<>();

    private RoleBuilder() {
    }

    public RoleBuilder id(Integer id) {
        this.id = id;
        return this;
    }

    public RoleBuilder name(String name) {
        this.name = name;
        return this;
    }

    public RoleBuilder users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Role build() {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        role.setUsers(users);

        return role;
    }

    public List<Role> buildAsList() {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        role.setUsers(users);

        return Arrays.asList(role);
    }
}
