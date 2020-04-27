package com.example.demo.controller;

import com.example.demo.mapper.Power_sensorMapper;
import com.example.demo.pojo.Power;
import com.example.demo.pojo.Power_sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8090", maxAge = 3600)
@RestController
public class Power_sensorController {
    @Autowired
    private Power_sensorMapper power_sensorMapper;
    @RequestMapping("/findpower_sensorbypower/{power_ID}")
    public List<Power_sensor> findpower_sensorbypower(@PathVariable int power_ID){
        try{
            List<Power_sensor> power_sensors = power_sensorMapper.findpower_sensorbypower(power_ID);
            return  power_sensors;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    @RequestMapping("/insertpower_sensor/{power_ID}/{sensor_ID}")
    public boolean insertpower_sensor(@PathVariable int power_ID,@PathVariable int sensor_ID){
        boolean check;
        try{
            check = power_sensorMapper.insertpower_sensor(power_ID,sensor_ID);
            return check;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
    @RequestMapping("/deletepower_sensor/{power_ID}/{sensor_ID}")
    public boolean deletepower_sensor(@PathVariable int power_ID,@PathVariable int sensor_ID){
        boolean check;
        try{
            check = power_sensorMapper.deletepower_sensor(power_ID,sensor_ID);
            return check;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
    @RequestMapping("/findallpower_sensor")
    public List<Power_sensor> findallpower_sensor(){
        try{
            List<Power_sensor> power_sensors = power_sensorMapper.findallpower_sensor();
            return power_sensors;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}
