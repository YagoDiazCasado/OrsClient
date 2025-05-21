package main.java.com.ors.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class EscucharConexion implements Runnable {

	private Socket sC;
	private Server server;

	public EscucharConexion(Server s, Socket conexionClient) {
		this.sC = conexionClient;
		this.server = s;
	}

	@Override
	public void run() {
		while (true) {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(sC.getInputStream()));
				String mensaje = reader.readLine();
				if (!mensaje.equals(null)) {
					server.updateAll(mensaje);
					System.out.println("Mensaje nuevo " + mensaje);
				}
			} catch (IOException e) {
				break;
			}
		}
	}
}
