package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data

public class Power {
    private int power_ID;
    private String power_name;
    private int parent_ID;
    private List<Power> childrenlist;

    public Power(String power_name,int parent_ID){
        this.power_name = power_name;
        this.parent_ID = parent_ID;
        childrenlist = new ArrayList<>();
    }
    public Power(){
        childrenlist = new ArrayList<>();
    }
    public int getPower_ID() {
        return power_ID;
    }

    public void setPower_ID(int power_ID) {
        this.power_ID = power_ID;
    }

    public String getPower_name() {
        return power_name;
    }

    public void setPower_name(String power_name) {
        this.power_name = power_name;
    }

    public int getParent_ID() {
        return parent_ID;
    }

    public void setParent_ID(int parent_ID) {
        this.parent_ID = parent_ID;
    }

    public List<Power> getChildrenlist() {
        return childrenlist;
    }

    public void addchildrenlist(Power power){
        this.childrenlist.add(power);
        power.setParent_ID(this.power_ID);//改变父亲节点时将子节点进行改变
    }

    public void remocechildrenlist(Power power,Power newparentpower){//删除旧子节点，并将子节点添加到新的父亲节点上
        this.childrenlist.remove(power);
        newparentpower.addchildrenlist(power);
    }
}
