package com.example.demo.mapper;

import com.example.demo.pojo.Sensor_type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface Sensor_typeMapper {
    List<Sensor_type> findallsensortype();
}
