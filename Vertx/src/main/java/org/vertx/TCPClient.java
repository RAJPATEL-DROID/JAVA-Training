package org.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;

public class TCPClient extends AbstractVerticle
{
    @Override
    public void start(Promise<Void> promise){
        NetClientOptions options = new NetClientOptions().setConnectTimeout(10000)
                .setReconnectAttempts(10)
                .setReconnectInterval(500);
        NetClient client = vertx.createNetClient(options);

        client.connect(8080,"localhost")
                .onComplete(res->{
                    if(res.succeeded()){
                        System.out.println("Client Connected");
                    }else{
                        System.out.println("Failed to connect "+ res.cause().getMessage() );
                    }
                });
        promise.complete();
    }
}
