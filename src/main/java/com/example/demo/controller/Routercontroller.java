package com.example.demo.controller;

import com.example.demo.mapper.RouterMapper;
import com.example.demo.pojo.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8090", maxAge = 3600)
@RestController
public class Routercontroller {
    @Autowired
    private RouterMapper routerMapper;

    @GetMapping("/router")    public List<Router> listofRouter(){
        List<Router> routerList= routerMapper.listofRouter();
        return routerList;
    }
    @RequestMapping(value = "/router/{router_IPv6}/{router_name}", method = RequestMethod.GET)
    public boolean updateRouter(@PathVariable String router_IPv6, @PathVariable String router_name){

        System.out.println("请求修改的router_IPv6是：" + router_IPv6 + "\n请求修改的名称是：" +router_name);
        Router changerouter =new Router(router_IPv6,router_name);
        boolean check = routerMapper.updateRouter(changerouter);
        return check;
    }
    @RequestMapping(value = "/router/{router_IPv6}/total_terminal", method = RequestMethod.GET)
    public int total_terminal(@PathVariable String router_IPv6){

        System.out.println("请求total_terminal的router_IPv6是：" + router_IPv6 );
        Router changerouter =new Router(router_IPv6,"");
        int check = routerMapper.total_terminal(changerouter);
        System.out.println(check);
        return check;
    }
    @RequestMapping(value = "/router/{router_IPv6}/online", method = RequestMethod.GET)
    public int online(@PathVariable String router_IPv6){

        System.out.println("请求online的router_IPv6是：" + router_IPv6 );
        Router online =new Router(router_IPv6,"");
        int check = routerMapper.online(online);
        System.out.println(check);
        return check;
    }
    @RequestMapping(value = "/router/{router_IPv6}/{router_name}/addrouter", method = RequestMethod.GET)
    public boolean addRouter(@PathVariable String router_IPv6, @PathVariable String router_name){

        System.out.println("请求添加的router_IPv6是：" + router_IPv6 + "\n请求添加的名称是：" +router_name);
        Router addRouter =new Router(router_IPv6,router_name);
        boolean check;
        try{
            check = routerMapper.addRouter(addRouter);
        }catch (Exception a)
        {
            System.out.println("error in add");
            return false;
        }

        return check;
    }

    @RequestMapping(value = "/router/{router_IPv6}/delete", method = RequestMethod.GET)
    public boolean deleteRouter(@PathVariable String router_IPv6){

        System.out.println("请求delete的router_IPv6是：" + router_IPv6 );
        Router delete =new Router(router_IPv6,"");
        boolean check;
        try {
            check = routerMapper.deleteRouterbyIPv6(delete);
        }catch (Exception a)
        {
            System.out.println("error in delete");
            System.out.println(a);
            return false;
        }
        System.out.println(check);
        return check;
    }

}
