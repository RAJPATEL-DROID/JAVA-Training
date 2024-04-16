package org.vertx.web;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class RouteFailure
{
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        Router router = Router.router(vertx);

        router.errorHandler(404, ctx -> ctx.response().setStatusCode(404).end("404 - Not Found"));
        router.errorHandler(405, ctx -> ctx.response().setStatusCode(405).end("405 - Method Not Allowed"));
        router.errorHandler(406, ctx -> ctx.response().setStatusCode(406).end("406 - Not Acceptable"));
        router.errorHandler(415, ctx -> ctx.response().setStatusCode(415).end("415 - Unsupported Media Type"));
        router.errorHandler(400, ctx -> ctx.response().setStatusCode(400).end("400 - Bad Request"));

        // Route for the path and method
        router.route("/hello").handler(ctx -> {
            if (ctx.request().method() == HttpMethod.GET) {
                ctx.response().end("Hello, World!");
            } else {
                ctx.response().setStatusCode(405).end("405 - Method Not Allowed");
            }
        });


        // Route for 406 Not Acceptable
        router.get("/reject").produces("text/plain").handler(ctx -> {
            ctx.response().putHeader("Content-Type", "text/plain");
            ctx.response().end("This should not be executed");
        });

        // Route for  415 Unsupported Media Type
        router.post("/reject").consumes("application/json").handler(ctx -> ctx.response().end("This should not be executed"));

        // Route for 400 Bad Request
        router.get("/empty").handler(ctx -> ctx.response().end("This should not be executed"));



        router.route().handler(BodyHandler.create());

        vertx.createHttpServer().requestHandler(router)
                .listen(8080, result ->
                {
                    if (result.succeeded())
                    {
                        System.out.println("Server is now listening on port 8080");
                    }
                    else
                    {
                        System.out.println("Failed to start server: " + result.cause());
                    }
                });
    }
}