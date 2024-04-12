package org.vertx.core.eventbus.pubsub;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.TimeoutStream;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;

public class HTTPServer extends AbstractVerticle {

    @Override
    public void start() {
        vertx.createHttpServer()
                .requestHandler(this::handler)
                .listen(config().getInteger("port", 8080));
    }

    private void handler(HttpServerRequest request) {
        if ("/".equals(request.path())) {
            request.response().sendFile("index.html");
        } else if ("/sse".equals(request.path())) {
            sse(request);
        } else {
            request.response().setStatusCode(404);
        }
    }

    private void sse(HttpServerRequest request) {
        HttpServerResponse response = request.response();
        response
                .putHeader("Content-Type", "text/event-stream")
                .putHeader("Cache-Control", "no-cache")
                .setChunked(true);

        MessageConsumer<JsonObject> consumer = vertx.eventBus().consumer("sensor.updates");
        consumer.handler(msg -> {
            response.write("event: update\n");
            response.write("data: " + msg.body().encode() + "\n\n");
        });


        vertx.periodicStream(1000)
            .handler(id -> {
            vertx.eventBus().<JsonObject>request("sensor.average", "",new DeliveryOptions().setSendTimeout(5000), reply -> {
                if (reply.succeeded()) {
                    response.write("Event: average\n");
                    response.write("Data: " + reply.result().body().encode() + "\n\n");
                }else{
                    response.write("Event: Request Time out\n");
                }
            });
        });


        response.endHandler(v -> {
            consumer.unregister();
        });
    }
}
