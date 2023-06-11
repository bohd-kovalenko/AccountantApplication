package com.java.coursework.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Index {
    private int index;

    @JsonCreator
    public Index(@JsonProperty("index") int index) {
        this.index = index;
    }
}
