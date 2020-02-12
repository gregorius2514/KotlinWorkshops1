package com.virtuslab.workshops.kotlin.user.model;

import com.virtuslab.workshops.kotlin.run.Run;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@SequenceGenerator(name = "usersSequenceGenerator", sequenceName = "users_seq", allocationSize = 1)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersSequenceGenerator")
    private Integer id;

    @Size(min = 5, max = 200, message = "{user.email.size}")
    @Column(length = 200, unique = true, nullable = false)
    private String email;

    @Size(min = 1, max = 60, message = "{user.firstName.size}")
    @Column(length = 60, nullable = false)
    private String firstName;

    @Size(min = 1, max = 60, message = "{user.lastName.size}")
    @Column(length = 60, nullable = false)
    private String lastName;

    @Size(min = 1, max = 80, message = "{user.password.size}")
    @Column(length = 80, nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY
    )
    @JoinTable(name = "user_run",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "run_id")
    )
    private Set<Run> runsInWhichParticipates = new HashSet<>();

    @OneToMany(
            mappedBy = "creator",
            cascade = CascadeType.ALL)
    private Set<Run> ownedRuns = new HashSet<>();

    public User participateInRun(Run run) {
        runsInWhichParticipates.add(run);
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Run> getRunsInWhichParticipates() {
        return runsInWhichParticipates;
    }

    public void setRunsInWhichParticipates(Set<Run> runsInWhichParticipates) {
        this.runsInWhichParticipates = runsInWhichParticipates;
    }

    public Set<Run> getOwnedRuns() {
        return ownedRuns;
    }

    public void setOwnedRuns(Set<Run> ownedRuns) {
        this.ownedRuns = ownedRuns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}