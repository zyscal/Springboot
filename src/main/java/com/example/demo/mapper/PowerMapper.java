package com.example.demo.mapper;

import com.example.demo.pojo.Power;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface PowerMapper {
    List<Power> findallPower();
    String findpower_namebyID(@Param("power_ID") int power_ID);
    int findnextpower_ID();
    boolean addapower(Power power);
    boolean deletepower_byID(@Param("power_ID") int power_ID);
    Power findpowerbyID(@Param("power_ID") int power_ID);
    boolean updataparent_ID(@Param("power_ID") int power_ID,@Param("new_parent_ID") int new_parent_ID);
}
