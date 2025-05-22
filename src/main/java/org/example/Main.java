package org.example;

import com.google.gson.Gson;
import org.example.client.SimpleHttpClient;
import org.example.dtos.AgePrediction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        //exemple1();
        //jsonplaceholderExemple();
        //agifyExemple();

        String age = SimpleHttpClient.get("https://api.agify.io/?name=bernardo");
        Gson gson = new Gson();
        AgePrediction prediction = gson.fromJson(age, AgePrediction.class);

        System.out.println("Nome: " + prediction.name());
        System.out.println("Idade estimada: " + prediction.age());

        System.out.println(prediction);


//        try {
//            String post = SimpleHttpClient.get("https://jsonplaceholder.typicode.com/posts/2");
//            System.out.println("Post: " + post);
//
//            String age = SimpleHttpClient.get("https://api.agify.io/?name=bruna");
//            System.out.println("Idade estimada: " + age);
//        } catch (IOException ex) {
//            System.out.println("error: " + ex.getMessage());
//        }

    }

    private static void agifyExemple() throws URISyntaxException, IOException {
        URL url = new URI("https://api.agify.io/?name=bernardo").toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );

        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine).append('\n');
        }

        in.close();
        conn.disconnect();

        System.out.println("Idade aproximada: " + content);
    }

    private static void jsonplaceholderExemple() throws URISyntaxException, IOException {
        URL url = new URI("https://jsonplaceholder.typicode.com/posts/1").toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );

        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine).append('\n');
        }

        in.close();
        conn.disconnect();

        System.out.println("Resposta JSON: " + content);
    }

    private static void exemple1() throws IOException {
        // 1. Criar URL
        URL url = new URL("https://api.exemplo.com/dados");

        // 2. Abrir conexão
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        //3. Definir Método
        conn.setRequestMethod("GET");

        // 4. Definir headers
        conn.setRequestProperty("Accept", "application/json");

        // 5. Obter status Http
        int status = conn.getResponseCode();

        //6. Ler a resposta
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );

        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        // 7. Fechar os recursos
        in.close();
        conn.disconnect();
    }
}