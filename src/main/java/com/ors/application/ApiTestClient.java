package main.java.com.ors.application;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiTestClient {

    public static void main(String[] args) {
        // Cambia esta URL base según el entorno que estés usando
        String baseUrl = "http://localhost:9000";

        // === PRUEBAS DISPONIBLES ===
        // descomenta una a una para probar

        sendPost(baseUrl + "/api/start", null);
        // sendGet(baseUrl + "/api/pj");
        // sendDelete(baseUrl + "/api/pj/Isaac");

    }

    public static void sendPost(String urlStr, String jsonBody) {
        sendRequest("POST", urlStr, jsonBody);
    }

    public static void sendGet(String urlStr) {
        sendRequest("GET", urlStr, null);
    }

    public static void sendPut(String urlStr, String jsonBody) {
        sendRequest("PUT", urlStr, jsonBody);
    }

    public static void sendDelete(String urlStr) {
        sendRequest("DELETE", urlStr, null);
    }

    private static void sendRequest(String method, String urlStr, String jsonBody) {
        try {
            System.out.println("\n--- " + method + " " + urlStr + " ---");

            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json");

            if (jsonBody != null && !jsonBody.isBlank()) {
                con.setDoOutput(true);
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                try (OutputStream os = con.getOutputStream()) {
                    os.write(input, 0, input.length);
                }
            }

            int status = con.getResponseCode();
            System.out.println("Status: " + status);

            // Leer respuesta (opcional)
            try (var reader = new java.util.Scanner(con.getInputStream())) {
                System.out.println("Respuesta:");
                while (reader.hasNextLine()) {
                    System.out.println(reader.nextLine());
                }
            } catch (Exception e) {
                System.out.println("Sin cuerpo de respuesta o error al leerlo.");
            }

            con.disconnect();
        } catch (Exception e) {
            System.out.println("Error al hacer la petición:");
            e.printStackTrace();
        }
    }
}
