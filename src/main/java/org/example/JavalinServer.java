package org.example;

import io.javalin.Javalin;

public class JavalinServer {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        app.get("/hello", ctx -> {
            ctx.contentType("text/plain; charset=UTF-8");
            ctx.result("Olá Javalin!");
        });
    }
}
