package org.vertx.core.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;

public class HttpServerDemo extends AbstractVerticle
{
    @Override
    public void start(Promise<Void> promise){
        HttpServer server = vertx.createHttpServer();
        server.requestHandler(req ->{
            System.out.println(req.version());
            System.out.println(req.method());
            System.out.println(req.uri());
            System.out.println(req.absoluteURI());
            System.out.println(req.path());
            req.response().end("Hello from Server");

        });
        server.listen(8080,"localhost")
                .onComplete(res -> {
                    if(res.succeeded()){
                        System.out.println("Server is now listening");
                    }else{
                        System.out.println("Failed to bind!");
                    }
                });



    }
}
