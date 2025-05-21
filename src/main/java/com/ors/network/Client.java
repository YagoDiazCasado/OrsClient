package main.java.com.ors.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;

import main.java.com.ors.controllers.CharacterController;
import main.java.com.ors.utiles.GestorFicheroConfiguracion;

public class Client implements Serializable {

	private static final long serialVersionUID = 1L;

	public transient Socket cliente;
	private Thread escucha;
	private CharacterController c;

	public Client(CharacterController c) throws Exception {
		this.c = c;
		try {
			cliente = new Socket(GestorFicheroConfiguracion.devolverCredencial("ipServer"),
					Integer.parseInt(GestorFicheroConfiguracion.devolverCredencial("puertoSockets")));
			escucharServidor();
		} catch (Exception e) {
			System.out.println("No me he podido conectar");
			String ip = ObtenerIPPublica.devolver();
			System.out.println("No esta encendido el servidor. Hosteando como Server desde la ip de este PC: " + ip
					+ "\nPuerto :" + GestorFicheroConfiguracion.devolverCredencial("puertoSockets"));
			GestorFicheroConfiguracion.actualizarValor("ipServer", ip);
			Thread server = new Thread(() -> {
				Server s = new Server();
				try {
					s.iniciarServer();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			server.isDaemon();
			server.start();
			try {
				Thread.sleep(500);
				cliente = new Socket(ip,
						Integer.parseInt(GestorFicheroConfiguracion.devolverCredencial("puertoSockets")));
				escucharServidor();
			} catch (Exception e1) {
				e.printStackTrace();
				System.out.println(
						"Probablemente el puerto 1234 no sea accesible en tu PC. Trata de cambiarlo en la pestaña de launcehr de la aplicacion");
				throw new Exception("No se pudo iniciar");
			}
		}
	}

	private void escucharServidor() {
		escucha = new Thread(() -> {
			try {
				BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
				while (true) {
					String mensaje = entrada.readLine();
					if (mensaje != null) {
						System.out.println("Nuevo mensaje recibido: " + mensaje);
						update(mensaje);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		escucha.start();
	}

	public void update(Object mensaje) {
		if (mensaje instanceof String) {
			c.escribirConsola("" + mensaje);
		}
	}

	public void enviarMensaje(String text) {
		PrintWriter salida;
		try {
			salida = new PrintWriter(cliente.getOutputStream(), true);
			salida.println(text);
			System.out.println("Texto enviado desde el cliente: " + text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Socket getCliente() {
		return cliente;
	}

	public void setCliente(Socket cliente) {
		this.cliente = cliente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
