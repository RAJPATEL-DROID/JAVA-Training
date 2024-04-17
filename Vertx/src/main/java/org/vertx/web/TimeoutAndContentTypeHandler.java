package org.vertx.web;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.ResponseContentTypeHandler;
import io.vertx.ext.web.handler.TimeoutHandler;

public class TimeoutAndContentTypeHandler
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);

        router.route("/*").failureHandler(failurectx ->
        {
            int statusCode = failurectx.statusCode();

            failurectx.response()
                    .setStatusCode(statusCode)
                    .end("Request Time Out, Try Again !!");


        });

        router.route("/timeout").handler(TimeoutHandler.create(2000))
                .handler(ctx -> vertx.setTimer(10000, res ->
                {
                    if(!ctx.request().isEnded()){
                        ctx.response().end("Hello");
                    }
                }));

        router.get("/api/books").handler(ResponseContentTypeHandler.create())
//                .produces("application/json")
                .produces("text/plain")
                .handler(ctx ->
                {
                    JsonObject jsonObject = new JsonObject().put("Hello","world");
                     ctx.response().end(jsonObject.toBuffer());
                });

        vertx.createHttpServer().requestHandler(router)
                .listen(8080, result ->
                {
                    if(result.succeeded())
                    {
                        System.out.println("Server is listening on port 8080");
                    }
                    else {
                        System.out.println(result.cause().getMessage());
                    }
                });
    }
}
