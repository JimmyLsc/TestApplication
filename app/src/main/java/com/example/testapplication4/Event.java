package com.example.testapplication4;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event {
    private String name;
    private People owner;
    private float fee;

    public Event(String name, People owner, float fee) {
        this.name = name;
        this.owner = owner;
        this.fee = fee;
    }
    public Event(){
    }
}
