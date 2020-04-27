package com.example.demo.mapper;

import com.example.demo.pojo.Power;
import com.example.demo.pojo.Power_sensor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface Power_sensorMapper {
    List<Power_sensor> findpower_sensorbypower(@Param("power_ID") int power_ID);
    List<Power_sensor> findallpower_sensor();
    boolean insertpower_sensor(@Param("power_ID") int power_ID,@Param("sensor_ID") int sensor_ID);
    boolean deletepower_sensor(@Param("power_ID") int power_ID,@Param("sensor_ID") int sensor_ID);

}
