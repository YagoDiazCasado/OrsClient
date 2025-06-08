package com.ors.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class StartService {

	public static void CrearBase() {
		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ComunAlmacen.urlBase + "/api/start"))
				.POST(HttpRequest.BodyPublishers.noBody()).build();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			System.out.println("Código de respuesta: " + response.statusCode());
			System.out.println("Respuesta: " + response.body());

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
