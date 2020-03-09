package com.virtuslab.workshops.kotlin.run;

import com.virtuslab.workshops.kotlin.user.model.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "run")
@SequenceGenerator(name = "runsSequenceGenerator", sequenceName = "run_seq", allocationSize = 1)
public class Run {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "runsSequenceGenerator")
    private Integer id;

    // FIXME [szymczuch] Add javax.persistence annotations
    private String place;

    private String name;

    private String description;

    private LocalDate date;

    private LocalTime startTime;

    @ManyToMany(mappedBy = "runsInWhichParticipates",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<User> participants = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

    private Integer distanceInMeters;

    private Integer participantsCapacity;

    public Run() {
    }

    public Run(String place, String name, String description, User creator, Integer distanceInMeters, Integer participantsCapacity, LocalDate date, LocalTime startTime) {
        this.place = place;
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.distanceInMeters = distanceInMeters;
        this.participantsCapacity = participantsCapacity;
        this.date = date;
        this.startTime = startTime;
    }

    public Run addParticipant(User user) {
        participants.add(user);
        user.participateInRun(this);
        return this;
    }

    public Integer getDistanceInMeters() {
        return distanceInMeters;
    }

    public void setDistanceInMeters(Integer distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Integer getParticipantsCapacity() {
        return participantsCapacity;
    }

    public void setParticipantsCapacity(Integer participantsCapacity) {
        this.participantsCapacity = participantsCapacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Integer getPlacesLeft() {
        return getParticipantsCapacity() - this.participants.size();
    }
}
