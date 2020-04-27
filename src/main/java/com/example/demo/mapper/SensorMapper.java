package com.example.demo.mapper;

import com.example.demo.pojo.Sensor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SensorMapper {
    int findIDbyIPandtypeand_stm32sensorID(Sensor sensor);
    boolean addasensor(Sensor sensor);//用于批量添加sensor 无stm32_sensor_ID属性，愚蠢写法！时间紧张回头再改！20200411
    boolean insertsensor(Sensor sensor);//用于添加一个真实sensor,有smt32_sensor_ID属性
    int findsensorbystm32andtype(@Param("stm32_IPv6") String stm32_IP6,@Param("type_ID") int type_ID);
    List<Sensor> findsensorbySTM32(@Param("stm32_IPv6") String stm32_IPv6);
    String findtype_namebyid(@Param("id") int id);
    List<Sensor> findsensorbypage(@Param("page") int page);
    boolean deleteSensor(@Param("sensor_ID") int sensor_ID);
    boolean updateSensor(@Param("sensor_ID") int sensor_ID,@Param("sensor_name") String sensor_name);
    List<Sensor> findallsensor();
    String findsensor_namebyid(@Param("sensor_ID") int sensor_ID);
}
