package org.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

public class TCPServer extends AbstractVerticle
{
    @Override
    public void start(Promise<Void> promise){
            NetServerOptions options = new NetServerOptions().setPort(8080);
        NetServer server = vertx.createNetServer(options);

        server.connectHandler(socket ->{
            System.out.println("Socket connection successful! on " + socket.localAddress()+ " from " + socket.remoteAddress());
        });

        server.listen(8080,"localhost")
                .onComplete(res->{
                    if(res.succeeded()){
                        System.out.println("Server is now listening");
                    }else{
                        System.out.println("Failed to bind");
                    }
                });
    }
}
