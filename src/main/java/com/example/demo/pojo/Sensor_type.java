package com.example.demo.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Sensor_type {
    private int type_ID;
    private String type_name;

    public int getType_ID() {
        return type_ID;
    }

    public void setType_ID(int type_ID) {
        this.type_ID = type_ID;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }
}
