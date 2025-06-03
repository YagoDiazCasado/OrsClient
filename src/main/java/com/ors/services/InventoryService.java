package main.java.com.ors.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.com.ors.utiles.EnumsDeItems.ItemShape;
import main.java.com.ors.vo.Inventory;
import main.java.com.ors.vo.Item;
import main.java.com.ors.vo.PJ;

public class InventoryService {

	private static final String BASE_URL = ComunAlmacen.urlBase + "/api/inventory";
	private static final HttpClient client = HttpClient.newHttpClient();
	private static final ObjectMapper mapper = new ObjectMapper();

	public static void insertar(Inventory inv) throws Exception {
		System.out.println("Valor de id_pj ahora: " + inv.getPj().getName());
		String json = mapper.writeValueAsString(inv);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/insert"))
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();
		client.send(request, HttpResponse.BodyHandlers.ofString());
	}

	public static double getPeso(PJ pj) throws Exception {
		String json = mapper.writeValueAsString(pj);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/peso"))
				.header("Content-Type", "application/json").method("GET", HttpRequest.BodyPublishers.ofString(json))
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		return Double.parseDouble(response.body());
	}

	public static HashMap<Long, Integer> getInventarioDePJ(PJ pj) throws Exception {
		String json = mapper.writeValueAsString(pj);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/porPJ"))
				.header("Content-Type", "application/json").method("GET", HttpRequest.BodyPublishers.ofString(json))
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		return mapper.readValue(response.body(), new TypeReference<>() {
		});
	}

	public static boolean existeObjeto(String nombre, PJ pj) throws Exception {
		String json = mapper.writeValueAsString(pj);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/existe?nombre=" + nombre))
				.header("Content-Type", "application/json").method("GET", HttpRequest.BodyPublishers.ofString(json))
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		return Boolean.parseBoolean(response.body());
	}

	public static void usarMunicion(ItemShape tipo, PJ pj, int cantidad) throws Exception {
		String json = mapper.writeValueAsString(pj);
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(BASE_URL + "/usar-municion?tipo=" + tipo + "&cantidad=" + cantidad))
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();
		client.send(request, HttpResponse.BodyHandlers.ofString());
	}

	public static void tirar(Item item, PJ pj, int cantidad) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("item", item);
		map.put("pj", pj);
		map.put("cantidad", cantidad);
		String json = mapper.writeValueAsString(map);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/tirar"))
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();
		client.send(request, HttpResponse.BodyHandlers.ofString());
	}

	public static String cantidadAmmo(PJ pj, ItemShape tipo) throws Exception {
		String json = mapper.writeValueAsString(pj);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/cantidad-ammo?tipo=" + tipo))
				.header("Content-Type", "application/json").method("GET", HttpRequest.BodyPublishers.ofString(json))
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		return response.body();
	}

	public static int getCantidad(PJ pj, Long itemId) throws Exception {
		String json = mapper.writeValueAsString(pj);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/cantidad?itemId=" + itemId))
				.header("Content-Type", "application/json").method("GET", HttpRequest.BodyPublishers.ofString(json))
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		return Integer.parseInt(response.body());
	}

	public static void sumar(PJ pj, Item item, int cantidad) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("pj", pj);
		map.put("item", item);
		map.put("cantidad", cantidad);
		String json = mapper.writeValueAsString(map);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/sumar"))
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();
		client.send(request, HttpResponse.BodyHandlers.ofString());
	}
}
