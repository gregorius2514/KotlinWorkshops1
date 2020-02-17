package com.virtuslab.workshops.kotlin.run.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class CreateRunRequest {
    private String place;
    private String name;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    private Integer distance;
    private Integer capacity;

    public CreateRunRequest() { }

    public CreateRunRequest(String place, String name, String description, LocalDate date, LocalTime startTime, Integer distance, Integer capacity) {
        Objects.requireNonNull(place);
        Objects.requireNonNull(name);
        Objects.requireNonNull(description);
        Objects.requireNonNull(date);
        Objects.requireNonNull(startTime);
        Objects.requireNonNull(distance);
        Objects.requireNonNull(capacity);
        this.name = name;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.place = place;
        this.distance = distance;
        this.capacity = capacity;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "CreateRunRequest{" +
                "place='" + place + '\'' +
                "name='" + name + '\'' +
                "description='" + description + '\'' +
                "date='" + date + '\'' +
                "start='" + startTime + '\'' +
                ", distance=" + distance +
                '}';
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
}