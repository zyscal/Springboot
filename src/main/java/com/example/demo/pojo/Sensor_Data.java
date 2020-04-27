package com.example.demo.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
public class Sensor_Data {
    private double Sensor_data;
    private String Sensor_time;

    public double getSensor_data() {
        return Sensor_data;
    }

    public void setSensor_data(double sensor_data) {
        Sensor_data = sensor_data;
    }

    public String getSensor_time() {
        return Sensor_time;
    }

    public void setSensor_time(String sensor_time) {
        Sensor_time = sensor_time;
    }
}
