package org.example;

import io.javalin.Javalin;

import java.util.Map;

public class JavalinServer {
    public static void main(String[] args) {
        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.http.defaultContentType = "text/plain; charset=UTF-8";
        }).start(7000);

        //GET simples - resposta direta
        app.get("/hello", ctx -> ctx.result("Olá Javalin!"));

        //POST - lê o corpo da requisição
        app.post("/echo", ctx -> {
            String msg = ctx.body();
            ctx.status(201);
            ctx.result("Você disse: " + msg);
        });

        //GET com queryParam: /saudacao?nome=Bernardo
        app.get("/saudacao", ctx -> {
            String nome = ctx.queryParam("nome");
            ctx.result("Olá " + nome + "!");
        });

        //GET com path param: /usuarios/45
        app.get("/usuarios/{id}", ctx -> {
            String id = ctx.pathParam("id");
            ctx.result("Usuários com ID: " + id);
        });

        //GET que retornar um JSON: /produto
        app.get("/produtos", ctx -> {
           var produto = Map.of("nome", "Camiseta", "preco", 59.90);
           ctx.json(produto);
        });

        //GET /produtos/88/comentarios?ordem=mais-antiga
        app.get("/produtos/{id}/comentarios/{id_comentario}", ctx -> {
            String id = ctx.pathParam("id");
            String idComentario = ctx.pathParam("id_comentario");
            String filtro = ctx.queryParam("ordem");
            String pagina = ctx.queryParam("pagina");
            var teste = ctx.queryParamMap();
            System.out.println(teste);
            ctx.result("Comentários do produto: " + id +
                    " - Ordem: " + filtro +
                    " | Página: " + pagina +
                    " - ComentarioID: " + idComentario);
        });
    }
}
