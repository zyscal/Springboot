package com.example.demo.pojo;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class STM32 {
    private String stm32_IPv6;
    private String stm32_name;
    private boolean state;
    private String router_IPv6;
    private int sensor_num;
    private String last_comm;
    private String register;
    private int power_ID;
    public  STM32(String stm32_IPv6,String stm32_name,boolean state,String router_IPv6,int sensor_num,int power_ID){
        this.stm32_name = stm32_name;
        this.stm32_IPv6 = stm32_IPv6;
        this.state = state;
        this.router_IPv6 = router_IPv6;
        this.sensor_num = sensor_num;
        this.power_ID = power_ID;
    }
    public STM32(String stm32_IPv6,boolean state,String router_IPv6,int sensor_num){
        this.stm32_IPv6 = stm32_IPv6;
        this.state = state;
        this.router_IPv6 = router_IPv6;
        this.sensor_num = sensor_num;
        this.power_ID = 1;//默认新设备在第一线路
        this.stm32_name = null;
    }

    public String getStm32_IPv6() {
        return stm32_IPv6;
    }

    public void setStm32_IPv6(String stm32_IPv6) {
        this.stm32_IPv6 = stm32_IPv6;
    }

    public String getStm32_name() {
        return stm32_name;
    }

    public void setStm32_name(String stm32_name) {
        this.stm32_name = stm32_name;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getRouter_IPv6() {
        return router_IPv6;
    }

    public void setRouter_IPv6(String router_IPv6) {
        this.router_IPv6 = router_IPv6;
    }

    public int getSensor_num() {
        return sensor_num;
    }

    public void setSensor_num(int sensor_num) {
        this.sensor_num = sensor_num;
    }

    public String getLast_comm() {
        return last_comm;
    }

    public void setLast_comm(String last_comm) {
        this.last_comm = last_comm;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public int getPower_ID() {
        return power_ID;
    }

    public void setPower_ID(int power_ID) {
        this.power_ID = power_ID;
    }
}
