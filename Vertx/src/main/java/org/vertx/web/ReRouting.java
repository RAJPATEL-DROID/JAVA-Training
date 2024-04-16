package org.vertx.web;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class ReRouting
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);

        router.get("/v1/user")
                .handler(ctx ->
                {
                    ctx.put("Name", " Raj");

                    ctx.next();
                });

        router.get("/v1/user")
                .handler(ctx ->
                {
                    System.out.println("Name" + ctx.get("Name"));

                    ctx.reroute("/v2/username");
                });

        router.get("/v2/username")
                .handler(ctx ->
                {
                    ctx.response().end("Hello");
                });


        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080, httpServerAsyncResult ->
        {
            if(httpServerAsyncResult.succeeded())
            {
                System.out.println("Server Listening on port 8080");
            }
            else
            {
                System.out.println(httpServerAsyncResult.cause().getMessage());
            }
        });
    }
}
