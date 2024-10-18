package com.example.unittestdemo.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SortDirection {

    ASC("asc"),
    DESC("desc");

    private final String direction;

    SortDirection(String direction){
        this.direction = direction;
    }

    @JsonValue
    public String getDirection() {
        return direction;
    }

    @JsonCreator
    public static SortDirection fromValue(String value) {
        for (SortDirection contact : values()) {
            String currentContact = contact.getDirection();
            if (currentContact.equals(value)) {
                return contact;
            }
        }

        // Return a response entity with a 400 Bad Request status
        throw new IllegalArgumentException("Invalid value for Contact type Enum: " + value);
    }

}
