package main.java.com.ors.utiles;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class GestionLog {

	public static void guardarLog(String error) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("errorLog.txt"))) {
			bw.write(error);
		} catch (Exception e) {
			System.err.println("Error al guardar el log: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
