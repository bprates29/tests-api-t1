package org.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class SimpleHttpClient {

    public static String get(String urlString) throws URISyntaxException, IOException {
        URL url = new URI(urlString).toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int status = conn.getResponseCode();
        if (status != 200) {
            throw new IOException("Erro na requisição. Codigo HTTP: " + status);
        }

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
