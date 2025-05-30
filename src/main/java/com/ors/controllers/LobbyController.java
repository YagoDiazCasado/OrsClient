package main.java.com.ors.controllers;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.java.com.ors.services.AdventureService;
import main.java.com.ors.services.BodyTypeService;
import main.java.com.ors.services.ComunAlmacen;
import main.java.com.ors.services.PjService;
import main.java.com.ors.services.RaceService;
import main.java.com.ors.services.StyleAndEffectService;
import main.java.com.ors.utiles.EnumsDeItems.CharacterTypes;
import main.java.com.ors.utiles.GestorFicheroConfiguracion;
import main.java.com.ors.utiles.ImagenesUtil;
import main.java.com.ors.vo.Adventure;
import main.java.com.ors.vo.BodyType;
import main.java.com.ors.vo.PJ;
import main.java.com.ors.vo.Race;

public class LobbyController implements Initializable {

	// METER BOTON DE VOVLER ATRAS

	public Stage sesion = ComunAlmacen.sesion;
	public static PJ selected = ComunAlmacen.selected;
	public StyleAndEffectService ser = ComunAlmacen.ser;
	public Random dado = ComunAlmacen.dado;
	public double tama = ComunAlmacen.tama;
	public double brillo = ComunAlmacen.brillo;
	public String colorR = ComunAlmacen.colorR;
	public String colorTexto = ComunAlmacen.colorTexto;
	public Double volumenFondo = ComunAlmacen.volumenFondo;
	public Double volumenEfectos = ComunAlmacen.volumenEfectos;
	public boolean animaciones = ComunAlmacen.animaciones;
	public boolean dm = ComunAlmacen.dm;
	public Adventure adventure = ComunAlmacen.adventure;

	public File defaultImage;
	private List<PJ> pjs = new ArrayList<PJ>();
	private int posicion = 0;
	private boolean mirar;
	public File imagenDePerfilPosible;
	public static ImageView backF;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FXML

	@FXML
	private Pane loadingOverlay;

	@FXML
	private Pane absolutePane;
	@FXML
	private Button floor;
	@FXML
	private ImageView fotito;
	@FXML
	private ImageView leftImage;
	@FXML
	private ImageView centralImage;
	@FXML
	private ImageView rightImage;
	@FXML
	private Pane mapView;
	@FXML
	private Button nameButton;
	@FXML
	private ImageView backImage;
	@FXML
	private Pane totalPanel;
	@FXML
	private Pane characterPreviewPanel;
	@FXML
	private Button enterBtn;
	@FXML
	private Button rightSlideBtn;
	@FXML
	private Button leftSlideBtn;

	@FXML
	private Button editBtn;
	@FXML
	private Button createBtn;

	@FXML
	private Button eraseBtn;
	@FXML
	private Button unableBtn;
	@FXML
	private Button moveBtn;
	@FXML
	private ProgressBar atlBar;
	@FXML
	private ProgressBar strBar;
	@FXML
	private ProgressBar endBar;
	@FXML
	private ProgressBar minBar;
	@FXML
	private ProgressBar dexBar;
	@FXML
	private Label atlLabel;
	@FXML
	private Label strLabel;
	@FXML
	private Label endLabel;
	@FXML
	private Label minLabel;
	@FXML
	private Label dexLabel;
	@FXML
	private Label powerLabel;
	@FXML
	private Label altNum;
	@FXML
	private Label strNum;
	@FXML
	private Label endNum;
	@FXML
	private Label minNum;
	@FXML
	private Label dexNum;
	@FXML
	private Button listoBtn;
	@FXML
	private Slider altSlider;
	@FXML
	private Slider strSlider;
	@FXML
	private Slider endSlider;
	@FXML
	private Slider minSlider;
	@FXML
	private Slider dexSlider;
	@FXML
	private TextField newNameText;
	@FXML
	private Pane newNamePane;
	@FXML
	private Pane selectImagePanel;
	@FXML
	private ComboBox<String> raceOptions;
	@FXML
	private ComboBox<String> btOptions;
	@FXML
	private Label raceLabel;
	@FXML
	private Label btLabel;
	@FXML
	private Label glimmersLabel;
	@FXML
	private TextField glimmersText;
	@FXML
	private Label dropLabel;
	@FXML
	private ImageView fastSearchImg;
	@FXML
	private ComboBox<String> personajesSel;
	@FXML
	private ImageView goToPosition;
	@FXML
	private Pane bienvenidaPanel;
	@FXML
	private Label bienvenidaLabel;
	@FXML
	private Pane panelAjustes;
	@FXML
	private Button botonAjustes;
	@FXML
	private ComboBox<String> powerOptions;

