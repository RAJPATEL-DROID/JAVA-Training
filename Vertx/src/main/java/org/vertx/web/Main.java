package org.vertx.web;

import io.vertx.core.Vertx;
import org.vertx.web.httpserver.HttpMethods;
import org.vertx.web.httpserver.HttpServerWeb;

public class Main
{
    public static void main(String[] args)
    {
//            Vertx vertx = Vertx.vertx();

            Vertx vertx1 = Vertx.vertx();

            vertx1.deployVerticle("org.vertx.web.httpserver.HttpMethods").onFailure(res ->{
                System.out.println(res.getCause());
            });

//            vertx.deployVerticle(new HttpServerWeb())
//                    .onComplete(res ->
//                    {
//                        if(res.succeeded()) System.out.println("Verticle Deployed Successfully!");
//                        else{
//                            System.out.println("Verticle Failed to deploy " + res.cause());
//                        }
//                    });
    }
}
