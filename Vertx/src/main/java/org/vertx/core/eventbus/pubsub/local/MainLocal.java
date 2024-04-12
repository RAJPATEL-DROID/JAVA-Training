package org.vertx.core.eventbus.pubsub.local;


import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class MainLocal {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle("org.vertx.core.eventbus.pubsub.HeatSensor", new DeploymentOptions().setInstances(4));
        vertx.deployVerticle("org.vertx.core.eventbus.pubsub.Listener");
        vertx.deployVerticle("org.vertx.core.eventbus.pubsub.SensorData");
        vertx.deployVerticle("org.vertx.core.eventbus.pubsub.HTTPServer");
    }
}