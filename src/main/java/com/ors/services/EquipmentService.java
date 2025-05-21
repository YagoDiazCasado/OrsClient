package main.java.com.ors.services;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.com.ors.vo.Equipment;
import main.java.com.ors.vo.PJ;

public class EquipmentService {

	private static final String API_BASE_URL = "http://localhost:8080/api/equipments";
	private static final HttpClient client = HttpClient.newHttpClient();
	private static final ObjectMapper mapper = new ObjectMapper();

	public static List<Equipment> getAll() throws Exception {
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL)).GET().build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() == 200) {
			return mapper.readValue(response.body(), new TypeReference<List<Equipment>>() {
			});
		} else {
			throw new RuntimeException("Error al obtener todos los Equipment: " + response.body());
		}
	}

	public static Equipment getById(PJ pj) throws Exception {
		String pjId = URLEncoder.encode(pj.getName(), StandardCharsets.UTF_8);
		String url = API_BASE_URL + "/" + pjId;

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() == 200) {
			return mapper.readValue(response.body(), Equipment.class);
		} else if (response.statusCode() == 404) {
			return null;
		} else {
			throw new RuntimeException("Error al obtener Equipment: " + response.body());
		}
	}

	public static void create(Equipment equipment) throws Exception {
		String json = mapper.writeValueAsString(equipment);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL))
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al crear Equipment: " + response.body());
		}
	}

	public static void update(Equipment equipment) throws Exception {
		String json = mapper.writeValueAsString(equipment);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL))
				.header("Content-Type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(json)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al actualizar Equipment: " + response.body());
		}
	}

	public static void delete(Equipment equipment) throws Exception {
		String json = mapper.writeValueAsString(equipment);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL))
				.header("Content-Type", "application/json").method("DELETE", HttpRequest.BodyPublishers.ofString(json))
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al eliminar Equipment: " + response.body());
		}
	}
}
