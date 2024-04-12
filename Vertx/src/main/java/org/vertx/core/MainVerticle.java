package org.vertx.core;

import io.vertx.core.Vertx;

public class MainVerticle
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();


        //        vertx.deployVerticle(new TCPServer(), res ->{
        //            if(res.succeeded()){
        //                System.out.println("Server");
        //            }else{
        //                System.out.println("Failed to deploy the verticle "+ res.cause().getMessage());
        //            }
        //        });

//        vertx.deployVerticle(new TCPClient(), res -> {
//            if(res.succeeded()){
//                System.out.println("TCP Client Verticle Deployed");
//            }else{
//                System.out.println("Failed to deploy the Client " + res.cause().getMessage());
//            }
//        });
    }
}