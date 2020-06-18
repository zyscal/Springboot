package com.example.demo.controller;

import com.example.demo.mapper.SensorMapper;
import com.example.demo.mapper.Sensor_DataMapper;
import com.example.demo.pojo.Sensor;
import com.example.demo.pojo.Sensor_Data;
import javafx.scene.SnapshotParameters;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8090", maxAge = 3600)
@RestController
public class Sensor_Datacontroller {
    @Autowired
    private Sensor_DataMapper sensor_dataMapper;
    @Autowired
    private SensorMapper sensorMapper;

//    @RequestMapping(value = "/Sensor_Data/{STM32_IPv6}/all1281BCurrent",method = RequestMethod.GET)//
//    public List<Sensor_Data> findallCurrentdata(@PathVariable String STM32_IPv6){
//
//        Sensor sensor = new Sensor(STM32_IPv6,2,0);
//        System.out.println(sensor.getStm32_IPv6());
//        System.out.println(sensor.getType_ID());
//        int findsensorid = 0;
//        try{
//            findsensorid = sensorMapper.findIDbyIPandtypeand_stm32sensorID(sensor);
//        }catch (Exception e){
//            System.out.println(e);
//            return null;
//        }
//
//        String sensor_name = "sensor_"+findsensorid;
//
//        System.out.println("findalldata");
//        List<Sensor_Data> alldata = sensor_dataMapper.findalldata(sensor_name);
//        return alldata;
//    }

    @RequestMapping(value = "/Sensor_Data/{STM32_IPv6}/1281BCurrent", method = RequestMethod.GET)
    public Sensor_Data findaCurrentdata(@PathVariable String STM32_IPv6){

        Sensor sensor = new Sensor(STM32_IPv6,2,0);
        System.out.println(sensor.getStm32_IPv6());
        System.out.println(sensor.getType_ID());
        int findsensorid = 0;
        try{
            findsensorid = sensorMapper.findIDbyIPandtypeand_stm32sensorID(sensor);
        }catch (Exception e)
        {
            System.out.println(e);
            return null;
        }
        String table_name = "sensor_"+findsensorid;
        Sensor_Data sensor_data = new Sensor_Data();
        sensor_data = sensor_dataMapper.findadata(table_name);
        return sensor_data;
    }

