package com.example.demo.mapper;

import com.example.demo.pojo.Router;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RouterMapper {
    List<Router> listofRouter();
    String[] listofRouter_IPv6();
    int total_terminal(Router router);
    int online(Router router);
    int numofRouter();//统计所有路由器数量

    boolean addRouter(Router router);
    boolean deleteRouterbyIPv6(Router router);
    boolean updateRouter(Router router);
    boolean updateRouterTotal_terminal(@Param("router_IPv6") String router_IPv6);
}
