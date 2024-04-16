package org.vertx.web;

import io.vertx.core.Vertx;
import io.vertx.core.http.Cookie;
import io.vertx.ext.web.Router;

import java.util.UUID;

public class CookiesDemo
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);

        router.route("/user")
                .handler(ctx ->
                {
                   ctx.response().addCookie(Cookie.cookie("userId", UUID.randomUUID().toString()));

                   ctx.end("Hello user");

                });

        router.route("/user/1")
                .handler(ctx ->
                {
                   String uid = ctx.request().getCookie("userId").getValue();
                    ctx.response().end(String.format("User with id %s is connected", uid));
                });

        vertx.createHttpServer().requestHandler(router)
                .listen(8080, res ->
                {
                    if(res.succeeded())
                    {
                        System.out.println("Server listening on Port 8080");
                    }
                    else
                    {
                        System.out.println(res.cause().getMessage());
                    }
                });
    }
}
