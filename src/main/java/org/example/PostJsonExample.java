package org.example;

import com.google.gson.Gson;
import org.example.client.SimpleHttpClient;
import org.example.dtos.PostRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class PostJsonExample {
    public static void main(String[] args) {
        try {
            //postExemploMunheca();
            PostRequest post = new PostRequest("Cavaleiros dos zodiacos", "a vida é bela", 1);

            var url = "https://jsonplaceholder.typicode.com/posts";
            String postReponse = SimpleHttpClient.post(url, post);

            System.out.println("POST response: " + postReponse);

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void postExemploMunheca() throws URISyntaxException, IOException {
        URL url = new URI("https://jsonplaceholder.typicode.com/posts").toURL();

        var post = new PostRequest("Objetos em java", "Corpo em java", 2);
        Gson gson = new Gson();

        String json = gson.toJson(post);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");

        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

//            String jsonInputString = """
//                    {
//                        "title": "qualquer coisa",
//                        "body": "corpo do meu artigo",
//                        "userId": 1
//                    }
//                    """;

        try (OutputStream os = conn.getOutputStream()) {
            //byte [] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }

        int status = conn.getResponseCode();
        System.out.println("codigo de resposta: " + status);

        String resposta = new String(conn.getInputStream().readAllBytes());

        System.out.println("resposta da api: " + resposta);

        conn.disconnect();
    }
}
