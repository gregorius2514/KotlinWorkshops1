package com.virtuslab.workshops.kotlin.run.dto;

import java.util.Objects;

public class RunDetails {
    private final Integer id;
    private final String place;
    private final Integer distance;
    private final Integer placesLeft;

    public RunDetails(Integer id, String place, Integer distance, Integer placesLeft) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(place);
        Objects.requireNonNull(distance);
        Objects.requireNonNull(placesLeft);
        this.id = id;
        this.place = place;
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
}