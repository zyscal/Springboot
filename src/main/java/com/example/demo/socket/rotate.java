package com.example.demo.socket;

import com.example.demo.mapper.RouterMapper;
import com.example.demo.mapper.STM32Mapper;
import com.example.demo.mapper.SensorMapper;
import com.example.demo.mapper.Sensor_DataMapper;
import com.example.demo.pojo.STM32;
import com.example.demo.pojo.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.management.DescriptorKey;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet6Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.server.resources.CoapExchange;

@ServerEndpoint("/websocket")
@Component
public class rotate {
    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<rotate> webSocketSet = new CopyOnWriteArraySet<rotate>();
    private Session session;
    private final int MAX_LENGTH = 1024;
    private final int PORT = 16415;
    private DatagramSocket datagramSocket;
    private HashSet<String> processing_1281BCurrent;
    private CoapServer server;
    private boolean runornot = false;
    /////////////////0310

    /////////////////
    private static rotate rotate1;
    @Autowired
    private SensorMapper sensorMapper;
    @Autowired
    private Sensor_DataMapper sensor_dataMapper;
    @Autowired
    private STM32Mapper stm32Mapper;
    @Autowired
    private RouterMapper routerMapper;
    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {
        rotate1 = this;
        rotate1.sensor_dataMapper = this.sensor_dataMapper;
        rotate1.sensorMapper = this.sensorMapper;
        rotate1.stm32Mapper = this.stm32Mapper;
        rotate1.routerMapper = this.routerMapper;
    }
    public void run() throws UnknownHostException {
        /**
         * Coap逻辑：(旧)
         * 1281B_Current
         * 1281B_Temp
         * 1.拿到STM32IPv6地址
         * 1.1 IPv6已注册，更新最后通讯时间，并继续
         * 1.2 IPv6没有注册则将stm32(stm32_IPv6,state,router_IPv6,sensor_num)格式进行注册,更新路由设备下total_terminal++
         * 1.2.1 找到相应路由前缀
         * 1.2.2 分离出传感器数量数据
         * 2.根据STM32_IPv6地址找传感器是否注册
         * 2.1已经注册：拿sensor_ID放数据
         * 2.2没注册：将sensor(stm32_IPv6,sensor_type)进行注册，并拿回sensor_ID新建表格，拿ID放数据
         *
         */
        /**coap逻辑：（新）
         * 1.新设备con注册，向/register发送"传感器数量 类型1_设备内编号 类型2 ...."
         * 2.完成新设备注册于传感器建立后，回复yes
         * 3.找到已有设备，回复yes
         * 4.数据类型设备内id 值
         *
         */
        this.server = new CoapServer();
//        server.addEndpoint(new CoapEndpoint(new InetSocketAddress(Inet6Address.getByName("2001:db8::25"), 5683)));
        server.addEndpoint(new CoapEndpoint(new InetSocketAddress(Inet6Address.getByName("2001:db5::240e:c402:3a03:953f"), 5683)));
        server.add(new CoapResource("register"){
            @Override
            public void handlePOST(CoapExchange exchange){// 5 1_0 2_0 4_0 5_0 7_0
//                System.out.println(exchange.getRequestText());
                String stm32_IPv6 = exchange.getSourceAddress().toString().split("/")[1];//拿到设备IP
                int numofsensor = findnumofsensor(exchange.getRequestText().toString());
                String router_IPv6 = new String();
                String table_name = new String();
                if(checkSTM32inbase(stm32_IPv6)){
                    //已经注册
                    exchange.respond("yes");
                    rotatedata.numofpac = 0;
                    rotatedata.Jitter_coefficient = 6;
                    rotatedata.temp = 0;
                    rotatedata.datax = 0;
                    rotatedata.datay = 0;
                    rotatedata.getridofJitterx = new float[ rotatedata.Jitter_coefficient];
                    rotatedata.getridofJittery = new float[ rotatedata.Jitter_coefficient];
                    for(int i = 0;i <  rotatedata.Jitter_coefficient;i++)
                    {
                        rotatedata.getridofJitterx[i] = 0;
                        rotatedata.getridofJittery[i] = 0;
                    }
                    return;
                }else {
                    try {
                        router_IPv6 = findRouterbySTM32(stm32_IPv6);

                    } catch (UnknownHostException e) {
                        System.out.println(e);
                        e.printStackTrace();
                    }
                    //注册STM32
                    try{
                        insertaSTM32(stm32_IPv6,router_IPv6,numofsensor);
                        //更新路由设备
                        updatetotal_terminal(router_IPv6);
                    }catch (Exception e){
                        System.out.println(e);
                    }
                }//完成注册
                //注册传感器
                String[] allsensor = exchange.getRequestText().split("\0")[0].split(" ");
                for(int i = 1;i <= numofsensor;i++){
                    int sensor_type = Integer.parseInt(allsensor[i].split("_")[0]);
                    int stm32_sensor_ID = Integer.parseInt(allsensor[i].split("_")[1]);
                    Sensor sensor = new Sensor(stm32_IPv6,sensor_type,stm32_sensor_ID);

                    try{
                        rotate1.sensorMapper.insertsensor(sensor);
                        int sensor_ID = rotate1.sensorMapper.findIDbyIPandtypeand_stm32sensorID(sensor);
                        table_name = "sensor_"+sensor_ID;
                        rotate1.sensor_dataMapper.createatable(table_name);//不玩幺蛾子删表 必成功
                    }catch (Exception e){
                        System.out.println(e);
                        System.out.println("注册传感器失败："+sensor);
                    }
                }
                rotatedata.numofpac = 0;
                rotatedata.Jitter_coefficient = 6;
                rotatedata.temp = 0;
                rotatedata.datax = 0;
                rotatedata.datay = 0;
                rotatedata.getridofJitterx = new float[ rotatedata.Jitter_coefficient];
                rotatedata.getridofJittery = new float[ rotatedata.Jitter_coefficient];
                for(int i = 0;i <  rotatedata.Jitter_coefficient;i++)
                {
                    rotatedata.getridofJitterx[i] = 0;
                    rotatedata.getridofJittery[i] = 0;
                }
                ///////////
                exchange.respond("yes");
            }
        });
        server.add(new CoapResource("1281B_Current"){//数据格式：设备内ID 值
            @Override
            public void handlePOST(CoapExchange exchange){//0 0.23456
                String stm32_IPv6 = exchange.getSourceAddress().toString().split("/")[1];
                int stm32_sensor_ID =Integer.parseInt(exchange.getRequestText().split(" ")[0]);
                float sensor_data = Float.parseFloat(exchange.getRequestText().split(" ")[1]);
                Sensor sensor = new Sensor(stm32_IPv6,2,stm32_sensor_ID);
                int sensor_ID = rotate1.sensorMapper.findIDbyIPandtypeand_stm32sensorID(sensor);
                String table_name = "sensor_"+sensor_ID;
                try {
                    boolean check = rotate1.sensor_dataMapper.addSensorData(table_name,sensor_data);
                }catch (Exception e){
//                    System.out.println("插入数据成功："+sensor_data);
                }
//                System.out.println("------------------------");
            };
        });
        server.add(new CoapResource("1281B_Temp"){//数据格式：设备内ID 值
            @Override
            public void handlePOST(CoapExchange exchange){
                String stm32_IPv6 = exchange.getSourceAddress().toString().split("/")[1];
                int stm32_sensor_ID =Integer.parseInt(exchange.getRequestText().split(" ")[0]);
                float sensor_data = Float.parseFloat(exchange.getRequestText().split(" ")[1]);
                Sensor sensor = new Sensor(stm32_IPv6,1,stm32_sensor_ID);
                int sensor_ID = rotate1.sensorMapper.findIDbyIPandtypeand_stm32sensorID(sensor);
                String table_name = "sensor_"+sensor_ID;
                try {
                    boolean check = rotate1.sensor_dataMapper.addSensorData(table_name,sensor_data);
                }catch (Exception e){
//                    System.out.println("插入数据成功："+sensor_data);
                }
//                System.out.println("------------------------");

            };
        });
        server.add(new CoapResource("BME_TEMP"){
            @Override
            public void handlePOST(CoapExchange exchange){
                String stm32_IPv6 = exchange.getSourceAddress().toString().split("/")[1];
                int stm32_sensor_ID =Integer.parseInt(exchange.getRequestText().split(" ")[0]);
                float sensor_data = Float.parseFloat(exchange.getRequestText().split(" ")[1]);
                Sensor sensor = new Sensor(stm32_IPv6,5,stm32_sensor_ID);
                int sensor_ID = rotate1.sensorMapper.findIDbyIPandtypeand_stm32sensorID(sensor);
                String table_name = "sensor_"+sensor_ID;
                try {
                    boolean check = rotate1.sensor_dataMapper.addSensorData(table_name,sensor_data);
                }catch (Exception e){
//                    System.out.println("插入数据成功："+sensor_data);
                }
//                System.out.println("------------------------");
            }
        });
        server.add(new CoapResource("BME_Press"){
            @Override
            public void handlePOST(CoapExchange exchange){
                String stm32_IPv6 = exchange.getSourceAddress().toString().split("/")[1];
                int stm32_sensor_ID =Integer.parseInt(exchange.getRequestText().split(" ")[0]);
                float sensor_data = Float.parseFloat(exchange.getRequestText().split(" ")[1]);
                Sensor sensor = new Sensor(stm32_IPv6,4,stm32_sensor_ID);
                int sensor_ID = rotate1.sensorMapper.findIDbyIPandtypeand_stm32sensorID(sensor);
                String table_name = "sensor_"+sensor_ID;
                try {
                    boolean check = rotate1.sensor_dataMapper.addSensorData(table_name,sensor_data);
                }catch (Exception e){
//                    System.out.println("插入数据成功："+sensor_data);
                }
//                System.out.println("------------------------");
            }
        });
        server.add(new CoapResource("BME_Hum") {
            @Override
            public void handlePOST(CoapExchange exchange) {
                String stm32_IPv6 = exchange.getSourceAddress().toString().split("/")[1];
                int stm32_sensor_ID = Integer.parseInt(exchange.getRequestText().split(" ")[0]);
                float sensor_data = Float.parseFloat(exchange.getRequestText().split(" ")[1]);
                Sensor sensor = new Sensor(stm32_IPv6, 7, stm32_sensor_ID);
                int sensor_ID = rotate1.sensorMapper.findIDbyIPandtypeand_stm32sensorID(sensor);
                String table_name = "sensor_" + sensor_ID;
                try {
                    boolean check = rotate1.sensor_dataMapper.addSensorData(table_name, sensor_data);
                } catch (Exception e) {
//                    System.out.println("插入数据成功："+sensor_data);
                }
//                System.out.println("------------------------");
            }
        });
        server.add(new CoapResource("rotate"){

            @Override
            public void handlePOST(CoapExchange exchange){
//                System.out.println("exchange:"+exchange.getRequestText());
                String xandy = exchange.getRequestText();


                //System.out.println("接收数据包" + receStr);
                String[] alldata = new String[2];
                alldata = xandy.split(" ");

                if(rotatedata.numofpac < rotatedata.Jitter_coefficient)//小于防抖系数，则将数据加入到数组中；
                {
                    rotatedata.getridofJitterx[rotatedata.numofpac] = Float.parseFloat(alldata[0]);
                    rotatedata.getridofJittery[rotatedata.numofpac] = Float.parseFloat(alldata[1]);
                    rotatedata.numofpac++;

                    String ans = Float.parseFloat(alldata[0]) + " " + Float.parseFloat(alldata[1]);
                    try {
                        sendMessage(ans);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    return;
                }
                else
                {
                    rotatedata.getridofJitterx[rotatedata.temp] = Float.parseFloat(alldata[0]);
                    rotatedata.getridofJittery[rotatedata.temp] = Float.parseFloat(alldata[1]);
                    rotatedata.temp = (rotatedata.temp + 1)%((int)rotatedata.Jitter_coefficient);
                }


//                for(int i = 0;i<rotatedata.Jitter_coefficient;i++)
//                {
//                    if(rotatedata.getridofJitterx[i] < 100){
//                        rotatedata.datax += (360 + rotatedata.getridofJitterx[i]);
//                    }else{
//                        rotatedata.datax += rotatedata.getridofJitterx[i];
//                    }
//                    if(rotatedata.getridofJittery[i] < 100){
//                        rotatedata.datay += (360 + rotatedata.getridofJittery[i]);
//                    }else{
//                        rotatedata.datay += rotatedata.getridofJittery[i];
//                    }
//                }
//                rotatedata.datax /= rotatedata.Jitter_coefficient;
//                rotatedata.datay /= rotatedata.Jitter_coefficient;
//                rotatedata.datax %= 360;
//                rotatedata.datay %= 360;


                //如果数组中最大值与最小值的差为钝角则出现临界问题，如果为锐角则
                float maxnumx= 0;
                float minnumx= 360;
                float maxnumy= 0;
                float minnumy= 360;
                //找到最大值
                for(int i = 0;i<rotatedata.Jitter_coefficient;i++){
                    if(rotatedata.getridofJitterx[i] < minnumx)
                         minnumx = rotatedata.getridofJitterx[i];
                    if(rotatedata.getridofJittery[i] < minnumy)
                        minnumy = rotatedata.getridofJittery[i];

                    if(rotatedata.getridofJitterx[i] > maxnumx)
                        maxnumx = rotatedata.getridofJitterx[i];
                    if(rotatedata.getridofJittery[i] > maxnumy)
                        maxnumy = rotatedata.getridofJittery[i];
                }

                for (int i = 0;i<rotatedata.Jitter_coefficient;i++){
                    rotatedata.datax += rotatedata.getridofJitterx[i];
                }
                rotatedata.datax /= rotatedata.Jitter_coefficient;
                int countx = 0;
                if(maxnumx - minnumx <= rotatedata.dupers){//正常求平均
                }else {
                    for (int i = 0;i<rotatedata.Jitter_coefficient;i++){
                        if (rotatedata.datax > rotatedata.getridofJitterx[i]){
                            countx++;
                        }
                    }
                    rotatedata.datax += (360/rotatedata.Jitter_coefficient)*countx;
                    rotatedata.datax %= 360;
                }

                for (int i = 0;i<rotatedata.Jitter_coefficient;i++){
                    rotatedata.datay += rotatedata.getridofJittery[i];
                }
                rotatedata.datay /= rotatedata.Jitter_coefficient;
                int county = 0;
                if(maxnumy - minnumy <= rotatedata.dupers){
                }else {
                    for (int i = 0;i<rotatedata.Jitter_coefficient;i++){
                        if (rotatedata.datay > rotatedata.getridofJittery[i]){
                            county++;
                        }
                    }
                    rotatedata.datay += (360/rotatedata.Jitter_coefficient)*county;
                    rotatedata.datay %= 360;
                }

//                if( rotatedata.datax-rotatedata.getridofJitterx[0] >= 40 || rotatedata.getridofJitterx[0] - rotatedata.datax >= 40)
//                {
//                    float count = 0;
//                    for(int i = 0;i<rotatedata.Jitter_coefficient;i++)
//                    {
//                        if(rotatedata.getridofJitterx[i] < rotatedata.datax)
//                            count++;
//                    }
//                    rotatedata.datax += 360*count/rotatedata.Jitter_coefficient;
//                }
//
//                if( rotatedata.datay-rotatedata.getridofJittery[0] >= 40 || rotatedata.getridofJittery[0] - rotatedata.datay >= 40)
//                {
//
//                    float count = 0;
//                    for(int i = 0;i<rotatedata.Jitter_coefficient;i++)
//                    {
//                        if(rotatedata.getridofJittery[i] < rotatedata.datay)
//                            count++;
//                    }
//                    rotatedata.datay += 360*count/rotatedata.Jitter_coefficient;
//                }

                String ans = Float.toString(rotatedata.datax) + " " + Float.toString(rotatedata.datay);

//                System.out.println("ans:"+ans);
                try {
                    sendMessage(ans);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                rotatedata.datax = 0;
                rotatedata.datay = 0;
            }
        });
        server.start();
    }

    //找到stm32_IPv6对应的路由设备
    private String findRouterbySTM32(String stm32_IPv6) throws UnknownHostException {

        String[] listofRouter = rotate1.routerMapper.listofRouter_IPv6();
        byte[] bytestm32 = Inet6Address.getByName(stm32_IPv6).getAddress();//获取二进制地址
        for (int i = 0;i<listofRouter.length;i++)
        {
            byte[] byterouter = Inet6Address.getByName(listofRouter[i]).getAddress();//获取二进制地址
            boolean check = true;
            for(int j = 0;j<8;j++){
                if(byterouter[j] != bytestm32[j]){
                    check = false;
                    break;
                }
            }
            if(check){
                return listofRouter[i];
            }
        }
        return null;
    }

    private int findnumofsensor(String message){
       String[] allmessage = message.split(" ");
       return Integer.parseInt(allmessage[0]);
    }

    private boolean checkSTM32inbase(String stm32_IPv6){//查找STM32是否在数据库中
        System.out.println("进入checkSTM32inbase"+stm32_IPv6);
        if(rotate1.stm32Mapper.findSTM32byIP(stm32_IPv6) != null)
        {
            System.out.println("找到STM32 IPv6" +stm32_IPv6);
            return true;
        }
        else
        {
            System.out.println("没有找到STM32 IPv6"+stm32_IPv6);
            return false;
        }

    }

    private boolean updatetotal_terminal(String router_IPv6){
       return rotate1.routerMapper.updateRouterTotal_terminal(router_IPv6);
    }

    private boolean insertaSTM32(String stm32_IPv6,String router_IPv6,int sensor_num){//注册新STM32
        STM32 stm32 = new STM32(stm32_IPv6,true,router_IPv6,sensor_num);
        return rotate1.stm32Mapper.insertaSTM32(stm32);
    }

    @OnOpen
    public void onOpen(Session session) throws IOException, InterruptedException {


        this.session = session;
        System.out.println("session"+session);
        System.out.println(session.getId());
        webSocketSet.add(this); //加入set中
        addOnlineCount(); //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        if(getOnlineCount() >1)
            return;
        if(!runornot){
            processing_1281BCurrent = new HashSet<String>();
            try{//启动coap服务器
                run();
            }catch (Exception e){
                System.out.println(e);
            }
            System.out.println("after run");
            runornot = true;
        }
    }
    @OnClose
    public void onClose(){
        webSocketSet.remove(this); //从set中删除
        subOnlineCount(); //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        this.server.destroy();
    }
    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
//群发消息
        for(rotate item: webSocketSet){
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
发送消息的函数
     */
    public void sendMessage(String message) throws IOException {

        this.session.getBasicRemote().sendText(message);
//        System.out.println("成功发送数据："+message);
//this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        rotate.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        rotate.onlineCount--;
    }


}
