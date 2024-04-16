package org.vertx.web;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.core.http.HttpMethod;

public class ContextDataAndMetadata
{

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        Router router = Router.router(vertx);

        router.putMetadata("app-name", "MyApp");

        router.putMetadata("version", "1.0.0");

        router.route(HttpMethod.GET, "/v1/*")
                .handler(ctx ->
                {
                    ctx.response().setChunked(true);

                    ctx.put("foo", "bar");

                    System.out.println("bar");
                    ctx.next();

                });

        router.route(HttpMethod.GET, "/v1/users").putMetadata("route-key", "products-route")
                .handler(ctx ->
                {
                    String bar = ctx.get("foo");

                    String routeKey = ctx.currentRoute().getMetadata("route-key");

                    ctx.response().putHeader("X-Route-Key", routeKey);

                    String appName = router.getMetadata("app-name");

                    String version = router.getMetadata("version");

                    ctx.response().end("App Name: " + appName + ", Version: " + version + "bar :" + bar);
                });

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080, httpServerAsyncResult ->
                {
                    if (httpServerAsyncResult.succeeded())
                    {
                        System.out.println("Server started on port 8080");
                    }
                    else
                    {
                        System.out.println("Failed to start server");
                    }
                });
    }
}
