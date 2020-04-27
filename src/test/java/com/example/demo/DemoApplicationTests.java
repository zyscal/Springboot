package com.example.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.pojo.Router;
import com.example.demo.pojo.Sensor;
import com.example.demo.socket.UdpServer;
import com.sun.javaws.IconUtil;
import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock;
import org.junit.jupiter.api.Test;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;
import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {
  /*  @Autowired
    DataSource dataSource;
    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }*/
  /*public static void main(String[] args)  {
      try
      {


          System.setProperty("java.net.preferIPv6Addresses","true");
          System.setProperty("java.net.preferIPv4Stack","false");

          System.out.println(InetAddress.getByName("localhost"));
        //  System.out.println("enter into test\n\n");
        //  byte dstipv6_2[]=new byte[]{(byte)0xfe,(byte)0x80,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x4c,(byte)0xd3,(byte)0x46,(byte)0x60,(byte)0x64,(byte)0x5e,(byte)0x36,(byte)0x27};
         // DatagramSocket socket = new DatagramSocket(8000, InetAddress.getByName("2001:db5::4cd3:4660:645e:3627"));
    //      socket.setReuseAddress(true);
//          socket.bind(new InetSocketAddress(Inet6Address.getByAddress(dstipv6_2));
        //  DatagramPacket request = new DatagramPacket(new byte[1024], 1024);

          //System.setProperty("java.net.preferIPv4Stack","false");

        //  socket.receive(request);

         // System.out.println(request);
          Socket socket = new Socket(8000);

          Socket s;
          while (true) {
              // 一旦有堵塞, 则表示服务器与客户端获得了连接
              System.out.println("qwqwqwqwqw");
              DataInputStream net = new DataInputStream(socket.getInputStream());

              System.out.println(net);
              // 处理这次连接

          }

      }
      catch (IOException a)
      {
          System.out.println(a);
      }

  }*/
  public static void main(String[] args) throws IOException {

      JSONObject object = new JSONObject();
      object.put("name","根节点");
      JSONArray Routerlist = new JSONArray();
      Router router1 = new Router("fe80::1","router1");
      Router router2 = new Router("fe80::2","router2");
      Routerlist.add(router1);
      Routerlist.add(router2);
      object.put("children",Routerlist);
      System.out.println(object.toJSONString());




//     // /fe80:0:0:0:204:a3ff:fe10:24%5
//    String routerIP ="fe80::1";
//    String stm32IP = "fe80:0:0:0:204:a3ff:fe10:24%5";
////    System.out.println(Inet6Address.getByName("2402:4E00:9000::0").getAddress());
//    byte[] byterouter = Inet6Address.getByName(routerIP).getAddress();
//    byte[] bytestm32 = Inet6Address.getByName(stm32IP).getAddress();
//    byte []prefix = { (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte) 0xff,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
//    byte[] prefixofrouter = new byte[16];
//    byte[] prefixofstm32 = new byte[16];
//    for(int i = 0;i<16;i++){
//        prefixofrouter[i] = (byte)(prefix[i] &byterouter[i]);
//        prefixofstm32[i] = (byte)(prefix[i] &bytestm32[i]);
//    }
//    boolean check = true;
//    for(int i = 0;i<16;i++){
//        if(prefixofrouter[i]!=prefixofstm32[i]){
//            check = false;
//        }
//      }
//    if(check)
//        System.out.println("yes");
//
//    for(int i = 0;i<6;i++)
//    {
//        if(byterouter[i] == bytestm32[i])
//            System.out.println("1");
//        else
//            System.out.println("0");
//    }
//    byte a0 = 0;
//    for(int i = 0;i<temp.length;i++){
//        if(temp[i] == a0)
//            System.out.print("0");
//        else
//            System.out.print("1");
//    }

//      System.out.println("");
//    String routerIPaddr = Inet6Address.getByName("2402:4E00:9000::0").getAddress().toString();
//    System.out.println(routerIPaddr);


  }
}
