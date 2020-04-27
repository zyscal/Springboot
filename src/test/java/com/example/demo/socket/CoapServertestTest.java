package com.example.demo.socket;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class CoapServertestTest {
    public static void main(String[] args) throws UnknownHostException {
        CoapServertest coapServertest = new CoapServertest();
        coapServertest.run();
        System.out.println("after run");
//        GETClient getClient = new GETClient();
//        try{
//            getClient.testclient();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
    }
}