package org.vertx.core.eventbus.point2point;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;


public class Reciever extends AbstractVerticle
{
    public void start()
    {
        EventBus eventBus = vertx.eventBus();

        eventBus.consumer("address", this::handleMessage);
    }

    private void handleMessage(Message<String> message)
    {

        System.out.println("Received message: " +  message.body());
    }

}