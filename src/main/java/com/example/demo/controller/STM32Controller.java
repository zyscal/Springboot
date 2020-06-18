package com.example.demo.controller;

import com.example.demo.mapper.STM32Mapper;
import com.example.demo.pojo.STM32;
import com.example.demo.pojo.Sensor;
import com.example.demo.pojo.Sensor_Data;
import com.example.demo.socket.rotate;
import org.apache.ibatis.annotations.Param;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLOutput;
import java.util.List;
@CrossOrigin(origins = "http://localhost:8090", maxAge = 3600)
@RestController
public class STM32Controller {

    @Autowired
    private STM32Mapper stm32Mapper;

    @GetMapping("/querySTM32List")
    public List<STM32> querySTM32List() {
        List<STM32> stm32list = stm32Mapper.querySTM32List();
        return stm32list;
    }
    @GetMapping("/testconnect24")
    public String testconnect24() throws IOException {
        InetAddress address = InetAddress.getByName("2001:db5::204:a3ff:fe10:24");
        if(address.isReachable(500)){
            System.out.println("SUCCESS");
            return "SUCCESS";
        }else{
            System.out.println("FAILURE");
            return "FAILURE";
        }
    }
    @RequestMapping("/addSTM32/{stm32_IPv6}/{stm32_name}/{state}/{router_IPv6}/{sensor_num}/{power_ID}")
    public boolean insertSTM32(@PathVariable String stm32_IPv6,@PathVariable String stm32_name,
                               @PathVariable boolean state,@PathVariable String router_IPv6,
                               @PathVariable int sensor_num,@PathVariable int power_ID){
        STM32 stm32 = new STM32(stm32_IPv6,stm32_name,state,router_IPv6,sensor_num,power_ID);
        boolean check;
        try {
            System.out.println(stm32);
            check = stm32Mapper.insertaSTM32(stm32);
            return check;
        }catch (Exception e){
            System.out.println(e);
            return  false;
        }
    }
    @RequestMapping("/updateSTM32/{stm32_IPv6}/{stm32_name}/{power_ID}")
    public boolean updateSTM32(@PathVariable String stm32_IPv6,@PathVariable String stm32_name,
                               @PathVariable int power_ID){
        boolean check;
        try{
           check =  stm32Mapper.updateSTM32(stm32_IPv6,stm32_name,power_ID);
           return check;
        }catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
    }
    @RequestMapping("/deleteSTM32/{stm32_IPv6}")
    public boolean deleteSTM32(@PathVariable String stm32_IPv6){
        boolean check;
        try{
            check = stm32Mapper.deleteSTM32(stm32_IPv6);
            return check;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
    @RequestMapping("/findSTM32namebyIP/{stm32_IPv6}")
    public String findSTM32namebyIP(@PathVariable String stm32_IPv6){
        try{
            String stm32_name = stm32Mapper.findSTM32namebyIP(stm32_IPv6);
            return stm32_name;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    @RequestMapping("/findSTM32byIP/{stm32_IPv6}")
    public STM32 findSTM32byIP(@PathVariable String stm32_IPv6){
        try {
            STM32 stm32 = stm32Mapper.findSTM32byIP(stm32_IPv6);
            return stm32;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

}
