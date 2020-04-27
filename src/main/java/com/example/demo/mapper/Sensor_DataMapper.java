package com.example.demo.mapper;

import com.example.demo.pojo.Sensor;
import com.example.demo.pojo.Sensor_Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface Sensor_DataMapper {
    Sensor_Data findadata(@Param("sensor_name") String sensor_name);
    List<Sensor_Data> findalldata(@Param("table_name") String table_name);
    boolean addSensorData(@Param("table_name") String table_name,@Param("sensor_data") float sensor_data);
    boolean createatable(@Param("table_name") String table_name);
}
