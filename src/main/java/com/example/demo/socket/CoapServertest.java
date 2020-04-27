package com.example.demo.socket;


import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.server.resources.CoapExchange;


public class CoapServertest {

    private static AtomicInteger count = new AtomicInteger();
    private final int PORT = 16415;
    public void run() throws UnknownHostException {
        CoapServer server = new CoapServer();
        server.addEndpoint(new CoapEndpoint(new InetSocketAddress(Inet6Address.getByName("2001:db8::25"), 5683)));
        server.add(new CoapResource("Current"){
            @Override
                    public void handlePOST(CoapExchange exchange){
                System.out.println("-----------------------");
                System.out.println("enter into post current");
                System.out.println(exchange.getRequestText());
                System.out.println("-----------------------");
//                exchange.respond("asdfasdf");
//                super.handlePOST(exchange);
            };
        });
        server.add(new CoapResource("hello"){
            @Override
            public void handleGET(CoapExchange exchange) {
                System.out.println("-----------------------");
                System.out.println("Get");
                //exchange.respond(CoAP.ResponseCode.CONTENT, "Hello CoAP!");
                System.out.println(exchange.getSourceAddress());
                System.out.println("-----------------------");
            };
            @Override
            public void handlePOST(CoapExchange exchange) { //1
                System.out.println("-----------------------");
                System.out.println("post");
                System.out.println(exchange.getRequestOptions().getUriQueryString());
                System.out.println(exchange.getRequestText().length());
                System.out.println(exchange.getRequestText());
                System.out.println(exchange.getSourceAddress());
                exchange.respond("asdfasdf");
                super.handlePOST(exchange);
                System.out.println("-----------------------");
            }
        });
            server.start();

    }
}
