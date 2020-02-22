package com.virtuslab.workshops.kotlin.run.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.StringJoiner;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RunDetails that = (RunDetails) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(place, that.place) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(date, that.date) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(distance, that.distance) &&
                Objects.equals(placesLeft, that.placesLeft);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, place, name, description, date, startTime, distance, placesLeft);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RunDetails.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("place='" + place + "'")
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .add("date=" + date)
                .add("startTime=" + startTime)
                .add("distance=" + distance)
                .add("placesLeft=" + placesLeft)
                .toString();
    }
}