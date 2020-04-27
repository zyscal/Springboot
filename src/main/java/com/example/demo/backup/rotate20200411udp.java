package com.example.demo.backup;

import com.example.demo.mapper.RouterMapper;
import com.example.demo.mapper.STM32Mapper;
import com.example.demo.mapper.SensorMapper;
import com.example.demo.mapper.Sensor_DataMapper;
import com.example.demo.pojo.STM32;
import com.example.demo.pojo.Sensor;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.CopyOnWriteArraySet;

//@ServerEndpoint("/websocket")
//@Component
//public class rotate20200411udp {
//    private static int onlineCount = 0;
//    private static CopyOnWriteArraySet<rotate20200411udp> webSocketSet = new CopyOnWriteArraySet<rotate20200411udp>();
//    private Session session;
//    private final int MAX_LENGTH = 1024;
//    private final int PORT = 16415;
//    private DatagramSocket datagramSocket;
//
//
//    /////////////////0310
//    private int numofpac = 0;
//    private float Jitter_coefficient = 6;
//    private int temp = 0;
//    private float datax;
//    private float datay;
//    /////////////////
//    private static rotate20200411udp rotate1;
//    @Autowired
//    private SensorMapper sensorMapper;
//    @Autowired
//    private Sensor_DataMapper sensor_dataMapper;
//    @Autowired
//    private STM32Mapper stm32Mapper;
//    @Autowired
//    private RouterMapper routerMapper;
//    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
//    public void init() {
//        rotate1 = this;
//        rotate1.sensor_dataMapper = this.sensor_dataMapper;
//        rotate1.sensorMapper = this.sensorMapper;
//        rotate1.stm32Mapper = this.stm32Mapper;
//        rotate1.routerMapper = this.routerMapper;
//    }
//    public void run() throws UnknownHostException {
//        CoapServer server = new CoapServer();
//        server.addEndpoint(new CoapEndpoint(new InetSocketAddress(Inet6Address.getByName("2001:db8::25"), 5683)));
//        server.add(new CoapResource("1281B_Current"){//数据格式：传感器总数量 + “ ” + 1281B电流
//            @Override
//            public void handlePOST(CoapExchange exchange){
//                System.out.println("enter into 1281B_Current");
//                System.out.println(exchange.getRequestText());
//                System.out.println(exchange.getSourceAddress());
//                System.out.println("------------------------");
//            };
//        });
//        server.add(new CoapResource("1281B_Temp"){//数据格式：传感器总数量 + “ ” + 1281B温度
//            @Override
//            public void handlePOST(CoapExchange exchange){
//                System.out.println("enter into 1281B_Temp");
//                System.out.println(exchange.getRequestText());
//                System.out.println(exchange.getSourceAddress());
//                System.out.println("------------------------");
//            };
//        });
//        server.start();
//    }
//    private String findRouterbySTM32(String[] listofRouter,String packetaddr) throws UnknownHostException {
//        byte[] bytestm32 = Inet6Address.getByName(packetaddr).getAddress();//获取二进制地址
//
//        for (int i = 0;i<listofRouter.length;i++)
//        {
//            byte[] byterouter = Inet6Address.getByName(listofRouter[i]).getAddress();//获取二进制地址
//            boolean check = true;
//            for(int j = 0;j<8;j++){
//                if(byterouter[j] != bytestm32[j]){
//                    check = false;
//                }
//                if(check){
//                    return listofRouter[i];
//                }
//            }
//        }
//        return null;
//    }
//
//    private boolean checkSTM32inbase(String stm32_IPv6){
//        System.out.println("进入checkSTM32inbase"+stm32_IPv6);
//        if(rotate1.stm32Mapper.findSTM32byIP(stm32_IPv6) != null)
//        {
//            System.out.println("找到STM32 IPv6" +stm32_IPv6);
//            return true;
//        }
//        else
//        {
//            System.out.println("没有找到STM32 IPv6"+stm32_IPv6);
//            return false;
//        }
//
//    }
//
//    private boolean handle1281BCurrent(String STM32_IPv6,int sensor_type,double sensor_data)
//    {
//        System.out.println("enter into handle");
//        if(rotate1.sensorMapper == null)
//            System.out.println("sensormapper is null");
//        if(rotate1.sensor_dataMapper == null)
//            System.out.println("sensor_datamapper is null");
//        Sensor sensor = new Sensor(STM32_IPv6,sensor_type);
//        int findsensorid = 0;
//        findsensorid = rotate1.sensorMapper.findIDbyIPandtype(sensor);
//        System.out.println("findsensorid :"+findsensorid);
//
//        String sensor_name = "sensor_"+findsensorid;
//
//
//        boolean check = rotate1.sensor_dataMapper.addSensorData(sensor_name,sensor_data);
//
//        return check;
//    }
//
//    private boolean updatetotal_terminal(String router_IPv6){
//       return rotate1.routerMapper.updateRouterTotal_terminal(router_IPv6);
//    }
//    private boolean insertaSTM32(String stm32_IPv6,String router_IPv6,int sensor_num){
//        STM32 stm32 = new STM32(stm32_IPv6,true,router_IPv6,sensor_num);
//        return rotate1.stm32Mapper.insertaSTM32(stm32);
//    }
//    /**
//     * 连接建立成功调用的方法
//     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
//     */
//    @OnOpen
//    public void onOpen(Session session) throws IOException, InterruptedException {
//        this.session = session;
//        System.out.println("session"+session);
//        webSocketSet.add(this); //加入set中
//        addOnlineCount(); //在线数加1
//        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
//        if(getOnlineCount() >1)
//            return;
//
//        try {
//            datagramSocket = new DatagramSocket(PORT , Inet6Address.getByName("fe80::204:a3ff:fe10:25"));
//            System.out.println("udp服务端已经启动！");
//        } catch (Exception e) {
//            datagramSocket = null;
//            System.out.println("udp服务端启动失败！");
//            e.printStackTrace();
//        }
//
//
//        while(true)
//        {
//            byte[] buf = new byte[256];
//            DatagramPacket packet=new DatagramPacket(buf, 256);
//            datagramSocket.receive(packet);
//            String packetaddr = packet.getAddress().toString();
//            System.out.println(packetaddr);
//            String[] temaddr = packetaddr.split("/");
//            temaddr =temaddr[1].split("%");
//            packetaddr = temaddr[0];
//            System.out.println(packetaddr);
//
//            String receStr = new String(packet.getData(), 0 , packet.getLength());
//            System.out.println("接收数据包" + receStr);
//            String[] receStrs = receStr.split(" ");
//            Integer sensor_num = Integer.parseInt(receStrs[0]);
//            System.out.println("传感器数量是："+sensor_num);
//            if (checkSTM32inbase(packetaddr)){
//                // 更新最后一次通讯时间
//                System.out.println("更新最后一此通讯时间");
//            }
//            else {
//                //添加一个新的STM32设备
//                System.out.println("添加一个新的STM32设备");
//                String[] listofRouter = rotate1.routerMapper.listofRouter_IPv6();
//                for (int i = 0;i<listofRouter.length;i++)
//                {
//                    System.out.println(listofRouter[i]);
//                }
//               String RouterofSTM32 = findRouterbySTM32(listofRouter,packetaddr);
//                System.out.println("本设备所属路由节点是："+ RouterofSTM32);
//               if(RouterofSTM32 == null){
//                   System.out.println("错误：无匹配路由器："+packetaddr);
//                    continue;
//               }
//               else {//在相应路由下添加stm32设备
//
//                   boolean check;
//                   check = updatetotal_terminal(RouterofSTM32);
//                   System.out.println("在相应的路由下添加stm32设备"+ check);
//                   //在STM32表中添加一个新的STM32
//                   check = insertaSTM32(packetaddr,RouterofSTM32,sensor_num);
//                   System.out.println("在STM32表中添加一个新的STM32"+check);
//                   //在传感器表中添加新的传感器
//                    for(int i = 1;i<=sensor_num;i++)
//                    {
//                        Sensor sensor = new Sensor(packetaddr,Integer.parseInt(receStrs[2*i-1]));
//                        check = rotate1.sensorMapper.addasensor(sensor);
//                        System.out.println("在传感器表中添加："+check);
//                        //查寻id值
//                        Sensor sensor_findid = new Sensor(packetaddr,Integer.parseInt(receStrs[2*i-1]));
//                        int findsensorid = 0;
//                        findsensorid = rotate1.sensorMapper.findIDbyIPandtype(sensor);
//                        //根据id值创建数据表
//                        String newtable_name = "sensor_"+findsensorid;
//                        rotate1.sensor_dataMapper.createatable(newtable_name);
//                    }
//               }
//            }
//            //开始进行数据处理
//            for(int i = 1;i<=sensor_num;i++)
//            {
//                switch ( Integer.parseInt(receStrs[2*i-1]) ){
//                    case 1://1281B_Temp
//                        break;
//                    case 2://1281B_Current
//                        handle1281BCurrent(packetaddr,2,Float.parseFloat(receStrs[2*i]));
//                        break;
//                    case  3://1281B_Vol
//                        break;
//                    case 4://BME_Press
//                        break;
//                    case 5://BME_Temp
//                        break;
//                    case 6://BME_Gas
//                        break;
//                    case 7://BME_Hum
//                        break;
//                    case 8://rotatex
//                        break;
//                    case 9://rotatey
//                        break;
//                }
//            }
//        }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////        float[] getridofJitterx =new float[(int)Jitter_coefficient];
////        float[] getridofJittery =new float[(int)Jitter_coefficient];
////
////        for(int i = 0;i < Jitter_coefficient;i++)
////        {
////            getridofJitterx[i] = 0;
////            getridofJittery[i] = 0;
////        }
////        try {
////            datagramSocket = new DatagramSocket(PORT , Inet6Address.getByName("fe80::204:a3ff:fe10:25"));
////            System.out.println("udp服务端已经启动！");
////        } catch (Exception e) {
////            datagramSocket = null;
////            System.out.println("udp服务端启动失败！");
////            e.printStackTrace();
////        }
////        while(true)
////        {
////            byte[] buffer = new byte[MAX_LENGTH];
////            DatagramPacket packetDatagramPacket = new (buffer, buffer.length);
////            datagramSocket.receive(packet);
////            String receStr = new String(packet.getData(), 0 , packet.getLength());
////            //System.out.println("接收数据包" + receStr);
////            String[] alldata = new String[2];
////            alldata = receStr.split(" ");
////            if(numofpac < Jitter_coefficient)//小于防抖系数，则将数据加入到数组中；
////            {
////                getridofJitterx[numofpac] = Float.parseFloat(alldata[0]);
////                getridofJittery[numofpac] = Float.parseFloat(alldata[1]);
////                numofpac++;
////            }
////            else
////            {
////                getridofJitterx[temp] = Float.parseFloat(alldata[0]);
////                getridofJittery[temp] = Float.parseFloat(alldata[1]);
////                temp = (temp + 1)%((int)Jitter_coefficient);
////            }
////            for(int i = 0;i<Jitter_coefficient;i++)
////            {
////                datax += getridofJitterx[i];
////                datay += getridofJittery[i];
////            }
////            datax /= Jitter_coefficient;
////            datay /= Jitter_coefficient;
////            if( datax-getridofJitterx[0] >= 40 || getridofJitterx[0] - datax >= 40)
////            {
////                float count = 0;
////                for(int i = 0;i<Jitter_coefficient;i++)
////                {
////                    if(getridofJitterx[i] < datax)
////                        count++;
////                }
////                datax += 360*count/Jitter_coefficient;
////
////            }
////            if( datay-getridofJittery[0] >= 40 || getridofJittery[0] - datay >= 40)
////            {
////
////                float count = 0;
////                for(int i = 0;i<Jitter_coefficient;i++)
////                {
////                    if(getridofJittery[i] < datay)
////                        count++;
////                }
////                datay += 360*count/Jitter_coefficient;
////            }
////            String ans = Float.toString(datax) + " " + Float.toString(datay);
////            /*
////            socket.receive(packet);
////            byte[] arr = packet.getData();//获取数据
////
////            int len = packet.getLength();//获取有效的字节个数
////
////            System.out.println(new String(arr,0,len));
////            */
////            System.out.println(ans);
////            this.sendMessage(ans);
////            datax = 0;
////            datay = 0;
////        }
//    }
//    @OnClose
//    public void onClose(){
//        webSocketSet.remove(this); //从set中删除
//        subOnlineCount(); //在线数减1
//        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
//    }
//    /**
//     * 收到客户端消息后调用的方法
//     * @param message 客户端发送过来的消息
//     * @param session 可选的参数
//     */
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        System.out.println("来自客户端的消息:" + message);
////群发消息
//        for(rotate20200411udp item: webSocketSet){
//            try {
//                item.sendMessage(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//                continue;
//            }
//        }
//    }
//
//    /**
//     * 发生错误时调用
//     * @param session
//     * @param error
//     */
//    @OnError
//    public void onError(Session session, Throwable error){
//        System.out.println("发生错误");
//        error.printStackTrace();
//    }
//
//    /**
//发送消息的函数
//     */
//    public void sendMessage(String message) throws IOException {
//
//        this.session.getBasicRemote().sendText(message);
//        System.out.println("成功发送数据："+message);
////this.session.getAsyncRemote().sendText(message);
//    }
//
//    public static synchronized int getOnlineCount() {
//        return onlineCount;
//    }
//
//    public static synchronized void addOnlineCount() {
//        rotate20200411udp.onlineCount++;
//    }
//
//    public static synchronized void subOnlineCount() {
//        rotate20200411udp.onlineCount--;
//    }
//
//
//}
