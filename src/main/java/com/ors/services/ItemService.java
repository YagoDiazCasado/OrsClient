package com.ors.services;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.ors.utiles.Combo;
import com.ors.vo.Item;
import com.ors.vo.PJ;

public class ItemService {

	private static final String API_BASE_URL = ComunAlmacen.urlBase + "/api/items";
	private static final HttpClient client = HttpClient.newHttpClient();
	private static final ObjectMapper mapper = new ObjectMapper();

	public static Combo atacar(Item actual, int difficulty, boolean adventage, PJ pj) throws Exception {
		String url = API_BASE_URL + "/combat/atacar" + "?dificultad=" + difficulty + "&ventaja=" + adventage + "&item="
				+ URLEncoder.encode(actual.getName(), StandardCharsets.UTF_8);

		Combo c = new Combo(pj, 0, difficulty, adventage, actual);

		String json = mapper.writeValueAsString(c);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(json)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() == 200) {
			return mapper.readValue(response.body(), Combo.class);
		} else {
			throw new RuntimeException("Error en ataque: " + response.body());
		}
	}

	public static List<Item> getAll() throws Exception {
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL)).GET().build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() == 200) {
			return mapper.readValue(response.body(), new TypeReference<List<Item>>() {
			});
		} else {
			throw new RuntimeException("Error al obtener ítems: " + response.body());
		}
	}

	public static Optional<Item> getById(Long id) throws Exception {
		String url = API_BASE_URL + "/" + id;

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() == 200 && response.body() != null && !response.body().isEmpty()) {
			Item item = mapper.readValue(response.body(), Item.class);
			return Optional.of(item);
		} else if (response.statusCode() == 404) {
			return Optional.empty();
		} else {
			throw new RuntimeException("Error al obtener ítem por ID: " + response.body());
		}
	}

	public static Optional<Item> getByName(String name) throws Exception {
		String url = API_BASE_URL + "/nombre/" + URLEncoder.encode(name, StandardCharsets.UTF_8);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() == 200 && response.body() != null && !response.body().isEmpty()) {
			Item item = mapper.readValue(response.body(), Item.class);
			return Optional.of(item);
		} else if (response.statusCode() == 404) {
			return Optional.empty();
		} else {
			throw new RuntimeException("Error al obtener ítem por nombre: " + response.body());
		}
	}

	public static void create(Item item) throws Exception {
		String json = mapper.writeValueAsString(item);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL))
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al crear ítem: " + response.body());
		}
	}

	public static void update(Item item) throws Exception {
		String json = mapper.writeValueAsString(item);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL))
				.header("Content-Type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(json)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al actualizar ítem: " + response.body());
		}
	}

	public static void delete(Item item) throws Exception {
		String json = mapper.writeValueAsString(item);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL))
				.header("Content-Type", "application/json").method("DELETE", HttpRequest.BodyPublishers.ofString(json))
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al eliminar ítem: " + response.body());
		}
	}

	public static List<Item> buscarItemsQueEmpiecenPor(String input) throws Exception {
		String url = API_BASE_URL + "/filtrados/" + URLEncoder.encode(input, StandardCharsets.UTF_8);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() == 200) {
			return mapper.readValue(response.body(), new TypeReference<List<Item>>() {
			});
		} else {
			throw new RuntimeException("Error al obtener ítems: " + response.body());
		}
	}

}
