package main.java.com.ors.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ObtenerIPPublica {
	public static String devolver() {
		try {
			String url = "https://api.ipify.org"; //esta es la api que nos da la ip
			URI i = new URI(url);
			URL obj = i.toURL();

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			return (response.toString());
		} catch (Exception e) {
			System.err.println("Error al obtener la IP pública: " + e.getMessage());
		}
		return null;
	}
}
