package com.example.demo.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Power_sensor {
    private int power_ID;
    private int sensor_ID;

    public int getPower_ID() {
        return power_ID;
    }

    public void setPower_ID(int power_ID) {
        this.power_ID = power_ID;
    }

    public int getSensor_ID() {
        return sensor_ID;
    }

    public void setSensor_ID(int sensor_ID) {
        this.sensor_ID = sensor_ID;
    }
}
