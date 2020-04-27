package com.example.demo.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Sensor {
    private int sensor_ID;
    private String stm32_IPv6;
    private int type_ID;
    private boolean sensor_state;
    private String sensor_name;
    private int stm32_sensor_ID;


    public Sensor(String stm32_IPv6,int type_ID,boolean sensor_state){//用于前端批量添加
        this.stm32_IPv6 = stm32_IPv6;
        this.type_ID = type_ID;
        this.sensor_state = sensor_state;
        this.sensor_name = null;
    }


    public Sensor(String stm32_IPv6,int type_ID,int stm32_sensor_ID)
    {
        this.stm32_IPv6 = stm32_IPv6;
        this.type_ID = type_ID;
        this.sensor_state = true;
        this.sensor_name = null;
        this.stm32_sensor_ID = stm32_sensor_ID;
    }

    public int getSensor_ID() {
        return sensor_ID;
    }

    public void setSensor_ID(int sensor_ID) {
        this.sensor_ID = sensor_ID;
    }

    public String getStm32_IPv6() {
        return stm32_IPv6;
    }

    public void setStm32_IPv6(String stm32_IPv6) {
        this.stm32_IPv6 = stm32_IPv6;
    }

    public int getType_ID() {
        return type_ID;
    }

    public void setType_ID(int type_ID) {
        this.type_ID = type_ID;
    }

    public boolean isSensor_state() {
        return sensor_state;
    }

    public void setSensor_state(boolean sensor_state) {
        this.sensor_state = sensor_state;
    }

    public String getSensor_name() {
        return sensor_name;
    }

    public void setSensor_name(String sensor_name) {
        this.sensor_name = sensor_name;
    }

    public int getStm32_sensor_ID() {
        return stm32_sensor_ID;
    }

    public void setStm32_sensor_ID(int stm32_sensor_ID) {
        this.stm32_sensor_ID = stm32_sensor_ID;
    }
}
