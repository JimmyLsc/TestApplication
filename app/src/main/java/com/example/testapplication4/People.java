package com.example.testapplication4;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class People {
    private long id;
    private String name;
    private float output;
    private float predict;

    public People(String name){
        this.name = name;
        this.output = 0;
        this.predict = 0;
    }

    public void addOutput(float output) {
        this.output+= output;
    }
    public void addPredict(float predict) {
        this.predict += predict;
    }
    public People(){
    }
}
