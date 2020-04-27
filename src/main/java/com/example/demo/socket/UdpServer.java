package com.example.demo.socket;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * udp连接，用于动态ip, pos向255.255.255.255：5060发送请求即可
 * **/
public class UdpServer extends Thread implements Runnable {
    private final int MAX_LENGTH = 1024;
    private final int PORT = 16415;
    private DatagramSocket datagramSocket;
    public static String getLocalIPv6Address() throws IOException {
        InetAddress inetAddress = null;
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        outer:
        while (networkInterfaces.hasMoreElements()) {
            Enumeration<InetAddress> inetAds = networkInterfaces.nextElement().getInetAddresses();
            while (inetAds.hasMoreElements()) {
                inetAddress = inetAds.nextElement();
                //Check if it's ipv6 address and reserved address
                if (inetAddress instanceof Inet6Address && !isReservedAddr(inetAddress)) {
                    break outer;
                }
            }
        }

        String ipAddr = inetAddress.getHostAddress();
        // Filter network card No
        int index = ipAddr.indexOf('%');
        if (index > 0) {
            ipAddr = ipAddr.substring(0, index);
        }

        return ipAddr;
    }
    private static boolean isReservedAddr(InetAddress inetAddr) {
        if (inetAddr.isAnyLocalAddress() || inetAddr.isLinkLocalAddress()
                || inetAddr.isLoopbackAddress()) {
            return true;
        }

        return false;
    }
    public void run() {
        try {
            while(true){
                try {
                    byte[] buffer = new byte[MAX_LENGTH];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    receive(packet);
                    String receStr = new String(packet.getData(), 0 , packet.getLength());
                    System.out.println("接收数据包" + receStr);
                    byte[] bt = new byte[packet.getLength()];

                    System.arraycopy(packet.getData(), 0, bt, 0, packet.getLength());
                    System.out.println(packet.getAddress().getHostAddress() + "：" + packet.getPort() + "：" + Arrays.toString(bt));
                    packet.setData(bt);
                    response(packet);

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receive(DatagramPacket packet) throws Exception {
        datagramSocket.receive(packet);
    }

    public void response(DatagramPacket packet) throws Exception {
        datagramSocket.send(packet);
    }

    /**
     * 初始化连接
     */
    public void init(){
        try {
            datagramSocket = new DatagramSocket(PORT ,Inet6Address.getByName("2001:db5:0:0:4cd3:4660:645e:3627"));
            System.out.println("udp服务端已经启动！");
        } catch (Exception e) {
            datagramSocket = null;
            System.out.println("udp服务端启动失败！");
            e.printStackTrace();
        }
    }
}