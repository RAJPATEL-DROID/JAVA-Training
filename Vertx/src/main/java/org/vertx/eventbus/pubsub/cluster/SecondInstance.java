package org.vertx.eventbus.pubsub.cluster;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecondInstance {

    private static final Logger logger = LoggerFactory.getLogger(SecondInstance.class);

    public static void main(String[] args) {
        Vertx.clusteredVertx(new VertxOptions(), ar -> {
            if (ar.succeeded()) {
                logger.info("Second instance has been started");
                Vertx vertx = ar.result();
                vertx.deployVerticle("org.vertx.eventbus.pubsub.HeatSensor", new DeploymentOptions().setInstances(4));
                vertx.deployVerticle("org.vertx.eventbus.pubsub.Listener");
                vertx.deployVerticle("org.vertx.eventbus.pubsub.SensorData");
                JsonObject conf = new JsonObject().put("port", 8080);
                vertx.deployVerticle("org.vertx.eventbus.pubsub.HTTPServer", new DeploymentOptions().setConfig(conf));
            } else {
                logger.error("Could not start", ar.cause());
            }
        });
    }
}