	@FXML
	private Label volMuLabel;
	@FXML
	private Slider muSlider;
	@FXML
	private Label volEfLabel;
	@FXML
	private Slider efSlider;
	@FXML
	private CheckBox animChek;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////// START

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			iniciar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showLoading(boolean show) {
		loadingOverlay.setVisible(show);
		if (show) {
			loadingOverlay.toFront();
		} else {
			loadingOverlay.toBack();
		}
	}

	private void iniciar() {
		try {
			floor.toBack();
			StyleAndEffectService.pointElement(bienvenidaLabel, 0.1, 0.9, "rgb(239,184,16)", colorTexto);
			StyleAndEffectService.parpadeoConstante(bienvenidaLabel);
			bienvenidaLabel.setVisible(true);
			bienvenidaPanel.setVisible(true);
			defaultImage = randomImagePick(); // correcto
			fastSearchImg.setImage(new Image(getClass().getResource("/com/ors/images/lupa.png").toExternalForm()));
			goToPosition.setImage(new Image(getClass().getResource("/com/ors/images/lupa.png").toExternalForm()));
			panelAjustes.setLayoutX(920);
			panelAjustes.setLayoutY(16);
			panelAjustes.setPrefSize(40, 40);
			panelAjustes.toFront();
			setDropInImagePane();
			bienvenidaLabel.setOnMouseClicked(event -> {
				try {
					ser.efectoEntrar(volumenEfectos);
					animChek.setSelected(
							Boolean.parseBoolean(GestorFicheroConfiguracion.devolverCredencial("animaciones")));
					animaciones = animChek.isSelected();
					efSlider.setValue(Double.parseDouble(GestorFicheroConfiguracion.devolverCredencial("volEfectos")));
					muSlider.setValue(Double.parseDouble(GestorFicheroConfiguracion.devolverCredencial("volMusica")));
					bienvenidaLabel.setVisible(false);
					bienvenidaPanel.setVisible(false);
					panelAjustes.setVisible(true);
					ajustesPanelEsconder();
					ser.efectoClick(volumenEfectos);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			comunicaError("FAllo en el iniciar: " + e.getMessage());
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Refresco

	private void updateCharacterView() throws Exception {
		defaultViewLobby(true);
		imagenDePerfilPosible = defaultImage;
		if (pjs.size() == 0) {
			setInitialCharacter();
		} else {
			ComunAlmacen.selected = pjs.get(posicion);
			comunicaError(ComunAlmacen.selected.showInfo());
			refrescoDeEstilo();
		}
	}

	private void refrescoDeEstilo() throws SQLException, Exception {

		eraseBtn.setVisible(dm);
		unableBtn.setVisible(dm);
		moveBtn.setVisible(dm);

		StyleAndEffectService.fadeIn(totalPanel, 1f);

		///////////////////////// VIAJE RÁPIDO LISTA

//		personajesSel.getItems().clear();
//		for (PJ pj : pjs) {
//			personajesSel.getItems().add(pj.getName());
//		}

		///////////////////////// IMAGENES CARROUSEL

		int leftIndex = (posicion - 1 + pjs.size()) % pjs.size();
		int rightIndex = (posicion + 1) % pjs.size();

		setImagenesDePosicionesYFotito(rightImage, leftIndex, false);
		setImagenesDePosicionesYFotito(centralImage, posicion, true);
		setImagenesDePosicionesYFotito(leftImage, rightIndex, false);

		///////////////////////// ACCIONES SLIDE

		rightImage.setOnMouseClicked(event -> {
			try {
				rightSlide();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		leftImage.setOnMouseClicked(event -> {
			try {
				leftSlide();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		///////////////////////// PONER FOTO FONDO

		totalPanel.getChildren().remove(backImage);
		File[] fotos = ser.checkFotosCarpeta();
		File elegida = fotos[dado.nextInt(0, fotos.length)];
		String ruta = "/com/ors/images/Fondos/" + elegida.getName();

		backImage = new ImageView(new Image(getClass().getResource(ruta).toExternalForm()));
		backImage.setFitWidth(totalPanel.getWidth() + 10);
		backImage.setFitHeight(totalPanel.getHeight() + 10);
		GaussianBlur blur = new GaussianBlur(25);
		backImage.setEffect(blur);

		if (!totalPanel.getChildren().contains(backImage)) {
			totalPanel.getChildren().add(0, backImage);
		}

		/////////////////////// DETALLES DEL PANEL E INFO

		setStyles(ComunAlmacen.selected);

	}

	public void setStyles(PJ pj) throws Exception {

		nameButton.setText(pjs.get(posicion).getName());
		ajusteDeBarras(altSlider, atlBar, pjs.get(posicion).getAtl());
		ajusteDeBarras(strSlider, strBar, pjs.get(posicion).getStr());
		ajusteDeBarras(endSlider, endBar, pjs.get(posicion).getEnd());
		ajusteDeBarras(minSlider, minBar, pjs.get(posicion).getMin());
		ajusteDeBarras(dexSlider, dexBar, pjs.get(posicion).getDex());
		if (!pj.isAble()) {
			characterPreviewPanel.setStyle("-fx-background-color: red;");
		} else {
			characterPreviewPanel.setStyle("-fx-background-color: white;");
		}
		switch (pj.getRace().getName()) {
		case "SOLEO":
			colorR = "rgb(180, 60, 40)";
			break;
		case "TECHITA":
			colorR = "rgb(100, 60, 200)";
			break;
		case "HUMANO":
			colorR = "rgb(50, 90, 200)";
			break;
		default:
			colorR = "rgb(40, 140, 190)";
			break;
		}
		GestorFicheroConfiguracion.actualizarValor("colorR", colorR);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////// ESENCIALES

	// Llama a updateCharacterView al final
	@FXML
	public void ajustesPanelEsconder() throws Exception {
		ComunAlmacen.mediaPlayer.setVolume(volumenFondo);
		panelAjustes.setDisable(false);
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.millis(300), new KeyValue(panelAjustes.layoutXProperty(), 920)) // o 798 si lo
																										// quieres
																										// desplazado
		);
		timeline.play();
		botonAjustes.setVisible(false);
		volMuLabel.setVisible(false);
		muSlider.setVisible(false);
		volEfLabel.setVisible(false);
		efSlider.setVisible(false);
		animChek.setVisible(false);
		GestorFicheroConfiguracion.actualizarValor("animaciones", animChek.isSelected() + "");
		GestorFicheroConfiguracion.actualizarValor("volEfectos", efSlider.getValue() + "");
		GestorFicheroConfiguracion.actualizarValor("volMusica", muSlider.getValue() + "");
		animaciones = animChek.isSelected();
		volumenFondo = muSlider.getValue();
		volumenEfectos = efSlider.getValue();

		////////////////////////// RECARGA DE VIAJE RÁPIDO NOMBRES

		personajesSel.getItems().clear();
		pjs = PjService.getCompletePJs(adventure, dm);
		for (PJ pj : pjs) {
			personajesSel.getItems().add(pj.getName());
		}
		personajesSel.setVisible(false);

		updateCharacterView();
	}

	// LLama a ajustesPanelEsconder al final
	private void setInitialCharacter() throws Exception {
		StyleAndEffectService.fadeIn(totalPanel, posicion);
		setOptions();
		ajusteDeBarras(altSlider, atlBar, 1);
		ajusteDeBarras(strSlider, strBar, 1);
		ajusteDeBarras(endSlider, endBar, 1);
		ajusteDeBarras(minSlider, minBar, 1);
		ajusteDeBarras(dexSlider, dexBar, 1);

		newNameText.clear();
		newNameText.setPromptText("NUEVO PERSONAJE");
		glimmersText.setPromptText("0");
		raceOptions.setValue("HUMANO");
		powerOptions.setValue("REGULAR");
		btOptions.setValue("STRONG");
		adjustTexts();
		defaultViewLobby(false);
		listoBtn.setOnAction(event -> {
			try {
				boolean puede = true;
				PJ tempo = new PJ(newNameText.getText());
				tempo.setAdventureName(adventure.getAdventureName());
				List<PJ> todos = PjService.getAll();
				if (todos.contains(tempo)) {
					puede = false;
				}
				if (puede) {
					tempo.setCharacterType((!dm) ? CharacterTypes.PARTY : CharacterTypes.NPC); // Meter mejor un botón
					ser.efectoPasar(volumenEfectos);
					System.out.println("ruta imagen" + imagenDePerfilPosible.getAbsolutePath());
					tempo.setProfile(ImagenesUtil.fileToByte(imagenDePerfilPosible)); // a no ser que la cambien, es la
																						// default
					tempo.setAtl((int) altSlider.getValue());
					tempo.setStr((int) strSlider.getValue());
					tempo.setEnd((int) endSlider.getValue());
					tempo.setMin((int) minSlider.getValue());
					tempo.setDex((int) dexSlider.getValue());
					tempo.setRace((Race) RaceService.getById((raceOptions.getValue().toString())));
					tempo.setGlimmers(
							glimmersText.getText().isEmpty() ? 0.0 : Double.parseDouble(glimmersText.getText()));
					tempo.setPower(powerOptions.getValue().toString());
					tempo.setAble(true);
					PjService.create(tempo);
					ComunAlmacen.pU.agregarBT(tempo, btOptions.getValue().toString());
					tempo.setActions(tempo.getMaxActions());
					tempo.setHp(tempo.getMaxHp());
					tempo.setKcal(tempo.getMaxKcal());
					PjService.update(tempo);
					pjs.add(tempo);
					ser.efectoEntrar(volumenEfectos);
					panelAjustes.setVisible(true);
					ajustesPanelEsconder();
				} else {
					ser.efectoClick(volumenEfectos);
					newNameText.setPromptText("Nombre existente");
					newNameText.setStyle("-fx-text-fill: red;");
					newNameText.requestFocus();
				}
			} catch (Exception e) {
				e.printStackTrace();
				iniciar();
			}
		});
	}

	// Guarda el personaje y llama a ajustesPanelEsconder al final
	private void enviar() throws Exception {
		ser.efectoClick(volumenEfectos);
		pjs.get(posicion).setAtl((int) altSlider.getValue());
		pjs.get(posicion).setStr((int) strSlider.getValue());
		pjs.get(posicion).setEnd((int) endSlider.getValue());
		pjs.get(posicion).setMin((int) minSlider.getValue());
		pjs.get(posicion).setDex((int) dexSlider.getValue());
		pjs.get(posicion).setAble(true);
		pjs.get(posicion).setName(newNameText.getText());
		pjs.get(posicion).setRace((Race) RaceService.getById(raceOptions.getValue().toString()));
		pjs.get(posicion).setGlimmers(Double.parseDouble(glimmersText.getText()));
		pjs.get(posicion).setAdventureName(adventure.getAdventureName());
		pjs.get(posicion).setPower(powerOptions.getValue().toString());
		ComunAlmacen.pU.agregarBT(pjs.get(posicion), btOptions.getValue().toString());
		PjService.update(pjs.get(posicion));
		ajustesPanelEsconder(); // vuelvo a cargar los personajes
	}

	@FXML
	private void edit() throws Exception {
		ser.efectoClick(volumenEfectos);
		defaultViewLobby(false);
		setOptions();
		if (dm) {
			listoBtn.setOnAction(event -> {
				try {
					if (imagenDePerfilPosible != defaultImage) {
						pjs.get(posicion).setProfile(ImagenesUtil.fileToByte(imagenDePerfilPosible));
					}
					enviar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			powerOptions.setValue(pjs.get(posicion).getPower().toString());
			newNameText.setText(pjs.get(posicion).getName());
			glimmersText.setText(pjs.get(posicion).getGlimmers() + "");
			raceOptions.setValue(pjs.get(posicion).getRace().getName());
			btOptions.setValue(pjs.get(posicion).getBodyTypes().iterator().next().getName());
		} else {
			setInitialCharacter();
		}
		adjustTexts();
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////// Multimedia

	/////////////////////////////////////////////////////////////// EDICION DE
	/////////////////////////////////////////////////////////////// IMAGEN DE PJ
	@FXML
	private void guardarImagen() throws Exception {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
		imagenDePerfilPosible = fileChooser.showOpenDialog(null);
		checkTamanoFoto();
	}

	private void setDropInImagePane() {
		selectImagePanel.setOnDragOver(event -> {
			if (event.getDragboard().hasFiles()) {
				dropLabel.setText("DROP");
				event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			}
			event.consume();
		});

		selectImagePanel.setOnDragDropped(event -> {
			List<File> archivos = event.getDragboard().getFiles();
			if (!archivos.isEmpty()) {
				imagenDePerfilPosible = archivos.get(0);
				checkTamanoFoto();
			}
			event.setDropCompleted(true);
			event.consume();
		});
	}

	// Si la foto es muy grande, pone la default, sino, no hace nada.
	private void checkTamanoFoto() {
		if (imagenDePerfilPosible != null) {
			if (imagenDePerfilPosible.length() > 2147483647) {
				dropLabel.setText("TOO HEAVY");
				imagenDePerfilPosible = defaultImage;
			} else {
				dropLabel.setText("SAVED");
			}
		}
	}

	////////////////////////////////// REFRESCO POR CADA SLIDE Y CARGA

	private void setImagenesDePosicionesYFotito(ImageView i, int index, boolean central) throws Exception {
		if (pjs.get(index).getProfile() != null) {
			if (central) {
				refrescarImagenActual();
			} else {
				i.setImage(ImagenesUtil.byteArrayToImage(pjs.get(index).getProfile()));
			}
		} else {
			pjs.get(index).setProfile(ImagenesUtil.fileToByte((defaultImage)));
			setImagenesDePosicionesYFotito(i, index, central);
		}
	}

	private void refrescarImagenActual() {
		Image nueva = ImagenesUtil.byteArrayToImage(pjs.get(posicion).getProfile());
		if (nueva != null && nueva.getWidth() > 0) { //
			fotito.setImage(nueva);
			fotito.toFront();
			centralImage.setImage(nueva);
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////// Modularizacion

	private void ajusteDeBarras(Slider a, ProgressBar o, int i) {
		a.setMin(1);
		a.setMax(60);
		a.setValue(i);
		o.setProgress(i / 60.0);
		o.setStyle("-fx-accent:" + colorR + ";");
	}

	private void anim(int i) {
		StyleAndEffectService.entradaEpica(fotito, 600 * i);
		StyleAndEffectService.entradaEpica(nameButton, 600 * i);
		StyleAndEffectService.entradaEpica(characterPreviewPanel, 600 * i);
		StyleAndEffectService.entradaEpica(centralImage, 100 * i);
		StyleAndEffectService.entradaEpica(rightImage, -200 * i);
		StyleAndEffectService.entradaEpica(leftImage, 100 * i);
		StyleAndEffectService.entradaEpica(editBtn, 600 * i);
		StyleAndEffectService.entradaEpica(enterBtn, 600 * i);
		StyleAndEffectService.entradaEpica(fastSearchImg, 600 * i);
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////// Utilidades

	private void comunicaError(String string) {
		Alert a = new Alert(Alert.AlertType.INFORMATION);
		a.setHeaderText(string);
		a.show();
	}

	private File randomImagePick() {
		String prefijo = "src/main/resources/com/ors/images/profiles/";
		File carpetita = new File(prefijo);
		File[] todas = carpetita.listFiles();
		return new File(prefijo + todas[dado.nextInt(0, todas.length)].getName());
	}

	synchronized void adjustTexts() {
		Platform.runLater(() -> {
			altNum.setText((int) altSlider.getValue() + "");
			strNum.setText((int) strSlider.getValue() + "");
			endNum.setText((int) endSlider.getValue() + "");
			minNum.setText((int) minSlider.getValue() + "");
			dexNum.setText((int) dexSlider.getValue() + "");
		});
	}

	private void setOptions() throws Exception {
		newNameText.setStyle("-fx-text-fill: black;");
		btOptions.getItems().clear();
		List<BodyType> bts = BodyTypeService.getAll();
		for (BodyType b : bts) {
			b.fillMods();
		}
		btOptions.getItems().addAll(bts.stream().map(obj -> ((BodyType) obj).getName()).toList());

		raceOptions.getItems().clear();
		raceOptions.getItems().addAll(RaceService.getAll().stream().map(obj -> ((Race) obj).getName()).toList());
		String[] talentos = { "REGULAR", "JANO", "BUNRAKU", "LEIRZA" };
		powerOptions.getItems().clear();
		powerOptions.getItems().addAll(talentos);
	}

	private void defaultViewLobby(boolean estado) throws Exception { // Esto es una gilipollez pero bueno, asi se queda

		StyleAndEffectService.setAllStyles(absolutePane, tama, brillo, colorR, colorTexto, animaciones, true);

		goToPosition.setVisible(false);
		fastSearchImg.setVisible(true);
		dropLabel.setText("NUEVA IMAGEN");

		characterPreviewPanel.setVisible(true);
		mapView.setVisible(estado);
		mapView.setDisable(!estado);
		leftSlideBtn.setVisible(true);
		rightSlideBtn.setVisible(true);

		nameButton.setVisible(estado);
		nameButton.setDisable(!estado);
		editBtn.setVisible(estado);
		editBtn.setDisable(!estado);
		createBtn.setVisible(estado);
		createBtn.setDisable(!estado);
		enterBtn.setVisible(estado);
		enterBtn.setDisable(!estado);

		fotito.setVisible(estado);

		atlBar.setVisible(estado);
		strBar.setVisible(estado);
		endBar.setVisible(estado);
		minBar.setVisible(estado);
		dexBar.setVisible(estado);

		atlBar.setDisable(!estado);
		strBar.setDisable(!estado);
		endBar.setDisable(!estado);
		minBar.setDisable(!estado);
		dexBar.setDisable(!estado);

		altNum.setVisible(!estado);
		strNum.setVisible(!estado);
		endNum.setVisible(!estado);
		minNum.setVisible(!estado);
		dexNum.setVisible(!estado);

		listoBtn.setVisible(!estado);

		altSlider.setVisible(!estado);
		strSlider.setVisible(!estado);
		endSlider.setVisible(!estado);
		minSlider.setVisible(!estado);
		dexSlider.setVisible(!estado);

		selectImagePanel.setVisible(!estado);
		selectImagePanel.setDisable(estado);

		btLabel.setVisible(!estado);
		btLabel.setDisable(estado);
		btOptions.setVisible(!estado);
		btOptions.setDisable(estado);
		raceOptions.setVisible(!estado);
		raceOptions.setDisable(estado);
		powerOptions.setVisible(!estado);
		powerOptions.setDisable(estado);
		powerLabel.setVisible(!estado);
		powerLabel.setDisable(estado);
		glimmersLabel.setVisible(!estado);
		glimmersLabel.setDisable(estado);
		glimmersText.setVisible(!estado);
		glimmersText.setDisable(estado);
		raceLabel.setVisible(!estado);
		raceLabel.setDisable(estado);

		newNamePane.setVisible(!estado);
		newNamePane.setDisable(estado);
		newNameText.setVisible(!estado);
		newNameText.setDisable(estado);

	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FXML

	@FXML
	private void create() throws Exception {
		setOptions(); // rellena los comboBox con las opciones de la bbdd
		ser.efectoClick(volumenEfectos);
		defaultViewLobby(false);
		setInitialCharacter(); // Crea un personaje desde 0
		adjustTexts();
	}

	@FXML
	private void setTexts() { // Cuando entro el slider inicia este
		mirar = true;
		Runnable mirarndoR = () -> {
			while (mirar) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				adjustTexts(); // lo tenemos que hacer en el hilo de la interfaz
			}
		};
		Thread mirandoTexto = new Thread(mirarndoR);
		mirandoTexto.start();
	}

	@FXML
	private void dejarDeMirar() { // Cuando suelto el slider ocurre esto
		mirar = false;
		adjustTexts();
	}

	@FXML
	private void enter() throws Exception {
		comunicaError(ComunAlmacen.selected.showInfo());
		showLoading(true); // para la pestaña de carga
		new Thread(() -> { // CAMBIAR!! poner mejor en el initialice de characterController todo, aqui
							// sobra
			try {
				backF = backImage;
				pjs.get(posicion).setAble(false);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ors/views/CharacterVista.fxml"));
				Parent root = loader.load();
				Scene ventana = new Scene(root);
				ventana.getStylesheets().add(
						getClass().getResource(GestorFicheroConfiguracion.devolverCredencial("css")).toExternalForm());

				Platform.runLater(() -> { // Esto como ya toca contenido se hace en runLater
					try {
						sesion.setTitle(pjs.get(posicion).getName());
						sesion.setScene(ventana);
						sesion.sizeToScene();
						sesion.setResizable(false);
						sesion.show();
						showLoading(false); // Ocultar loading aquí
						ser.efectoPaginas(volumenEfectos);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	@FXML
	private void rightSlide() throws Exception {
		posicion = (posicion + 1) % pjs.size();
		if (posicion < 0)
			posicion += pjs.size();
		if (animaciones) {
			anim(1);
		}
		ser.efectoPasar(volumenEfectos);
		updateCharacterView();
	}

	@FXML
	private void leftSlide() throws Exception {
		posicion = (posicion - 1) % pjs.size();
		if (posicion < 0)
			posicion += pjs.size();
		if (animaciones) {
			anim(-1);
		}
		ser.efectoPasar(volumenEfectos);
		updateCharacterView();
	}

	@FXML
	public void fastSearch() {
		ser.efectoClick(volumenEfectos);
		personajesSel.setVisible(true);
		fastSearchImg.setVisible(false);
		goToPosition.setVisible(true);
	}

	@FXML
	public void gotoPositionGo() throws Exception {
		ser.efectoClick(volumenEfectos);
		fastSearchImg.setVisible(true);
		goToPosition.setVisible(false);
		posicion = personajesSel.getSelectionModel().getSelectedIndex();
		personajesSel.setVisible(false);
		updateCharacterView();
	}

	@FXML
	private void erase() throws Exception {
		PjService.delete(pjs.get(posicion));
		pjs.remove(posicion);
		updateCharacterView();
	}

	@FXML
	private void move() throws Exception {
		ser.efectoClick(volumenEfectos);

		List<Adventure> aventurasDisponibles = AdventureService.getAll();
		if (aventurasDisponibles.isEmpty())
			return;

		ComboBox<Adventure> comboAventuras = new ComboBox<>();
		comboAventuras.getItems().addAll(aventurasDisponibles);
		comboAventuras.setValue(aventurasDisponibles.get(0));
		ComboBox<CharacterTypes> comboTipo = new ComboBox<>();
		comboTipo.getItems().addAll(CharacterTypes.values());
		comboTipo.setValue(CharacterTypes.NPC);

		Pane content = new Pane();
		Label aventuraLabel = new Label("Aventura:");
		Label tipoLabel = new Label("Tipo:");
		aventuraLabel.setLayoutY(10);
		comboAventuras.setLayoutY(30);
		tipoLabel.setLayoutY(70);
		comboTipo.setLayoutY(90);
		content.getChildren().addAll(aventuraLabel, comboAventuras, tipoLabel, comboTipo);

		Dialog<Void> dialog = new Dialog<>();
		dialog.setTitle("Mover personaje");
		dialog.getDialogPane().setContent(content);
		dialog.getDialogPane().getButtonTypes().addAll(new ButtonType("Mover", ButtonBar.ButtonData.OK_DONE),
				ButtonType.CANCEL);

		dialog.setResultConverter(button -> {
			if (button.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
				try {
					PJ pj = pjs.get(posicion);
					pj.setAdventureName(comboAventuras.getValue().getAdventureName());
					pj.setCharacterType(comboTipo.getValue());
					PjService.update(pj);
					pjs = PjService.getCompletePJs(adventure, dm);
					posicion = 0;
					updateCharacterView();
					ser.efectoEleccion(volumenEfectos);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		});
		dialog.showAndWait();
	}

	@FXML
	private void unable() throws Exception {
		if (pjs.get(posicion).isAble()) {
			pjs.get(posicion).setAble(false);
		} else {
			pjs.get(posicion).setAble(true);

		}
		updateCharacterView();
	}

	@FXML
	public void ajustesPanelDesplegar() throws Exception {
		// inicial:
		// x =920
		// y =16
		// bounds, 40x40
		//
		// Objetivo:
		// x =798

		panelAjustes.setDisable(false);
		panelAjustes.toFront();

//		TranslateTransition agrandar = new TranslateTransition(Duration.millis(300), panelAjustes);
//		agrandar.setFromX(0);
//		agrandar.setToX(-122); // 798 - 920
//		agrandar.playFromStart();

		Timeline timeline = new Timeline(
				new KeyFrame(Duration.millis(300), new KeyValue(panelAjustes.layoutXProperty(), 798)));
		timeline.play();

		animChek.setSelected(animaciones);
		efSlider.setMin(0);
		efSlider.setMax(1);
		efSlider.setValue(volumenEfectos);
		muSlider.setMin(0);
		muSlider.setMax(1);
		muSlider.setValue(volumenFondo);

		botonAjustes.setVisible(true);
		volMuLabel.setVisible(true);
		muSlider.setVisible(true);
		volEfLabel.setVisible(true);
		efSlider.setVisible(true);
		animChek.setVisible(true);
	}

}