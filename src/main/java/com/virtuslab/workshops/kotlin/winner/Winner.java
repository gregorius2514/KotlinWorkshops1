package com.virtuslab.workshops.kotlin.winner;


import com.virtuslab.workshops.kotlin.run.Run;
import com.virtuslab.workshops.kotlin.user.model.User;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "winner")
public class Winner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "winnerSequenceGenerator")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "run_id")
    private Run run;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Winner() {
    }

    public Winner(Run run, User user) {
        this.run = run;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Run getRun() {
        return run;
    }

    public void setRun(Run run) {
        this.run = run;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Winner winner = (Winner) o;
        return id.equals(winner.id) &&
                run.equals(winner.run) &&
                user.equals(winner.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, run, user);
    }
}
