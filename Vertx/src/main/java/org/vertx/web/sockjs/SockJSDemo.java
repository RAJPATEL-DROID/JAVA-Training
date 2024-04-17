package org.vertx.web.sockjs;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;


public class SockJSDemo
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);
        EventBus eventBus = vertx.eventBus();

        SockJSHandlerOptions options = new SockJSHandlerOptions().setHeartbeatInterval(2000).setRegisterWriteHandler(true);

        SockJSHandler sockJSHandler = SockJSHandler.create(vertx,options);

        router.route("/chatroom/*")
                .subRouter(sockJSHandler.socketHandler(sockJSSocket ->
                {
                    String writeHandlerID = sockJSSocket.writeHandlerID();
                    eventBus.send(writeHandlerID, Buffer.buffer("Hello"));
                    sockJSSocket.handler(buffer ->
                    {
                        String message = buffer.toString();

                        sockJSSocket.write(message);
                    });

                }));


        vertx.createHttpServer(new HttpServerOptions().setHost("10.20.40.197").setPort(8080)).requestHandler(router)
                .listen( result ->
                {
                    if(result.succeeded())
                    {
                        System.out.println("Server is listening on 8080");
                    }
                    else {
                        System.out.println(result.cause().getMessage());
                    }
                });

    }
}
