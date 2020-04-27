package com.example.demo.controller;

import com.example.demo.mapper.SensorMapper;
import com.example.demo.mapper.Sensor_typeMapper;
import com.example.demo.pojo.Sensor;
import com.example.demo.pojo.Sensor_type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8090", maxAge = 3600)
@RestController
public class SensorController {
    @Autowired
    SensorMapper sensorMapper;
    @Autowired
    Sensor_typeMapper sensor_typeMapper;

    @RequestMapping("/findsensorbypage/{page}")
    public List<Sensor> findsenorbypage(@PathVariable int page){
        int begin = 8*(page - 1);
        List<Sensor> sensorbypage= sensorMapper.findsensorbypage(begin);
        return  sensorbypage;
    }
    @RequestMapping("/deleteSensor/{sensor_ID}")
    public boolean deleteSensor(@PathVariable int sensor_ID){
        boolean check;
        try {
            check = sensorMapper.deleteSensor(sensor_ID);
            return check;
        }catch (Exception e){
            return false;
        }
    }
    @RequestMapping("/findsensortype/{type_ID}")
    public String findtype_namebyid(@PathVariable int type_ID){
        try {
            String type_name = sensorMapper.findtype_namebyid(type_ID);
            return type_name;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @GetMapping("/findallsensortype")
    public List<Sensor_type> findallsensortype(){
        List<Sensor_type>  findallsensortype= sensor_typeMapper.findallsensortype();
        return findallsensortype;
    }

    @RequestMapping("/updateSensor/{sensor_ID}/{sensor_name}")
    public boolean updateSensorname(@PathVariable int sensor_ID,@PathVariable String sensor_name){
        boolean check;
        try{
            check = sensorMapper.updateSensor(sensor_ID,sensor_name);
            return check;
        }catch (Exception e){
            return false;
        }
    }
    @RequestMapping("/insertsensor/{stm32_IPv6}/{type_ID}/{sensor_state}")
    public boolean addasensor(@PathVariable String stm32_IPv6,@PathVariable int type_ID,
                              @PathVariable boolean sensor_state){
        boolean check;
        Sensor sensor = new Sensor(stm32_IPv6,type_ID,sensor_state);
        try{
            check = sensorMapper.addasensor(sensor);
            return check;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
    @GetMapping("/findallsensor")
    public List<Sensor> findallsensor(){
        try{
            List<Sensor> allsensor = sensorMapper.findallsensor();
            return allsensor;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    @RequestMapping("/findsensor_namebyid/{sensor_ID}")
    public String findsensor_namebyid(@PathVariable int sensor_ID){
        try {
            String findsensor_namebyid = sensorMapper.findsensor_namebyid(sensor_ID);
            return findsensor_namebyid;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}
