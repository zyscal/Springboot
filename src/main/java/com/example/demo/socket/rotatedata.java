package com.example.demo.socket;

import javax.websocket.*;

public class rotatedata {
    public static int numofpac = 0;
    public static int Jitter_coefficient = 6;
    public static float Ang = 1.047f;//角速度 弧度每秒 60度每秒
    public static float cipers = 4;//每秒钟采样频率
    public static float dupers = 90;//按照最大扭转速度，系数次的最大差值  (ang*180/3.14)*(Jitter_coefficient/cipers)
    public static int temp = 0;
    public static float datax;
    public static float datay;
    public static float[] getridofJitterx = new float[6];
    public static float[] getridofJittery = new float[6];
    public Session session;
}