    @RequestMapping(value = "/Sensor_Data/{STM32_IPv6}/Temp", method = RequestMethod.GET)
    public List<Sensor_Data> findaTempdata(@PathVariable String STM32_IPv6){

        Sensor sensor1 = new Sensor(STM32_IPv6,1,0);
        Sensor sensor2 = new Sensor(STM32_IPv6,5,0);
        List<Sensor_Data> sensor_dataList = new ArrayList<>();
        int findsensorid1 = 0;
        int findsensorid2 = 0;
        try{
            findsensorid1 = sensorMapper.findIDbyIPandtypeand_stm32sensorID(sensor1);
        }catch (Exception e)
        {
            System.out.println(e);
            return null;
        }
        try {
            findsensorid2 = sensorMapper.findIDbyIPandtypeand_stm32sensorID(sensor2);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        String table_name = "sensor_"+findsensorid1;
        Sensor_Data sensor_data1 = sensor_dataMapper.findadata(table_name);

        table_name = "sensor_"+findsensorid2;
        Sensor_Data sensor_data2 = sensor_dataMapper.findadata(table_name);

        sensor_dataList.add(sensor_data1);//1281
        sensor_dataList.add(sensor_data2);//680
        return sensor_dataList;
    }

    @RequestMapping(value = "/Sensor_Data/{STM32_IPv6}/HumandPre", method = RequestMethod.GET)
    public List<Sensor_Data> findaHumandPredata(@PathVariable String STM32_IPv6){

        Sensor sensor1 = new Sensor(STM32_IPv6,4,0);
        Sensor sensor2 = new Sensor(STM32_IPv6,7,0);
        List<Sensor_Data> sensor_dataList = new ArrayList<>();
        int findsensorid1 = 0;
        int findsensorid2 = 0;
        try{
            findsensorid1 = sensorMapper.findIDbyIPandtypeand_stm32sensorID(sensor1);
        }catch (Exception e)
        {
            System.out.println("fail1");
            System.out.println(e);
            return null;
        }
        try {

            findsensorid2 = sensorMapper.findIDbyIPandtypeand_stm32sensorID(sensor2);
        }catch (Exception e){
            System.out.println("fail2");
            System.out.println(e);
            return null;
        }
        String table_name = "sensor_"+findsensorid1;
        Sensor_Data sensor_data1 = sensor_dataMapper.findadata(table_name);

        table_name = "sensor_"+findsensorid2;
        Sensor_Data sensor_data2 = sensor_dataMapper.findadata(table_name);

        sensor_dataList.add(sensor_data1);//press
        sensor_dataList.add(sensor_data2);//hum
        return sensor_dataList;
    }

    @RequestMapping(value = "/Sensor_Data/{STM32_IPv6}/{type}/{date}",method = RequestMethod.GET)//拿所有1281Temp
    public List<Sensor_Data> findall1281BTemp(@PathVariable String STM32_IPv6,@PathVariable String type,@PathVariable String date){
        Sensor sensor;
        switch (type){
            case "all1281BTemp":
                 sensor = new Sensor(STM32_IPv6,1,0);
                break;
            case "all680Temp":
                 sensor = new Sensor(STM32_IPv6,5,0);
                break;
            case "all680Pre":
                 sensor = new Sensor(STM32_IPv6,4,0);
                break;
            case "all680Hum":
                 sensor = new Sensor(STM32_IPv6,7,0);
                break;
            case "all1281BCurrent":
                 sensor = new Sensor(STM32_IPv6,2,0);
                System.out.println("current");
                break;
            default:
                sensor = new Sensor("0",0,0);
                break;
        }

        System.out.println(sensor.getStm32_IPv6());
        System.out.println(sensor.getType_ID());
        int findsensorid = 0;
        try{
            findsensorid = sensorMapper.findIDbyIPandtypeand_stm32sensorID(sensor);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        String table_name = "sensor_"+findsensorid;
        System.out.println(table_name);
        if(date.equals("null")){
            List<Sensor_Data> alldata = sensor_dataMapper.findalldata(table_name);
            return alldata;
        }else{
            String onedate = date + "%";
            List<Sensor_Data> alldata = sensor_dataMapper.findalldatabydate(table_name,onedate);
            return alldata;
        }


    }
//    @RequestMapping(value = "/Sensor_Data/{STM32_IPv6}/all680Temp",method = RequestMethod.GET)//拿所有1281Temp
//    public List<Sensor_Data> findall680Temp(@PathVariable String STM32_IPv6){
//
//        Sensor sensor = new Sensor(STM32_IPv6,5,0);
//        System.out.println(sensor.getStm32_IPv6());
//        System.out.println(sensor.getType_ID());
//        int findsensorid = 0;
//        try{
//            findsensorid = sensorMapper.findIDbyIPandtypeand_stm32sensorID(sensor);
//        }catch (Exception e){
//            System.out.println(e);
//            return null;
//        }
//        String table_name = "sensor_"+findsensorid;
//        System.out.println("findalldata");
//        List<Sensor_Data> alldata = sensor_dataMapper.findalldata(table_name);
//        return alldata;
//    }
//    @RequestMapping(value = "/Sensor_Data/{STM32_IPv6}/all680Pre",method = RequestMethod.GET)//拿所有1281Temp
//    public List<Sensor_Data> findall680Pre(@PathVariable String STM32_IPv6){
//
//        Sensor sensor = new Sensor(STM32_IPv6,4,0);
//        System.out.println(sensor.getStm32_IPv6());
//        System.out.println(sensor.getType_ID());
//        int findsensorid = 0;
//        try{
//            findsensorid = sensorMapper.findIDbyIPandtypeand_stm32sensorID(sensor);
//        }catch (Exception e){
//            System.out.println(e);
//            return null;
//        }
//        String table_name = "sensor_"+findsensorid;
//        System.out.println("findalldata");
//        List<Sensor_Data> alldata = sensor_dataMapper.findalldata(table_name);
//        return alldata;
//    }
//    @RequestMapping(value = "/Sensor_Data/{STM32_IPv6}/all680Hum",method = RequestMethod.GET)//拿所有1281Temp
//    public List<Sensor_Data> findall680Hum(@PathVariable String STM32_IPv6){
//
//        Sensor sensor = new Sensor(STM32_IPv6,7,0);
//        System.out.println(sensor.getStm32_IPv6());
//        System.out.println(sensor.getType_ID());
//        int findsensorid = 0;
//        try{
//            findsensorid = sensorMapper.findIDbyIPandtypeand_stm32sensorID(sensor);
//        }catch (Exception e){
//            System.out.println(e);
//            return null;
//        }
//        String table_name = "sensor_"+findsensorid;
//        System.out.println("findalldata");
//        List<Sensor_Data> alldata = sensor_dataMapper.findalldata(table_name);
//        return alldata;
//    }
}
