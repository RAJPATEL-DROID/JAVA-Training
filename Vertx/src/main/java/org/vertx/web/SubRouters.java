package org.vertx.web;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class SubRouters
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        Router mainRouter = Router.router(vertx);

        Router restAPI = Router.router(vertx);

        mainRouter.route("/productsAPI/*").subRouter(restAPI);

        restAPI.get("/products/:productID").handler(ctx -> {


            ctx.response().setChunked(true);

            ctx.response().write("hello from restAPI router\n");

            ctx.response().end(String.format("Product %s is here" , ctx.pathParam("productID")));

        });

        restAPI.put("/products/:productID").handler(ctx -> {


            ctx.response().end("Product updated");

        });

        restAPI.delete("/products/:productID").handler(ctx ->
        {
            ctx.response().end(String.format("Product with Id %s deleted" , ctx.pathParam("productID")));

        });

        mainRouter.route("/home").handler(ctx ->
        {
            ctx.response().end("Welcome to Home page");
        });



        vertx.createHttpServer().requestHandler(mainRouter).listen(8080, result ->
        {
           if(result.succeeded())
           {
               System.out.println("Server is listening on port 8080");
           }
           else
           {
               System.out.println(result.cause().getMessage());
           }
        });
    }
}
