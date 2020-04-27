package com.example.demo.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.demo.dao.STM32dao;
import com.example.demo.pojo.STM32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
public class HelloController {

//    @GetMapping("/hello")
//    public String hello(@RequestParam(name = "name") String name, Model model){
//        model.addAttribute("name",name);
//        return "hello";
//    }
//    @GetMapping("/index")
//    public String index(){
//        return "index";
//    }
    @GetMapping("/login")
    public String login()
    {
        return "../static/login/index.html";
    }
    @Autowired
    STM32dao stm32dao;

    @GetMapping("/userlogin")
    public String userlogin(@RequestParam("name") String name,@RequestParam("pass") String pass,Model model)
    {
    System.out.println(name);
     if(!name.isEmpty() && "1234560".equals(pass))
     {
         System.out.println("hello");

         Collection<STM32> stm32s = stm32dao.getSTM32s();
         model.addAttribute("emps",stm32s);
         return "../static/body/html/table-basic";
     }
     else
         return "../static/login/index";

    }
}
