package com.ors.application;

import java.io.IOException;
import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import com.ors.services.ComunAlmacen;
import com.ors.utiles.GestorFicheroConfiguracion;

public class MainLobby extends Application {

	public static Stage pS;

	@Override
	public void start(Stage primaryStage) throws Exception {
		pS = primaryStage;
		ComunAlmacen.sesion = pS;
		iniciarLauncher(primaryStage);
	}

	private void iniciarLauncher(Stage primaryStage) throws IOException {
		try {
			try {
				InputStream iconStream = getClass().getResourceAsStream("/com/ors/images/icono.png");
				primaryStage.getIcons().add(new Image(iconStream));
			} catch (Exception e) {
				System.out.println("Error: No se ha encontrado el icono");
				;
			}
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ors/views/LauncherVista.fxml"));
			Parent root = loader.load();
			root.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-background-radius: 15;");
			Scene ventana = new Scene(root);
			ventana.getStylesheets()
					.add(getClass().getResource(GestorFicheroConfiguracion.devolverCredencial("css")).toExternalForm());
			primaryStage.setTitle("LAUNCHER");
			primaryStage.setScene(ventana);
			primaryStage.sizeToScene();
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}