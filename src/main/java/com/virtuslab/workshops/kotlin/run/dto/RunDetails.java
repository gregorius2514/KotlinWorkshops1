package com.virtuslab.workshops.kotlin.run.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class RunDetails {
    private final Integer id;
    private final String place;
    private final String name;
    private final String description;
    private final LocalDate date;
    private final LocalTime startTime;
    private final Integer distance;
    private final Integer placesLeft;

    public RunDetails(Integer id, String place, String name, String description, LocalDate date, LocalTime startTime, Integer distance, Integer placesLeft) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(place);
        Objects.requireNonNull(name);
        Objects.requireNonNull(description);
        Objects.requireNonNull(date);
        Objects.requireNonNull(startTime);
        Objects.requireNonNull(distance);
        Objects.requireNonNull(placesLeft);
        this.id = id;
        this.place = place;
        this.name = name;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.distance = distance;
        this.placesLeft = placesLeft;
    }

    public Integer getId() {
        return id;
    }

    public String getPlace() {
        return place;
    }

    public Integer getPlacesLeft() {
        return placesLeft;
    }

    public Integer getDistance() {
        return distance;
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
}