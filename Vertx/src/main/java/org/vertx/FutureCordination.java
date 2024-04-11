package org.vertx;

import io.vertx.core.*;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

import java.util.Arrays;
import java.util.Set;

public class FutureCordination extends AbstractVerticle
{
    Future<String> getInteger()
    {
        Promise<String> pro = Promise.promise();
        this.getVertx().setTimer(2000, e -> {
            System.out.println("getInteger Method Called");
            pro.complete("Integer");
        });
        return pro.future();
    }

    Future<String> getString()
    {
        Promise<String> pro = Promise.promise();
        this.getVertx().setTimer(4000, e -> {
            System.out.println("getString Method Called");
            pro.complete("String");
        });
        return pro.future();
    }

    Future<String> getDouble()
    {
        Promise<String> pro = Promise.promise();
        this.getVertx().setTimer(6000, e -> {
            System.out.println("getDouble Method Called");
            pro.complete("Double");
        });
        return pro.future();
    }

        @Override
    public void start()
    {
        Future.all(Arrays.asList(getString(), getInteger(), getDouble())).onComplete(result -> {
            if(result.succeeded())
            {
                var resultArr = result.result();
                for(int i = 0; i < resultArr.size(); i++)
                {
                    System.out.println(resultArr.resultAt(i).toString());
                }
            }
            else
            {
                System.out.println("Failed Message: " + result.cause().getMessage());
            }

        });
    }

//    @Override
//    public void start(Promise<Void> promise)
//    {
//        Future.all(Arrays.asList(getString(), getInteger(), getDouble()))
//                .onComplete(result -> {
//                    if(result.succeeded())
//                    {
//                        var resultArr = result.result();
//                        System.out.println("result is here");
//                        System.out.println(resultArr.resultAt(2).toString());
//                    }
//                    else
//                    {
//                        System.out.println("Failed Message: " + result.cause().getMessage());
//                    }
//                });
//
//                //
//                //        Future.any(Arrays.asList(getFailingString(), getString(), getInteger(), getDouble())).onComplete(result -> {
//                //            if(result.succeeded())
//                //            {
//                //                var resultArr = result.result();
//                //                for(int i = 0; i < resultArr.size(); i++)
//                //                {
//                //                    if(resultArr.resultAt(i) != null)
//                //                        System.out.println(resultArr.resultAt(i).toString());
//                //                }
//                //            }
//                //            else
//                //            {
//                //                System.out.println("Failed Message: " + result.cause().getMessage());
//                //            }
//                //        });
//
//
//                //        Future.join(Arrays.asList(getString(), getInteger(), getDouble())).onComplete(result -> {
//                //            if(result.succeeded())
//                //            {
//                //                var resultArr = result.result();
//                //                for(int i = 0; i < resultArr.size(); i++)
//                //                {
//                //                    System.out.println(resultArr.resultAt(i).toString());
//                //                }
//                //            }
//                //            else
//                //            {
//                //                System.out.println("Failed Message: " + result.cause().getMessage());
//                //            }
//                //        });
//                promise.complete();
//        }

    @Override
    public void stop(Promise<Void> stopPromise)
    {
        System.out.println("Stopping the verticle");
        stopPromise.complete();
    }

    public static void main(String[] args)
    {

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle("org.vertx.FutureCordination", res -> {
            if(res.succeeded()){
                Set<String> str = vertx.deploymentIDs();
                System.out.println(str);
            }else{
                System.out.println("Hello");
                System.out.println(res.cause());
            }
        });

    }
}