package org.vertx.eventbus.point2point;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageProducer;



public class Sender extends AbstractVerticle
{
    public void start()
    {
        EventBus eventBus = vertx.eventBus();

        MessageProducer<String> producer = eventBus.sender("address");


        vertx.setPeriodic(10000,id->{
            String message = "Hello from sender";

            System.out.println("Sending message : " + message);

            producer.write(message);
        });

    }

}