package org.example.produtos_crud;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProdutoCrud {
    public static void main(String[] args) {
        List<Produto> produtos = new ArrayList<>();
        AtomicInteger idGenerator = new AtomicInteger(1);

        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.http.defaultContentType = "application/json; charset=UTF-8";
        }).start(7000);

        //GET - listar todos os produtos
        app.get("/produtos", ctx -> ctx.json(produtos));

        //GET - buscar por ID
        app.get("/produtos/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Produto produto = produtos.stream()
                    .filter(p -> p.id == id)
                    .findFirst()
                    .orElseThrow(() -> new NotFoundResponse("Produto não encontrado"));
            ctx.json(produto);
        });

        //POST - Criar novo produto
        app.post("/produtos", ctx -> {
            String nome = ctx.body();
            Produto novoProduto = new Produto(idGenerator.getAndIncrement(), nome);
            produtos.add(novoProduto);
            ctx.status(HttpStatus.CREATED).json(novoProduto);
        });

        //PUT - Alterar um produto
        app.put("/produtos/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            String novoNome = ctx.body();
            Produto produto = produtos.stream()
                    .filter(p -> p.id == id)
                    .findFirst()
                    .orElseThrow(() -> new NotFoundResponse("Produto não encontrado"));
            produto.nome = novoNome;
            ctx.json(produto);
        });

        app.delete("/produtos/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean removido = produtos.removeIf(p -> p.id == id);
            if (!removido) {
                throw new NotFoundResponse("Produto não encontrado!");
            }
            ctx.status(HttpStatus.NO_CONTENT);
        });


    }
}
