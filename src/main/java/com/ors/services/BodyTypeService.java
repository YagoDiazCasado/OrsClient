
package main.java.com.ors.services;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.com.ors.vo.BodyType;

public class BodyTypeService {

	private static final String API_BASE_URL = ComunAlmacen.urlBase + "/api/bodytypes";
	private static final HttpClient client = HttpClient.newHttpClient();
	private static final ObjectMapper mapper = new ObjectMapper();

	public static List<BodyType> getAll() throws Exception {
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL)).GET()
				.header("Accept", "application/json").build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() == 200) {
			return mapper.readValue(response.body(), new TypeReference<List<BodyType>>() {
			});
		} else {
			throw new RuntimeException("Error en getAll(): " + response.body());
		}
	}

	public static BodyType obtenerPorId(String id) throws Exception {
		String url = API_BASE_URL + "/" + URLEncoder.encode(id, StandardCharsets.UTF_8);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().header("Accept", "application/json")
				.build();

		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body().toString());

		if (response.statusCode() == 200 && response.body() != null && !response.body().isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			System.out.println("TEngo ya el " + response.body().toString());
			return mapper.readValue(response.body(), BodyType.class);
		} else if (response.statusCode() == 404) {
			return null;
		} else {
			throw new RuntimeException(
					"Error al acceder al BodyType: " + response.statusCode() + " - " + response.body());
		}
	}

	public static void create(BodyType bodyType) throws Exception {
		String json = mapper.writeValueAsString(bodyType);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL))
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() != 200 && response.statusCode() != 201) {
			throw new RuntimeException("Error en create(): " + response.body());
		}
	}

	public static void update(BodyType bodyType) throws Exception {
		String json = mapper.writeValueAsString(bodyType);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL))
				.header("Content-Type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(json)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			throw new RuntimeException("Error en update(): " + response.body());
		}
	}

	public static void delete(BodyType bodyType) throws Exception {
		String json = mapper.writeValueAsString(bodyType);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL))
				.header("Content-Type", "application/json").method("DELETE", HttpRequest.BodyPublishers.ofString(json))
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			throw new RuntimeException("Error en delete(): " + response.body());
		}
	}
}
