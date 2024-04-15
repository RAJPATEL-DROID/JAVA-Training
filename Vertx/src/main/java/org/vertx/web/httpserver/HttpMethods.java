package org.vertx.web.httpserver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.core.Promise;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class HttpMethods extends AbstractVerticle
{
        @Override
        public void start (Promise<Void> promise) {
            HttpServer server = vertx.createHttpServer();
            Router router = Router.router(vertx);
            Route route = router.route().method(HttpMethod.POST).consumes("text/html").consumes("application/json");

            route.handler(ctx -> {
                Future<Buffer> bodyFuture = ctx.request().body();
                bodyFuture.onSuccess(buffer ->
                {
                    String body = buffer.toString();
                    System.out.println(body);
                    ctx.response().end(body);
                }).onFailure(err ->
                {
                    ctx.fail(err);
                });
            });

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
