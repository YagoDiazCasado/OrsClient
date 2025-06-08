package com.ors.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.ors.vo.Skill;

public class SkillService {

	private static final String API_BASE_URL = ComunAlmacen.urlBase +"/api/skills";
	private static final ObjectMapper mapper = new ObjectMapper();

	// 🧠 Obtener todas las skills
	public static List<Skill> getAll() throws Exception {
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(API_BASE_URL))
			.GET()
			.header("Accept", "application/json")
			.build();

		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() == 200) {
			return mapper.readValue(response.body(), new TypeReference<List<Skill>>() {});
		}
		throw new RuntimeException("Error al obtener skills: " + response.body());
	}

	// 🔍 Obtener una skill por ID
	public static Skill getById(String id) throws Exception {
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(API_BASE_URL + "/" + id))
			.GET()
			.header("Accept", "application/json")
			.build();

		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() == 200 && response.body() != null && !response.body().isEmpty()) {
			return mapper.readValue(response.body(), Skill.class);
		} else if (response.statusCode() == 404) {
			return null;
		}
		throw new RuntimeException("Error al obtener skill: " + response.statusCode() + " - " + response.body());
	}

	// ➕ Insertar skill
	public static void create(Skill skill) throws Exception {
		String json = mapper.writeValueAsString(skill);

		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(API_BASE_URL))
			.POST(HttpRequest.BodyPublishers.ofString(json))
			.header("Content-Type", "application/json")
			.build();

		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al crear skill: " + response.body());
		}
	}

	// ✏️ Actualizar skill
	public static void update(Skill skill) throws Exception {
		String json = mapper.writeValueAsString(skill);

		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(API_BASE_URL))
			.PUT(HttpRequest.BodyPublishers.ofString(json))
			.header("Content-Type", "application/json")
			.build();

		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al actualizar skill: " + response.body());
		}
	}

	// 🗑️ Eliminar skill
	public static void delete(Skill skill) throws Exception {
		String json = mapper.writeValueAsString(skill);

		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(API_BASE_URL))
			.method("DELETE", HttpRequest.BodyPublishers.ofString(json))
			.header("Content-Type", "application/json")
			.build();

		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al eliminar skill: " + response.body());
		}
	}
}
