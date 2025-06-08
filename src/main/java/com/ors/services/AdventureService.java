package com.ors.services;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.ors.utiles.GestorFicheroConfiguracion;
import com.ors.vo.Adventure;

public class AdventureService {

	private static final String API_BASE_URL = ComunAlmacen.urlBase +"/api/adventures";
	private static final HttpClient client = HttpClient.newHttpClient();
	private static final ObjectMapper mapper = new ObjectMapper();

	public static List<Adventure> getAll() throws Exception {
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL)).GET().build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() == 200) {
			System.out.println(response.body().toString());
			return mapper.readValue(response.body(), new TypeReference<List<Adventure>>() {
			});
		} else {
			throw new RuntimeException("Error al obtener aventuras: " + response.body());
		}
	}

	public static Adventure getById(String id) throws Exception {
		String url = API_BASE_URL + "/" + URLEncoder.encode(id, StandardCharsets.UTF_8);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() == 200 && response.body() != null && !response.body().isEmpty()) {
			return mapper.readValue(response.body(), Adventure.class);
		} else if (response.statusCode() == 404) {
			return null;
		} else {
			throw new RuntimeException("Error al obtener aventura: " + response.body());
		}
	}

	public static void create(Adventure adventure) throws Exception {
		String json = mapper.writeValueAsString(adventure);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL))
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al crear aventura: " + response.body());
		}
	}

	public static void update(Adventure adventure) throws Exception {
		String json = mapper.writeValueAsString(adventure);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL))
				.header("Content-Type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(json)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al actualizar aventura: " + response.body());
		}
	}

	public static void delete(Adventure adventure) throws Exception {
		String json = mapper.writeValueAsString(adventure);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL))
				.header("Content-Type", "application/json").method("DELETE", HttpRequest.BodyPublishers.ofString(json))
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al eliminar aventura: " + response.body());
		}
	}
}
