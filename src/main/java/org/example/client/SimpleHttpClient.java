package org.example.client;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class SimpleHttpClient {

    private static final Gson gson = new Gson();
    public static final String APP_JSON = "application/json";

    public static String get(String urlString) throws URISyntaxException, IOException {
        URL url = new URI(urlString).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        setCommunsPropertys(conn, "GET");
        checkResponse(conn.getResponseCode());
        return lerResposta(conn);
    }

    public static String post(String urlString, Object body) throws URISyntaxException, IOException {
        URL url = new URI(urlString).toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        setCommunsPropertys(conn, "POST");
        conn.setRequestProperty("Content-Type", APP_JSON);
        conn.setDoOutput(true);

        String jsonInput = gson.toJson(body);
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            os.write(input);
        }

        checkResponse(conn.getResponseCode());

        return lerResposta(conn);
    }

    private static void setCommunsPropertys(HttpURLConnection conn, String method) throws ProtocolException {
        conn.setRequestMethod(method);
        conn.setRequestProperty("Accept", APP_JSON);
    }

    private static void checkResponse(int status) throws IOException {
        if (status != 200 && status != 201) {
            throw new IOException("Erro na requisição POST. Codigo HTTP: " + status);
        }
    }

    private static String lerResposta(HttpURLConnection conn) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine).append('\n');
        }

        in.close();
        conn.disconnect();
        return response.toString();
    }
}
