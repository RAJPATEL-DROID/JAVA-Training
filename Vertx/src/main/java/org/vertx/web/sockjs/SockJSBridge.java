package org.vertx.web.sockjs;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.bridge.BridgeEventType;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;


public class SockJSBridge
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        Router router = Router.router(vertx);

        EventBus eventBus = vertx.eventBus();

        SockJSHandler sockJSHandler = SockJSHandler.create(vertx);

        SockJSBridgeOptions options = new SockJSBridgeOptions();

        options.addInboundPermitted(new PermittedOptions().setAddress("chat.to.server").setMatch(new JsonObject().put("message","hello world")));

        options.addOutboundPermitted(new PermittedOptions().setAddress("chat.to.client"));

        router.route("/eventbus/*")
                .subRouter(sockJSHandler
                .bridge(options, be -> {
                    System.out.println(be.type());
                    System.out.println(be.getRawMessage());
                    if (be.type() == BridgeEventType.PUBLISH ||
                            be.type() == BridgeEventType.RECEIVE
                                    && (be.getRawMessage().getString("body").contains("server"))) {

                        // Reject it
                        System.out.println("This event type is not supported!!!");

                        be.complete(false);

                        return;
                    }
                    be.complete(true);
                }));

        eventBus.consumer("chat.to.server", message -> System.out.println("Received message from client : " + message.body()));

        vertx.setPeriodic(3000,id -> eventBus.send("chat.to.client","Hello from server"));


        vertx.createHttpServer(new HttpServerOptions().setHost("10.20.40.197").setPort(8080)).requestHandler(router)
                .listen( result ->
                {
                    if(result.succeeded())
                    {
                        System.out.println("Server is listening on 8080");
                    }
                    else
                    {
                        System.out.println(result.cause().getMessage());
                    }
                });
    }
}
