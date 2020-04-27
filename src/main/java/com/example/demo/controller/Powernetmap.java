package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.mapper.RouterMapper;
import com.example.demo.mapper.STM32Mapper;
import com.example.demo.mapper.SensorMapper;
import com.example.demo.pojo.Router;
import com.example.demo.pojo.STM32;
import com.example.demo.pojo.Sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8090", maxAge = 3600)
@RestController
public class Powernetmap {
    @Autowired
    private SensorMapper sensorMapper;
    @Autowired
    private RouterMapper routerMapper;
    @Autowired
    private STM32Mapper stm32Mapper;

    @RequestMapping(value = "/Powernetmap",method = RequestMethod.GET)
    public JSONObject Powernetmap(){
        JSONObject object = new JSONObject();
        JSONArray arrayofRouter = new JSONArray();
        object.put("name","localhost");
        List<Router> listofRouter = new ArrayList<Router>();
        listofRouter = routerMapper.listofRouter();
        int numofrouter = listofRouter.size();

        for(int i = 0;i<numofrouter;i++)
        {
            JSONObject temrouterobject = new JSONObject();
            temrouterobject.put("name",listofRouter.get(i).getRouter_name());
            List<STM32> listofSTM32byRouter = new ArrayList<STM32>();
            listofSTM32byRouter = stm32Mapper.listofSTM32byRouter(listofRouter.get(i).getRouter_IPv6());
            JSONArray arrayofstm32 = new JSONArray();
            int numofSTM32 = listofSTM32byRouter.size();

            for (int j = 0;j<numofSTM32;j++){
                System.out.println("numofSTM32:"+numofSTM32);
                JSONObject temstm32object = new JSONObject();
                temstm32object.put("name",listofSTM32byRouter.get(j).getStm32_name());
                JSONArray arrayofsensor = new JSONArray();
                List<Sensor> listofsensorbySTM32 = new ArrayList<Sensor>();
//                System.out.println("listofSTM32byRouter"+listofSTM32byRouter);
//                System.out.println(listofSTM32byRouter.get(j).getStm32_IPv6());



                listofsensorbySTM32 = sensorMapper.findsensorbySTM32(listofSTM32byRouter.get(j).getStm32_IPv6());
                int numofsensor = listofsensorbySTM32.size();
                System.out.println("numofsensor:"+numofsensor);
                for(int k = 0;k<numofsensor;k++){
                    JSONObject temsensor = new JSONObject();
                    System.out.println(listofsensorbySTM32.get(k).getType_ID());


                    String sensorname = sensorMapper.findtype_namebyid(listofsensorbySTM32.get(k).getType_ID());
                    temsensor.put("name",sensorname);
                    temsensor.put("value",0);
                    arrayofsensor.add(temsensor);
//                    System.out.println("k:"+k+"  temsensor"+temsensor);
                }
                temstm32object.put("children",arrayofsensor);
                arrayofstm32.add(temstm32object);
//                System.out.println("j:"+j+"  temstm32object"+temstm32object);
            }
            temrouterobject.put("children",arrayofstm32);
            arrayofRouter.add(temrouterobject);
//            System.out.println("i:"+ i+" arrayofRouter:"+temrouterobject);
        }
        object.put("children",arrayofRouter);
        System.out.println(object);
        return object;
    }
}
