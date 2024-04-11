package org.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class AsynchronousVerticle extends AbstractVerticle
{
    private HttpServer server;

    @Override
    public void start(Promise<Void> startPromise) {
        server = vertx.createHttpServer().requestHandler(req -> req.response()
                .putHeader("content-type", "text/plain")
                .end("Hello from Vert.x!"));

        // Now bind the server:
        server.listen(8080, res -> {
            if (res.succeeded()) {
                startPromise.complete();
            } else {
                startPromise.fail(res.cause());
            }
        });
    }

    public static void main(String[] args)
    {
        Vertx vertx1 = Vertx.vertx();
        vertx1.deployVerticle(AsynchronousVerticle.class.getName())
                .onComplete(result -> {
                    if(result.succeeded()){
                        System.out.println("Deployed sever on" + result.result());
                    }else {
                        System.out.println(result.cause().getMessage());
                    }
                });
    }
}
