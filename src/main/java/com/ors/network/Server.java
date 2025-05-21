package main.java.com.ors.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

	private static List<Socket> conexiones = new ArrayList<Socket>();
	public ServerSocket socket;

	public Server() {
	}

	public void iniciarServer() throws IOException {
		socket = new ServerSocket(1234, 50, InetAddress.getByName("0.0.0.0"));
		try {
			while (true) {
				Socket conexionClient = socket.accept();
				System.out.println(conexionClient == null);
				conexiones.add(conexionClient);
				Thread cli = new Thread(new EscucharConexion(this, conexionClient));
				cli.start();
				cli.isDaemon();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			socket.close();
		}
	}

	public void updateAll(String mensaje) {

		System.out.println(conexiones.size() + " : clientes conectados");
		for (Socket c : conexiones) {
			try {
				PrintWriter w = new PrintWriter(c.getOutputStream(), true);
				w.println(mensaje);
				System.out.println("Mensaje enviado desde el server");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
