package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.mapper.PowerMapper;
import com.example.demo.pojo.Power;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8090", maxAge = 3600)
@RestController
public class PowerController {
    @Autowired
   private PowerMapper powerMapper;
    @GetMapping("/findallpower")
    public List<Power> findallPower(){
        List<Power> PowerList = powerMapper.findallPower();
        return PowerList;
    }
    @RequestMapping("/addapower/{power_name}/{parent_ID}")
    public boolean addapower(@PathVariable String power_name,@PathVariable int parent_ID){
        boolean check;
        try{
           check = powerMapper.addapower(new Power(power_name,parent_ID));
           return check;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
    @RequestMapping("/deletepower_byID/{power_ID}")
    public boolean deletepower_byID(@PathVariable int power_ID){
        boolean check;
        try{
            check = powerMapper.deletepower_byID(power_ID);
            return check;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
    @RequestMapping("/updataparent_ID/{power_ID}/{new_parent_ID}")
    public boolean updataparent_ID(@PathVariable int power_ID,@PathVariable int new_parent_ID){
        boolean check;
        try{
            check = powerMapper.updataparent_ID(power_ID,new_parent_ID);
            return check;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    @RequestMapping("/findpower_namebyID/{power_ID}")
    public String findpower_namebyID(@PathVariable int power_ID){
        try{
            String findpower_namebyID = powerMapper.findpower_namebyID(power_ID);
            return findpower_namebyID;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    @RequestMapping("/findparentpower_namebyID/{power_ID}")
    public String findparentpower_namebyID(@PathVariable int power_ID){
        try{
            Power power = powerMapper.findpowerbyID(power_ID);
            String parent_name = powerMapper.findpower_namebyID(power.getParent_ID());
            return parent_name;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    @GetMapping("/findnextpower_ID")
    public int findnextpower_ID(){
        int ans = 0;
        try {
            ans = powerMapper.findnextpower_ID();
        }catch (Exception e){
            System.out.println(e);
            return ans;
        }
        return ans;
    }
    @RequestMapping("/findpowertreebyid/{power_ID}")
    public JSONObject findpowertreebyid(@PathVariable int power_ID){
        Power root = createpowertree();
        Power newroot = DFS(root,power_ID);//将新的子节点生成格式化的json文件发送
        return createJSON(newroot);
    }
    @RequestMapping("/findpowerexcept/{power_ID}")//防止父亲节点添加到子节点，查询时候不展示自己与子节点
    //算法为获取所有list，从list中深搜删除节点
    public List<Power> findpowerexcept(@PathVariable int power_ID){
        List<Power> listofpower = findallPower();
        Power root = createpowertree();
        findrootneedtodelete(listofpower,root,power_ID);
        for(int i = 0;i<listofpower.size();i++){
            System.out.println(listofpower.get(i).getPower_ID());
        }
        return listofpower;
    }

    @GetMapping("/finddeletelist")
    public List<Power> finddeletelist(){
        Power root = createpowertree();//拿到根节点
        List<Power> deletelist = new ArrayList<>();
        deletelist(deletelist,root);
        return deletelist;

    }
    public void deletelist(List<Power> deletelist,Power newroot){
        if(newroot.getChildrenlist().size() == 0){
            deletelist.add(newroot);
        }else {
            for(int i = 0;i<newroot.getChildrenlist().size();i++){
                deletelist(deletelist,newroot.getChildrenlist().get(i));
            }
        }
    }

    public void findrootneedtodelete(List<Power> listofpower,Power newroot,int IDneedtodelete) {//传递list列表，递归查询所有节点
/**
 */
        if (newroot.getPower_ID() == IDneedtodelete){
            deletefromlist(listofpower,newroot);
        }else {
            for(int i = 0;i<newroot.getChildrenlist().size();i++){
                findrootneedtodelete(listofpower,newroot.getChildrenlist().get(i),IDneedtodelete);
            }
        }
    }
    public void deletefromlist(List<Power> listofpower,Power newroot){//递归删除所有点
        for(int i = 0;i<newroot.getChildrenlist().size();i++){
            deletefromlist(listofpower,newroot.getChildrenlist().get(i));
        }
        Power removepower = new Power();//有点坑 list 的用法child不同不能删除
        removepower.setPower_ID(newroot.getPower_ID());
        removepower.setPower_name(newroot.getPower_name());
        removepower.setParent_ID(newroot.getParent_ID());
        listofpower.remove(removepower);
    }


    public JSONObject createJSON(Power root){
        JSONObject newroot = new JSONObject();
        newroot.put("name",root.getPower_name());
        newroot.put("power_ID",root.getPower_ID());
        if(root.getChildrenlist().size() == 0){
            newroot.put("value",1);
            return newroot;
        }else {
            JSONArray newchildren = new JSONArray();
            for(int i = 0;i<root.getChildrenlist().size();i++){//将所有孩子进行添加
                JSONObject childroot = createJSON(root.getChildrenlist().get(i));
                newchildren.add(childroot);
            }
            newroot.put("children",newchildren);
        }
        return newroot;
    }
    private Power createpowertree(){//只允许一个根节点，返回根节点
        List<Power> Powerlist = findallPower();//拿到所有节点
        System.out.println(Powerlist);

        for(int i = 0;i<Powerlist.size();i++){//遍历所有节点，分别将每一个节点添加到父亲下，0除外
            switch (Powerlist.get(i).getParent_ID()){
                case 0://根节点不做处理
                    break;
                default:{
                    for(int j = 0;j<Powerlist.size();j++){//遍历所有节点找父亲
                        if(Powerlist.get(j).getPower_ID() == Powerlist.get(i).getParent_ID()){//找到父亲，进行添加
                            Powerlist.get(j).addchildrenlist(Powerlist.get(i));
                            break;
                        }
                    }
                }
            }
        }

        System.out.println(Powerlist);
        for(int i = 0;i<Powerlist.size();i++){
            if(Powerlist.get(i).getParent_ID() == 0){
                return Powerlist.get(i);
            }
        }
        System.out.println("goto null");
        return null;
    }
    private Power DFS(Power root,int power_ID){//输入root，输入寻找的ID，返回节点，DFS深搜
        if(root.getPower_ID() == power_ID)
            return root;
        else{
            List<Power> children = root.getChildrenlist();
            for(int i = 0;i<children.size();i++){
                if(DFS(children.get(i),power_ID) == null)
                    continue;
                else
                    return DFS(children.get(i),power_ID);
            }
            return null;
        }
    }
}
