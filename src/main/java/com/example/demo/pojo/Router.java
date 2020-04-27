package com.example.demo.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Router {
    private String router_IPv6;
    private String router_name;
    private int online;
    private int total_terminal;

    public Router(String router_IPv6, String router_name){
        this.router_IPv6 = router_IPv6;
        this.router_name = router_name;
    }

    public String getRouter_IPv6() {
        return router_IPv6;
    }

    public void setRouter_IPv6(String router_IPv6) {
        this.router_IPv6 = router_IPv6;
    }

    public String getRouter_name() {
        return router_name;
    }

    public void setRouter_name(String router_name) {
        this.router_name = router_name;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getTotal_terminal() {
        return total_terminal;
    }

    public void setTotal_terminal(int total_terminal) {
        this.total_terminal = total_terminal;
    }
}
