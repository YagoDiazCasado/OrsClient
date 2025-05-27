package main.java.com.ors.controllers;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

	public static String defaultImage;
	private List<PJ> pjs = new ArrayList<PJ>();
	private int posicion = 0;
	private boolean mirar;
	public File imagenDePerfilPosible;
	public static ImageView backF;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		defaultImage = randomImagePick();
		fastSearchImg.setImage(new Image(getClass().getResource("/com/ors/images/lupa.png").toExternalForm()));
		goToPosition.setImage(new Image(getClass().getResource("/com/ors/images/lupa.png").toExternalForm()));
		try {
			System.out.println(adventure);
			pjs = PjService.getCompletePJs(adventure, dm);
			setDropInImagePane();
			floor.toBack();
			fotito.toFront();
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

	private void setInitialCharacter() throws Exception {
		StyleAndEffectService.fadeIn(totalPanel, posicion);
		setOptions();
		altSlider.setMin(1);
		altSlider.setMax(60);
		altSlider.setValue(1);
		strSlider.setMin(1);
		strSlider.setMax(60);
		strSlider.setValue(1);
		endSlider.setMin(1);
		endSlider.setMax(60);
		endSlider.setValue(1);
		minSlider.setMin(1);
		minSlider.setMax(60);
		minSlider.setValue(1);
		dexSlider.setMin(1);
		dexSlider.setMax(60);
		dexSlider.setValue(1);
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
					ser.efectoPasar(volumenEfectos);
					tempo.setProfile((imagenDePerfilPosible != null)
							? ImagenesUtil.convertImageToBytes(new Image(imagenDePerfilPosible.toURI().toString()))
							: ImagenesUtil.convertImageToBytes(new Image(new File(defaultImage).toURI().toString())));
					tempo.setCharacterType((!dm) ? CharacterTypes.PARTY : CharacterTypes.NPC); // Meter mejor un botón
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
					panelAjustes.setVisible(true);
					panelAjustes.setDisable(false);
					panelAjustes.toFront();
					panelAjustes.setLayoutX(920);
					panelAjustes.setLayoutY(16);
					panelAjustes.setPrefSize(40, 40);
					if (pjs.size() < 1) {
						iniciar();
					} else {
						ser.efectoEntrar(volumenEfectos);
						bienvenidaLabel.setVisible(false);
						bienvenidaLabel.setDisable(true);
						bienvenidaPanel.setVisible(false);
						bienvenidaPanel.setDisable(true);
						panelAjustes.setVisible(true);
						panelAjustes.setDisable(false);
						panelAjustes.toFront();
						panelAjustes.setLayoutX(920);
						panelAjustes.setLayoutY(16);
						panelAjustes.setPrefSize(40, 40);
						ajustesPanelEsconder();
						ser.efectoClick(volumenEfectos);
					}
				} else {
					setInitialCharacter();
					newNameText.requestFocus();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private void iniciar() throws Exception {
		StyleAndEffectService.pointElement(bienvenidaLabel, 0.1, 0.9, "rgb(239,184,16)", colorTexto);
		bienvenidaLabel.setVisible(true);
		bienvenidaLabel.setDisable(false);
		bienvenidaPanel.setVisible(true);
		bienvenidaPanel.setDisable(false);
		StyleAndEffectService.parpadeoConstante(bienvenidaLabel);
		bienvenidaLabel.setOnMouseClicked(event -> {
			try {
				ser.efectoEntrar(volumenEfectos);
				animChek.setSelected(
						Boolean.parseBoolean(GestorFicheroConfiguracion.devolverCredencial("animaciones")));
				animaciones = animChek.isSelected();
				efSlider.setValue(Double.parseDouble(GestorFicheroConfiguracion.devolverCredencial("volEfectos")));
				muSlider.setValue(Double.parseDouble(GestorFicheroConfiguracion.devolverCredencial("volMusica")));
				bienvenidaLabel.setVisible(false);
				bienvenidaLabel.setDisable(true);
				bienvenidaPanel.setVisible(false);
				bienvenidaPanel.setDisable(true);
				panelAjustes.setVisible(true);
				panelAjustes.setDisable(false);
				panelAjustes.toFront();
				panelAjustes.setLayoutX(920);
				panelAjustes.setLayoutY(16);
				panelAjustes.setPrefSize(40, 40);
				ajustesPanelEsconder();
				ser.efectoClick(volumenEfectos);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private void updateCharacterView() throws Exception {
		if (pjs.size() == 0) {
			setInitialCharacter();
		} else {
			personajesSel.getItems().clear();
			for (PJ pj : pjs) {
				personajesSel.getItems().add(pj.getName());
			}
			ComunAlmacen.selected = pjs.get(posicion);
			updateMapView(); // la vista de arriba
			setStyles(ComunAlmacen.selected);
			defaultViewLobby(true); // Desactivar el modo edit
			StyleAndEffectService.fadeIn(totalPanel, 1f);
		}
	}

	private void updateMapView() throws SQLException, Exception {

		int leftIndex = (posicion - 1 + pjs.size()) % pjs.size();
		int rightIndex = (posicion + 1) % pjs.size();

		if (pjs.get(leftIndex).getProfile() != null) {
			leftImage.setImage(ImagenesUtil.byteArrayToImage(pjs.get(leftIndex).getProfile()));
		} else {
			leftImage.setImage(new Image(getClass().getResource(defaultImage).toExternalForm()));
		}

		if (pjs.get(posicion).getProfile() != null) {
			centralImage.setImage(ImagenesUtil.byteArrayToImage(pjs.get(posicion).getProfile()));
		} else {
			centralImage.setImage(new Image(getClass().getResource(defaultImage).toExternalForm()));
		}

		if (pjs.get(rightIndex).getProfile() != null) {
			rightImage.setImage(ImagenesUtil.byteArrayToImage(pjs.get(rightIndex).getProfile()));
		} else {
			rightImage.setImage(new Image(getClass().getResource(defaultImage).toExternalForm()));
		}

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

	}

	public void setStyles(PJ pj) throws Exception {

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

		altSlider.setMin(1);
		altSlider.setMax(60);
		altSlider.setValue(pj.getAtl());
		strSlider.setMin(1);
		strSlider.setMax(60);
		strSlider.setValue(pj.getStr());
		endSlider.setMin(1);
		endSlider.setMax(60);
		endSlider.setValue(pj.getEnd());
		minSlider.setMin(1);
		minSlider.setMax(60);
		minSlider.setValue(pj.getMin());
		dexSlider.setMin(1);
		dexSlider.setMax(60);
		dexSlider.setValue(pj.getDex());

		nameButton.setText(pjs.get(posicion).getName());

		atlBar.setProgress(pjs.get(posicion).getAtl() / 60.0);
		strBar.setProgress(pjs.get(posicion).getStr() / 60.0);
		endBar.setProgress(pjs.get(posicion).getEnd() / 60.0);
		minBar.setProgress(pjs.get(posicion).getMin() / 60.0);
		dexBar.setProgress(pjs.get(posicion).getDex() / 60.0);

		atlBar.setStyle("-fx-accent:" + colorR + ";");
		strBar.setStyle("-fx-accent:" + colorR + ";");
		endBar.setStyle("-fx-accent:" + colorR + ";");
		minBar.setStyle("-fx-accent:" + colorR + ";");
		dexBar.setStyle("-fx-accent:" + colorR + ";");

		totalPanel.getChildren().remove(backImage);
		if (!pj.isAble()) {
			characterPreviewPanel.setStyle("-fx-background-color: red;");
		} else {
			characterPreviewPanel.setStyle("-fx-background-color: white;");
		}

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

		Image foto = ImagenesUtil.byteArrayToImage(pj.getProfile());
		if (foto != null && foto.getWidth() != 0) {
			fotito.setImage(foto);
			pj.setProfile(ImagenesUtil.convertImageToBytes(foto));
		} else {
			fotito.setImage(new Image(getClass().getResource(defaultImage).toExternalForm())); // Imagen por defecto
		}

	}

	private String randomImagePick() {
		File carpetita = new File("src/main/resources/com/ors/images/profiles");
		File[] todas = carpetita.listFiles();
		return "/com/ors/images/profiles/" + todas[dado.nextInt(0, todas.length)].getName();
	}

	private void enviar() throws Exception {
		ser.efectoClick(volumenEfectos);
		aplicarCambios();
		ajustesPanelEsconder(); // vuelvo a cargar los personajes
	}

	private void aplicarCambios() throws Exception {
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
	}

	@FXML
	private void edit() throws Exception {
		setOptions(); // rellena los comboBox con las opciones de la bbdd
		ser.efectoClick(volumenEfectos);
		defaultViewLobby(false);
		if (dm) {
			listoBtn.setOnAction(event -> {
				try {
					if (imagenDePerfilPosible != null) {
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
			setInitialCharacter(); // Crea un personaje desde 0
		}
		adjustTexts();
	}

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

	synchronized void adjustTexts() {
		Platform.runLater(() -> { // Esto es porque en fx los hilos no deberian tocar cosas de la interfaz
			// Hay que hacer un runLater(), lo cual ejecute nuestros hilos en orden para que
			// la interfaz ocurra siempre a su ritmo
			altNum.setText((int) altSlider.getValue() + "");
			strNum.setText((int) strSlider.getValue() + "");
			endNum.setText((int) endSlider.getValue() + "");
			minNum.setText((int) minSlider.getValue() + "");
			dexNum.setText((int) dexSlider.getValue() + "");
		});
	}

	@FXML
	private void dejarDeMirar() { // Cuando suelto el slider ocurre esto
		mirar = false;
		adjustTexts();
	}

	@FXML
	private void enter() throws Exception {
		showLoading(true); // para la pestaña de carga
		new Thread(() -> { // CAMBIAR!! poner mejor en el initialice de characterController todo, aqui
							// sobra
			try {
				selected = pjs.get(posicion);
				ComunAlmacen.selected = selected;
				System.out.println(selected.getName() + " acaba de entrar. El POj completo");
				backF = backImage;
				ComunAlmacen.colorR = colorR;
				pjs.get(posicion).setAble(false);

				// Cargar FXML y escena SÍ se puede hacer fuera del FX thread
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
			StyleAndEffectService.entradaEpica(fotito, 600);
			StyleAndEffectService.entradaEpica(nameButton, 600);
			StyleAndEffectService.entradaEpica(characterPreviewPanel, 600);
			StyleAndEffectService.entradaEpica(centralImage, 100);
			StyleAndEffectService.entradaEpica(rightImage, -200);
			StyleAndEffectService.entradaEpica(leftImage, 100);
			StyleAndEffectService.entradaEpica(editBtn, 600);
			StyleAndEffectService.entradaEpica(enterBtn, 600);
			StyleAndEffectService.entradaEpica(fastSearchImg, 600);
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
			StyleAndEffectService.entradaEpica(fotito, -600);
			StyleAndEffectService.entradaEpica(nameButton, -600);
			StyleAndEffectService.entradaEpica(characterPreviewPanel, -600);
			StyleAndEffectService.entradaEpica(centralImage, -100);
			StyleAndEffectService.entradaEpica(leftImage, 200);
			StyleAndEffectService.entradaEpica(rightImage, -100);
			StyleAndEffectService.entradaEpica(editBtn, -600);
			StyleAndEffectService.entradaEpica(enterBtn, -600);
			StyleAndEffectService.entradaEpica(fastSearchImg, -600);
		}
		ser.efectoPasar(volumenEfectos);
		updateCharacterView();
	}

	@FXML
	public void fastSearch() {
		ser.efectoClick(volumenEfectos);
		personajesSel.setVisible(true);
		personajesSel.setDisable(false);
		fastSearchImg.setVisible(false);
		fastSearchImg.setDisable(true);
		goToPosition.setVisible(true);
		goToPosition.setDisable(false);
	}

	@FXML
	public void gotoPositionGo() throws Exception {
		ser.efectoClick(volumenEfectos);
		fastSearchImg.setVisible(true);
		fastSearchImg.setDisable(false);
		goToPosition.setVisible(false);
		goToPosition.setDisable(true);

		try {
			posicion = personajesSel.getSelectionModel().getSelectedIndex();
			personajesSel.setVisible(false);
			personajesSel.setDisable(true);
			updateCharacterView();
		} catch (Exception e) {
			System.out.println("No esta en la lsita");
		}
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
				if (imagenDePerfilPosible.length() > 2147483647) {
					dropLabel.setText("TOO HEAVY");
					imagenDePerfilPosible = new File(defaultImage);
				} else {
					dropLabel.setText("SAVED");
				}
			}
			event.setDropCompleted(true);
			event.consume();
		});
	}

	@FXML
	private void guardarImagen() throws Exception {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
		imagenDePerfilPosible = fileChooser.showOpenDialog(null);
		if (imagenDePerfilPosible != null) {
			if (imagenDePerfilPosible.length() > 2147483647) {
				dropLabel.setText("TOO HEAVY");
				imagenDePerfilPosible = new File(defaultImage);
			} else {
				dropLabel.setText("SAVED");
			}
		}
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

	private void defaultViewLobby(boolean estado) throws Exception { // Esto es una gilipollez pero bueno, asi se queda

		StyleAndEffectService.setAllStyles(absolutePane, tama, brillo, colorR, colorTexto, animaciones, true);

		goToPosition.setVisible(false);
		goToPosition.setDisable(true);
		fastSearchImg.setVisible(true);
		fastSearchImg.setDisable(false);
		dropLabel.setText("NUEVA IMAGEN");

		characterPreviewPanel.setVisible(true);
		characterPreviewPanel.setDisable(false);
		mapView.setVisible(estado);
		mapView.setDisable(!estado);
		leftSlideBtn.setVisible(true);
		leftSlideBtn.setDisable(false);
		rightSlideBtn.setVisible(true);
		rightSlideBtn.setDisable(false);

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

	@FXML
	public void ajustesPanelDesplegar() throws Exception {
		// inicial:
		// x =920
		// y =16
		// bounds, 40x40
		//
		// Objetivo:
		// x =798

		TranslateTransition agrandar = new TranslateTransition(Duration.millis(300), panelAjustes);
		agrandar.setFromX(0);
		agrandar.setToX(-122); // 798 - 920
		agrandar.playFromStart();

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
		botonAjustes.setDisable(false);
		volMuLabel.setDisable(false);
		muSlider.setDisable(false);
		volEfLabel.setDisable(false);
		efSlider.setDisable(false);
		animChek.setDisable(false);
	}

	@FXML
	public void ajustesPanelEsconder() throws Exception {
		botonAjustes.setVisible(false);
		volMuLabel.setVisible(false);
		muSlider.setVisible(false);
		volEfLabel.setVisible(false);
		efSlider.setVisible(false);
		animChek.setVisible(false);
		botonAjustes.setDisable(true);
		volMuLabel.setDisable(true);
		muSlider.setDisable(true);
		volEfLabel.setDisable(true);
		efSlider.setDisable(true);
		animChek.setDisable(true);
		GestorFicheroConfiguracion.actualizarValor("animaciones", animChek.isSelected() + "");
		GestorFicheroConfiguracion.actualizarValor("volEfectos", efSlider.getValue() + "");
		GestorFicheroConfiguracion.actualizarValor("volMusica", muSlider.getValue() + "");
		animaciones = animChek.isSelected();
		volumenFondo = muSlider.getValue();
		volumenEfectos = efSlider.getValue();
		if (dm) {
			eraseBtn.setVisible(true);
			unableBtn.setVisible(true);
			moveBtn.setVisible(true);
			moveBtn.setDisable(false);
			eraseBtn.setDisable(false);
			unableBtn.setDisable(false);
		} else {
			eraseBtn.setVisible(false);
			unableBtn.setVisible(false);
			moveBtn.setVisible(false);
			moveBtn.setDisable(true);
			eraseBtn.setDisable(true);
			unableBtn.setDisable(true);
		}
		ComunAlmacen.mediaPlayer.setVolume(volumenFondo);
		personajesSel.getItems().clear();
		try {
			pjs = PjService.getCompletePJs(adventure, dm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (PJ pj : pjs) {
			personajesSel.getItems().add(pj.getName());
		}
		personajesSel.setVisible(false);
		personajesSel.setDisable(true);

		TranslateTransition mover = new TranslateTransition(Duration.millis(300), panelAjustes);
		mover.setFromX(-122);
		mover.setToX(0);
		mover.play();
		try {
			updateCharacterView();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("La primera creación ha fallado");
		}
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

}