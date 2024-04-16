package org.vertx.web;


import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.Router;


public class HelperFunction
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        Router router = Router.router(vertx);

        router.get("/pdf").handler(ctx -> {

            vertx.fileSystem().readFile("/home/raj/Downloads/Gulzar.pdf", ar -> {
                if(ar.succeeded())
                {
                    Buffer buffer = ar.result();

                    /* Pdf as Attachment */
                    //                        ctx.attachment("Gulzar.pdf")
                    //                                .response().putHeader("Content-Type","application/pdf")
                    //                                .end(buffer);

                    /* pdf as view */
                    ctx.response().putHeader("Content-Type", "application/pdf").end(buffer);

                }
                else
                {
                    ctx.response().setStatusCode(500).end("Error: " + ar.cause().getMessage());
                }
            });
        });


        vertx.createHttpServer().requestHandler(router).listen(8080, httpServerAsyncResult -> {
            if(httpServerAsyncResult.succeeded())
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
