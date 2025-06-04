package main.java.com.ors.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import main.java.com.ors.services.AdventureService;
import main.java.com.ors.services.ComunAlmacen;
import main.java.com.ors.services.StartService;
import main.java.com.ors.services.StyleAndEffectService;
import main.java.com.ors.utiles.GestorFicheroConfiguracion;
import main.java.com.ors.vo.Adventure;

public class LauncherController implements Initializable {

	public static Stage primaryStage = ComunAlmacen.sesion;
	private static StyleAndEffectService ser = ComunAlmacen.ser;
	public Double volumenFondo = ComunAlmacen.volumenFondo;
	public Double volumenEfectos = ComunAlmacen.volumenEfectos;
	private String rutaMusica = ComunAlmacen.rutaMusica;
	// el dm verá todos los pjs, no solo los de la party. Además, podrá editar
	// existentes y borrarlos
	public static boolean dm = ComunAlmacen.dm;

	// Saldrán solo pjs de la aventura elejida
	public static Adventure adventure = ComunAlmacen.adventure;

	// además, el able controlará que personajes de la party pueden o no usarse.
	// (implementar controles en la app)

	// Campaña selección
	@FXML
	private Pane adventureLogPanel;
	@FXML
	private ComboBox<Adventure> adventures;
	@FXML
	private TextField pasword;
	@FXML
	private Button go;

	// Seleccion tango de usuario. Inician apagados
	@FXML
	private Pane rangeChoosePanel;
	@FXML
	private Pane playerOption;
	@FXML
	private Label letraP;
	@FXML
	private Pane dmOption;
	@FXML
	private Label letraD;

	// COnexion, Inicia oculto
	@FXML
	private Pane conexionPanel;
	@FXML
	private Button iniciarJuegoBotton;
	@FXML
	private TextField ipField;
	@FXML
	private TextField portField;
	@FXML
	private TextField portServerField;
	@FXML
	private Button addAdventureBtn;
	@FXML
	private Button cargarBaseBtn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dm = false;
		try {
			Media sound = new Media(getClass().getResource(rutaMusica).toString());
			ComunAlmacen.mediaPlayer = new MediaPlayer(sound);
			ComunAlmacen.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Repetir en bucle
			ComunAlmacen.mediaPlayer.setVolume(volumenFondo);
			ComunAlmacen.mediaPlayer.play();
		} catch (Exception e) {
			System.out.println("No hay musica");
		}

		StyleAndEffectService.pointElement(ipField, 0.05, 0.2, "blue", "black");
		StyleAndEffectService.pointElement(portServerField, 0.05, 0.2, "blue", "black");
		StyleAndEffectService.pointElement(portField, 0.05, 0.2, "blue", "black");
		StyleAndEffectService.pointElement(playerOption, 0.05, 0.2, "blue", "black");
		StyleAndEffectService.pointElement(dmOption, 0.05, 0.2, "blue", "black");
		
