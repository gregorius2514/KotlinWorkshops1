package com.virtuslab.workshops.kotlin.run.dto;

import java.util.Objects;

public class CreateRunRequest {
    private String place;
    private Integer distance;
    private Integer capacity;

    public CreateRunRequest() {
        this("", 0, 0);
    }

    public CreateRunRequest(String place, Integer distance, Integer capacity) {
        Objects.requireNonNull(place);
        Objects.requireNonNull(distance);
        Objects.requireNonNull(capacity);
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
                ", distance=" + distance +
                '}';
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}