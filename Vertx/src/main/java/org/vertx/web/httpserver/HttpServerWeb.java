package org.vertx.web.httpserver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import org.vertx.core.json.JsonObjectReciever;

import java.nio.file.Path;

public class HttpServerWeb extends AbstractVerticle
{
    @Override
    public void start(Promise<Void> promise)
    {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        /* Routing with Exact Path*/
        Route route = router.route().path("/home/v1");

        route.handler(ctx ->
        {
            // "home/v1" === home/v1/ === home/v1/////
            ctx.response().end("Hello from /home/v1");
        });

        /* Route the request through multiple handler */
        //        route.handler(ctx ->
        //        {
        //            ctx.response().setChunked(true);
        ////            ctx.response().putHeader("content-type", "text/event-stream")
        ////                    .putHeader("Cache-Control", "no-cache");
        //
        //            ctx.response().putHeader("content-type", "text/plain");
        //            ctx.response().write("Hello from vert.x web!!\n");
        //
        //            vertx.setTimer(2000, tid -> ctx.next());
        //        });

        //        route.handler(ctx ->
        //        {
        //            ctx.response().end("Hello from version 1 !!");
        //        });


        //        router.get("/api/get").respond(ctx -> Future.succeededFuture(new JsonObject().put("hello","world")));

        //        router.get("/api/get").respond(ctx ->
        //                ctx.response().putHeader("content-type", "application/json")
        //                        .end(new JsonObject().put("hello","world").toBuffer()));


        /* Routing with Regular Expression in Path */
//        Route route1 = router.route().pathRegex(".*v2");
//        route1.handler(ctx ->
//        {
//            ctx.response().end("Hello This is regular Expression");
//        });

        server.requestHandler(router).listen(8080, "localhost").onComplete(res -> {
            if(res.succeeded())
            {
                System.out.println("Server is now listening");
                promise.complete();
            }
            else
            {
                System.out.println("Failed to bind!");
                promise.fail(res.cause());
            }
        });


    }
}
