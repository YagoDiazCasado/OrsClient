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

import com.ors.vo.Race;

public class RaceService {

	private static final String API_BASE_URL = ComunAlmacen.urlBase +"/api/races";
	private static final ObjectMapper mapper = new ObjectMapper();

	// 🔍 Obtener todas las razas
	public static List<Race> getAll() throws Exception {
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(API_BASE_URL))
			.GET()
			.header("Accept", "application/json")
			.build();

		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() == 200) {
			return mapper.readValue(response.body(), new TypeReference<List<Race>>() {});
		}
		throw new RuntimeException("Error al obtener razas: " + response.body());
	}

	// 🔍 Obtener raza por ID
	public static Race getById(String id) throws Exception {
		String url = API_BASE_URL + "/" + URLEncoder.encode(id, StandardCharsets.UTF_8);

		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(url))
			.GET()
			.header("Accept", "application/json")
			.build();

		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() == 200 && response.body() != null && !response.body().isEmpty()) {
			return mapper.readValue(response.body(), Race.class);
		} else if (response.statusCode() == 404) {
			return null;
		}
		throw new RuntimeException("Error al obtener raza: " + response.statusCode() + " - " + response.body());
	}

	// ➕ Crear una nueva raza
	public static void create(Race race) throws Exception {
		String json = mapper.writeValueAsString(race);

		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(API_BASE_URL))
			.POST(HttpRequest.BodyPublishers.ofString(json))
			.header("Content-Type", "application/json")
			.build();

		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al crear raza: " + response.body());
		}
	}

	// ✏️ Actualizar raza
	public static void update(Race race) throws Exception {
		String json = mapper.writeValueAsString(race);

		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(API_BASE_URL))
			.PUT(HttpRequest.BodyPublishers.ofString(json))
			.header("Content-Type", "application/json")
			.build();

		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al actualizar raza: " + response.body());
		}
	}

	// 🗑️ Eliminar raza
	public static void delete(Race race) throws Exception {
		String json = mapper.writeValueAsString(race);

		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(API_BASE_URL))
			.method("DELETE", HttpRequest.BodyPublishers.ofString(json))
			.header("Content-Type", "application/json")
			.build();

		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al eliminar raza: " + response.body());
		}
	}
}