		StyleAndEffectService.pointElement(letraP, 0.2, 0.2, "blue", "black");
		StyleAndEffectService.pointElement(letraD, 0.2, 0.2, "orange", "black");

		
		StyleAndEffectService.pointElement(pasword, 0.05, 0.2, "blue", "black");
		StyleAndEffectService.pointElement(adventures, 0.05, 0.2, "blue", "black");
		StyleAndEffectService.pointElement(iniciarJuegoBotton, 0.07, 0.3, "blue", "black");
		iniciarJuegoBotton.setOnMouseClicked(event -> {
			try {
				GestorFicheroConfiguracion.actualizarValor("puertoSockets", portField.getText());
				GestorFicheroConfiguracion.actualizarValor("ipServer", ipField.getText());
				GestorFicheroConfiguracion.actualizarValor("puerto", portServerField.getText());
				ComunAlmacen.urlBase = "http://" + ipField.getText() + ":" + portServerField.getText();
				List<Adventure> todas = AdventureService.getAll();
				adventures.getItems().addAll(todas);
				iniciarCampaing();
				setupAdventureCreation();
				reCrearBaseDatos();
				ser.efectoPasar(volumenEfectos);
			} catch (Exception e) { // Ha fallado la carga de la campaña
				e.printStackTrace();
				mostrarCuadroDePrompts(); // vuelve a tener que meter las credenciales
			}
		});
		mostrarCuadroDePrompts();
	}

	private void mostrarCuadroDePrompts() {
		portField.setText(GestorFicheroConfiguracion.devolverCredencial("puertoSockets"));
		portServerField.setText(GestorFicheroConfiguracion.devolverCredencial("puerto"));
		ipField.setText(GestorFicheroConfiguracion.devolverCredencial("ipServer"));
		ser.efectoEntrar(volumenEfectos);
	}

	private void reCrearBaseDatos() {
		StyleAndEffectService.pointElement(cargarBaseBtn, 0.3, 0.3, "blue", "black");
		cargarBaseBtn.setOnAction(event -> {
			StartService.CrearBase();
			cargarBaseBtn.setVisible(false);
			List<Adventure> todas;
			try {
				todas = AdventureService.getAll();
				adventures.getItems().addAll(todas);
				iniciarCampaing();
				setupAdventureCreation();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	private void setupAdventureCreation() {
		StyleAndEffectService.pointElement(addAdventureBtn, 0.3, 0.3, "blue", "black");
		addAdventureBtn.setOnAction(event -> {
			TextField nombreField = new TextField();
			nombreField.setPromptText("Nombre de la aventura");

			TextField passField = new TextField();
			passField.setPromptText("Contraseña (vacío si no)");

			Label infoLabel = new Label("Mete nombre y contraseña:");

			Pane formPane = new Pane();
			nombreField.setLayoutY(30);
			passField.setLayoutY(60);
			formPane.getChildren().addAll(infoLabel, nombreField, passField);

			javafx.scene.control.Dialog<Void> dialog = new javafx.scene.control.Dialog<>();
			dialog.setTitle("Nueva Aventura");
			dialog.getDialogPane().setContent(formPane);
			dialog.getDialogPane().getButtonTypes().addAll(
					new javafx.scene.control.ButtonType("Crear", javafx.scene.control.ButtonBar.ButtonData.OK_DONE),
					javafx.scene.control.ButtonType.CANCEL);

			dialog.setResultConverter(button -> {
				if (button.getButtonData() == javafx.scene.control.ButtonBar.ButtonData.OK_DONE) {
					String nombre = nombreField.getText().trim();
					String pass = passField.getText().trim();

					if (!nombre.isEmpty()) {
						try {
							Adventure nueva = new Adventure(nombre, pass);
							AdventureService.create(nueva);
							adventures.getItems().add(nueva);
							adventures.setValue(nueva);
							adventure = nueva;
							GestorFicheroConfiguracion.actualizarValor(nueva.getAdventureName(), nueva.getPasword());
							seleccionRango();
							ser.efectoEleccion(volumenEfectos);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
				return null;
			});
			dialog.showAndWait();
		});
	}

	private void iniciarCampaing() { // Si falla, nos devuelve al trycatch de antes

		adventureLogPanel.setVisible(true);
		adventureLogPanel.setDisable(false);
		conexionPanel.setVisible(false);
		conexionPanel.setDisable(true);
		adventures.getSelectionModel().selectFirst();
		
		StyleAndEffectService.pointElement(go, 0.3, 0.3, "blue", "black");
		go.setOnMouseClicked(event -> {
			try {
				adventure = (Adventure) AdventureService.getById(adventures.getValue().getAdventureName());
				if (!(pasword.getText().equals(""))) {
					if (pasword.getText().equals(adventure.getPasword())) {
						GestorFicheroConfiguracion.actualizarValor(adventure.getAdventureName(),
								adventure.getPasword());
						seleccionRango();
					} else {
						System.out.println("FAllo");
						System.out.println(adventures.getValue().getPasword());
						pasword.setStyle("-fx-radius-color: RED; -fx-background-color:RED");
					}
				} else {
					if (adventures.getValue().getPasword().equals(
							GestorFicheroConfiguracion.devolverCredencial(adventures.getValue().getAdventureName()))) {
						seleccionRango();
					}
				}
				ser.efectoEleccion(volumenEfectos);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private void seleccionRango() {
		adventureLogPanel.setVisible(false);
		adventureLogPanel.setDisable(true);
		rangeChoosePanel.setVisible(true);
		rangeChoosePanel.setDisable(false);
	}

	@FXML
	private void launchPlayer() throws IOException {
		System.out.println("Player");
		dm = false;
		iniciarJuego();
		ser.efectoEleccion(volumenEfectos);
	}

	@FXML
	private void launchDM() throws IOException {
		System.out.println("DM");
		dm = true;
		iniciarJuego();
		ser.efectoEleccion(volumenEfectos);
	}

	private void iniciarJuego() throws IOException {
		System.out.println("Intento");
		System.out.println(adventure.getAdventureName() + " entrando como " + dm);
		ComunAlmacen.adventure = adventure;
		ComunAlmacen.dm = dm;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ors/views/LoobyVista.fxml"));
			Parent root = loader.load();
			Scene ventana = new Scene(root);
			ventana.getStylesheets()
					.add(getClass().getResource(GestorFicheroConfiguracion.devolverCredencial("css")).toExternalForm());
			primaryStage.setTitle("LOBBY");
			primaryStage.setScene(ventana);
			primaryStage.setResizable(false);
			primaryStage.sizeToScene();
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
