package main.java.com.ors.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.com.ors.vo.Adventure;
import main.java.com.ors.vo.PJ;

public class PjService {

	private static final String API_BASE_URL = "http://localhost:8080/api/pj";
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final HttpClient client = HttpClient.newHttpClient();

	private static HttpResponse<String> postJson(String url, Object body) throws Exception {
		String json = mapper.writeValueAsString(body);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(json)).build();

		return client.send(request, HttpResponse.BodyHandlers.ofString());
	}

	private static HttpResponse<String> putJson(String url, Object body) throws Exception {
		String json = mapper.writeValueAsString(body);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Content-Type", "application/json")
				.PUT(HttpRequest.BodyPublishers.ofString(json)).build();

		return client.send(request, HttpResponse.BodyHandlers.ofString());
	}

	// DEben de ser peticiones que reciban la imagen como tal
	public static Byte[] getFotoPj(PJ pj) {
	}

	public static Byte[] changeFotoPj(PJ pj) {
	}

	public static List<PJ> getCompletePJs(Adventure adventure, boolean dm) throws Exception {
		String json = mapper.writeValueAsString(adventure);

		String url = API_BASE_URL + "/completos?dm=" + dm;

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(json)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() == 200) {
			return mapper.readValue(response.body(), new TypeReference<List<PJ>>() {
			});
		} else {
			throw new RuntimeException(
					"Error al obtener personajes: " + response.statusCode() + " - " + response.body());
		}
	}

	public static void update(PJ pj) throws Exception { // Updatea los valores y GUARDA en la bbdd
		System.out.println("GUARDADO ---------------------------------------------------------------------------");
		String json = mapper.writeValueAsString(pj);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL + "/update"))
				.header("Content-Type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(json)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al actualizar PJ: " + response.statusCode() + " - " + response.body());
		}
	}

	public static void setBasicHitter(PJ pj) throws Exception {
		String json = mapper.writeValueAsString(pj);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL + "/calcular/basic-hitter"))
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al calcular golpe básico: " + response.body());
		}
	}

	private static String calculMayorStat(PJ pj) {
		Map<String, Integer> stats = new HashMap<>();
		stats.put("atl", pj.getAtl());
		stats.put("str", pj.getStr());
		stats.put("end", pj.getEnd());
		stats.put("min", pj.getMin());
		stats.put("dex", pj.getDex());
		stats.put("acr", pj.getAcrobatics());
		stats.put("per", pj.getPreception());
		stats.put("cha", pj.getCharisma());
		return stats.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("balance");
	}

	public static void setSecondaryStats(PJ pj) throws Exception {
		String json = mapper.writeValueAsString(pj);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL + "/calcular/secondary-stats"))
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al calcular estadísticas secundarias: " + response.body());
		}
	}

	public static void setMods(PJ pj) throws Exception {
		String json = mapper.writeValueAsString(pj);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL + "/calcular/mods"))
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al calcular modificadores: " + response.body());
		}
	}

	public static void setLevelCheck(PJ pj) throws Exception {
		String json = mapper.writeValueAsString(pj);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL + "/calcular/level-check"))
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al comprobar nivel: " + response.body());
		}
	}

	public static int getStat(String type, PJ pj) {
		switch (type) {
		case "alt":
			return pj.getAtl();
		case "str":
			return pj.getStr();
		case "end":
			return pj.getEnd();
		case "min":
			return pj.getMin();
		case "dex":
			return pj.getDex();
		case "acr":
			return pj.getAcrobatics();
		case "vas":
			return pj.getVaste();
		case "per":
			return pj.getPreception();
		case "cha":
			return pj.getCharisma();
		}
		return 0;
	}

	public static List<PJ> getAll() throws Exception {
		String url = API_BASE_URL;
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().header("Accept", "application/json")
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() == 200) {
			return mapper.readValue(response.body(), new TypeReference<List<PJ>>() {
			});
		} else {
			throw new RuntimeException("Error al obtener todos los PJ: " + response.body());
		}
	}

	public static PJ getById(String id) throws Exception {
		String url = API_BASE_URL + "/" + id;
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().header("Accept", "application/json")
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() == 200) {
			return mapper.readValue(response.body(), PJ.class);
		} else if (response.statusCode() == 404) {
			return null;
		} else {
			throw new RuntimeException("Error al obtener PJ por id: " + response.body());
		}
	}

	public static void create(PJ pj) throws Exception {
		String json = mapper.writeValueAsString(pj);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL))
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al crear PJ: " + response.body());
		}
	}

	public static void updateFull(PJ pj) throws Exception {
		String json = mapper.writeValueAsString(pj);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL))
				.header("Content-Type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(json)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al actualizar PJ: " + response.body());
		}
	}

	public static void delete(PJ pj) throws Exception {
		String json = mapper.writeValueAsString(pj);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE_URL))
				.header("Content-Type", "application/json").method("DELETE", HttpRequest.BodyPublishers.ofString(json))
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al eliminar PJ: " + response.body());
		}
	}

}
