package main.java.com.ors.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;

//Seguir adad

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.com.ors.network.Client;
import main.java.com.ors.services.BodyTypeService;
import main.java.com.ors.services.ComunAlmacen;
import main.java.com.ors.services.EquipmentService;
import main.java.com.ors.services.InventoryService;
import main.java.com.ors.services.ItemService;
import main.java.com.ors.services.PjService;
import main.java.com.ors.services.RaceService;
import main.java.com.ors.services.SkillService;
import main.java.com.ors.services.StyleAndEffectService;
import main.java.com.ors.usables.PjUsables;
import main.java.com.ors.utiles.EnumsDeItems.DamageType;
import main.java.com.ors.utiles.EnumsDeItems.Distance;
import main.java.com.ors.utiles.EnumsDeItems.ItemFamily;
import main.java.com.ors.utiles.EnumsDeItems.ItemShape;
import main.java.com.ors.utiles.EnumsDeItems.Rarity;
import main.java.com.ors.utiles.GestorFicheroConfiguracion;
import main.java.com.ors.utiles.Habilidad;
import main.java.com.ors.utiles.ImagenesUtil;
import main.java.com.ors.vo.BodyType;
import main.java.com.ors.vo.Equipment;
import main.java.com.ors.vo.Inventory;
import main.java.com.ors.vo.Item;
import main.java.com.ors.vo.PJ;
import main.java.com.ors.vo.Race;
import main.java.com.ors.vo.Skill;

public class CharacterController implements Initializable {

	// COMUNES

	public Stage sesion = ComunAlmacen.sesion;
	public StyleAndEffectService ser = ComunAlmacen.ser;
	public PjUsables pU = ComunAlmacen.pU;
	public Random dado = ComunAlmacen.dado;
	public String colorR = ComunAlmacen.colorR;
	public String colorTexto = ComunAlmacen.colorTexto;
	public Double volumenFondo = ComunAlmacen.volumenFondo;
	public Double volumenEfectos = ComunAlmacen.volumenEfectos;
	public boolean animaciones = ComunAlmacen.animaciones;
	public boolean dm = ComunAlmacen.dm;
	public double tama = ComunAlmacen.tama;
	public double brillo = ComunAlmacen.brillo;
	public DecimalFormat df = ComunAlmacen.df;
	public static PJ selected = ComunAlmacen.selected;
	public static String rutaMusica = ComunAlmacen.rutaMusica;

	// COSITIS NECESARIAS
	public static List<Pane> paneles = new ArrayList<Pane>();
	private Client cliente;
	public String defaultImage = LobbyController.defaultImage;
	public GaussianBlur blur = new GaussianBlur(25);

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FXML

	@FXML
	private Pane absolutePane;
	@FXML
	private ImageView fotito;
	@FXML
	private ImageView backImage;
	@FXML
	private Pane totalPanel;

	// PANEL INFO
	@FXML
	private Pane infoPanel;
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
	private Label glimmersLabel;
	@FXML
	private Label raceLabel;
	@FXML
	private Label btLabel;

	// AJUSTES
	@FXML
	private Pane panelAjustes;
	@FXML
	private Button btnAjustes; // Esto es el que lo invoca
	@FXML
	private Button botonAjustes;
	@FXML
	private Button btnVolver;
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
	@FXML
	private Button botonFoto;
	@FXML
	private Label linkW;
	@FXML
	private Label linkR;

	// UI
	@FXML
	private Pane contenedor;
	@FXML
	private Pane hotBatPanel;
	@FXML
	private Pane messagePanel;

	// COSAS DEL MESSAGE PANNEL
	@FXML
	private TextArea messageText;

	// BOTONES DEL HOTBAR
	@FXML
	private Button btrResume;
	@FXML
	private Button btnShop;
	@FXML
	private Button btnPets;
	@FXML
	private Button btnInventory;
	@FXML
	private Button btnLootBox;
	@FXML
	private Button btnUtiles;

	// PANELES DE CONTENEDOR
	@FXML
	private Pane resumePanel;
	@FXML
	private Pane shopPanel;
	@FXML
	private Pane inventaryPanel;
	@FXML
	private Pane lootBoxPanel;
	@FXML
	private Pane petPanel;

	// COSAS DEL INVENTARIO

	// PANEL RESUMEN
	@FXML
	private Pane resumenStats;
	@FXML
	private ProgressBar xpAtlBar;
	@FXML
	private Label atlLbl;
	@FXML
	private ProgressBar xpStrBar;
	@FXML
	private Label strLbl;
	@FXML
	private ProgressBar xpEndBar;
	@FXML
	private Label endLBL;
	@FXML
	private ProgressBar xpMinBar;
	@FXML
	private Label minLbl;
	@FXML
	private ProgressBar xpDexBar;
	@FXML
	private Label dexLbl;
	@FXML
	private Label speedLbl;
	@FXML
	private Label weightLbl;
	@FXML
	private Label freeForceLbl;
	@FXML
	private Label glimmersLbl;
	@FXML
	private Label vasteLbl;

	// Panel resumen de útiles
	@FXML
	private Pane modsPane;
	@FXML
	private Pane resumenUtiles;
	@FXML
	private Button runBtn;
	@FXML
	private Button saveBtn;
	@FXML
	private Button sleepBtn;
	@FXML
	private Button sleepBtn1;
	@FXML
	private Button refreshBtn;
	@FXML
	private Pane dadosPanel;

	// Panel del arma equipada
	@FXML
	private Pane equippedWeaponPanel;
	@FXML
	private Label equippedNameLbl;
	@FXML
	private Button envainarBtn;
	@FXML
	private Button fistsBtn;

	@FXML
	private VBox equipmentBox;

	@FXML
	private Pane equipmentSummaryPanel;

	// Panel resumen de vida
	@FXML
	private Pane livingResumen;
	@FXML
	private ProgressBar barraHp;
	@FXML
	private ProgressBar barraAcc;
	@FXML
	private ProgressBar barraKcal;
	@FXML
	private Label hpInfo;
	@FXML
	private Label accInfo;
	@FXML
	private Label kcalInfo;

	@FXML
	private CheckBox ventaja;

	@FXML
	private Button d20;

	@FXML
	private Button d16;

	@FXML
	private Button d12;

	@FXML
	private Button d10;

	@FXML
	private Button d8;

	@FXML
	private Button d6;

	@FXML
	private Button d4;

	@FXML
	private Button acrobaciasDado;

	@FXML
	private Button carismaDado;

	@FXML
	private Button percepcionDado;

	@FXML
	private Button vasteDado;

	@FXML
	private Button atlDado;

	@FXML
	private Button strDado;

	@FXML
	private Button endDado;

	@FXML
	private Button minDado;

	@FXML
	private Button dexDado;

	// PANEL INVENTARIO

	@FXML
	private ProgressBar barraHpI;
	@FXML
	private ProgressBar barraAccI;
	@FXML
	private ProgressBar barraKcalI;

	@FXML
	private TabPane inventarioPersonalPanel;

	@FXML
	private Tab todosInvTab;

	@FXML
	private TilePane todosInvPanel;

	@FXML
	private Tab weaponsInvTab;

	@FXML
	private TilePane weaponsInvPanel;

	@FXML
	private Tab equipmentInvTab;

	@FXML
	private TilePane equipmentInvPanel;

	@FXML
	private Tab itemsInvTab;

	@FXML
	private TilePane itemsInvPanel;

	@FXML
	private Tab edibleInvTab;

	@FXML
	private TilePane ediblesInvPanel;

	@FXML
	private Label pesoLbl;

	@FXML
	private TabPane escaparatePanel;

	@FXML
	private Tab todosEscTab;

	@FXML
	private TilePane todosEscPanel;

	@FXML
	private Tab weaponsEscTab;

	@FXML
	private TilePane weaponsEscPanel;

	@FXML
	private Tab equipmentEscTab;

	@FXML
	private TilePane equipmentEscPanel;

	@FXML
	private Tab itemsEscTab;

	@FXML
	private TilePane itemsEscPanel;

	@FXML
	private Tab edibleEscTab;

	@FXML
	private TilePane ediblesEscPanel;

	@FXML
	private Pane ammoPanel;

	@FXML
	private Button bowAmoBtn;
	@FXML
	private Button crossBowAmoBtn;
	@FXML
	private Button canonAmoBtn;
	@FXML
	private Button bulletAmoBtn;

	// PANEL ACTION

	@FXML
	private ProgressBar barraHpA;
	@FXML
	private ProgressBar barraAccA;
	@FXML
	private ProgressBar barraKcalA;
	private String posible;

	// PANEL TIENDA

	@FXML
	private TabPane tiendasTabPanel;

	@FXML
	private Tab tabGeneral;

	@FXML
	private TilePane tiendaGeneral;

	@FXML
	private TilePane tiendaGeneralTilePane;

	@FXML
	private Label puntosInspiracionLbl;

	@FXML
	private Button subirNvBtn;

	@FXML
	private Button ltbHabBtn;

	@FXML
	private Button ltbArmBtn;

	@FXML
	private Button ltbTalBtn;

	@FXML
	private Pane panelPreview;

	@FXML
	private TilePane tiendaOpcionesPanel;

	// PANEL CREAR ITEM

	@FXML
	private Button btnCrearItem;

	@FXML
	private Pane crearItemPane;

	@FXML
	private ComboBox<ItemFamily> selectorTipo;
	@FXML
	private Pane loadingOverlay;

	@FXML
	private VBox itemFormBox; // Lo agregaremos al FXML dinámicamente si no existe
	@FXML
	private Button guardarItemBtn; // Botón para guardar item

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////// START

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		selected = ComunAlmacen.selected;
		sesion.setOnCloseRequest(event -> {
			System.out.println("El programa se está cerrando...");
			try {
				selected.setAble(true);
				PjService.update(selected);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		try {
			cliente = new Client(this);
		} catch (Exception e) {
			System.out.println("Cerrando");
			System.out.println(e.getMessage());
			sesion.close();
		}
		backImage = LobbyController.backF;
		rellenarListaPaneles();
		setAllStyles();
		Platform.runLater(() -> {
			if (selected.getProfile() == null) {
				selected.setProfile(ImagenesUtil.fileToByte(new File("src/main/resources/" + defaultImage)));
			}
			fotito.setImage(ImagenesUtil.byteArrayToImage(selected.getProfile()));
			backImage.setEffect(blur);
			totalPanel.getChildren().add(0, backImage);
		});
		loadResume();
	}

	private void rellenarListaPaneles() {
		paneles.add(panelAjustes);
		paneles.add(resumePanel);
		paneles.add(shopPanel);
		paneles.add(inventaryPanel);
		paneles.add(lootBoxPanel);
		paneles.add(petPanel);
		paneles.add(crearItemPane);
	}

	private void setAllStyles() {
		panelAjustes.toFront();
		StyleAndEffectService.setAllStyles(absolutePane, tama, brillo, colorR, colorTexto, animaciones, true);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////// LOADS

	public void setVisibilities(Pane visible) { // esto hace que solo sea visible el panel
												// actual
		paneles.stream().forEach(e -> {
			e.setVisible(false);
			e.toBack();
		});
		visible.setVisible(true);
		visible.requestFocus();
		messagePanel.setVisible(true);
		StyleAndEffectService.fadeIn(visible, volumenEfectos);
	}

	// Carga de 0 el panel de resumen, con info stats, envainar y dados base
	@FXML
	public void loadResume() {
		try {
			PjService.update(selected);
			Tooltip.install(fotito, new Tooltip(selected.showInfo()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		cargarDatosInfo();
		setVisibilities(resumePanel);
	}

	@FXML
	public void loadAjustes() {
		linkW.setOnMouseClicked(evento -> {
			String link = GestorFicheroConfiguracion.devolverCredencial("linkW");
			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.browse(new URI(link));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		linkR.setOnMouseClicked(evento -> {
			String link = GestorFicheroConfiguracion.devolverCredencial("linkR");
			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.browse(new URI(link));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		setVisibilities(panelAjustes);
		messagePanel.setVisible(true);
		animChek.setSelected(animaciones);
		efSlider.setMin(0);
		efSlider.setMax(1);
		efSlider.setValue(volumenEfectos);
		muSlider.setMin(0);
		muSlider.setMax(1);
		muSlider.setValue(volumenFondo);
		ser.efectoPaginas(volumenEfectos);
	}

	@FXML
	public void loadAction() throws Exception {
		cargarAction();
		ser.efectoPaginas(volumenEfectos);
	}

	// Carga el inventario con opcion de filtros. Opciones de crear objetos y
	// hacerse con otros de la BBDD
	@FXML
	public void loadInventory() throws Exception {
		setVisibilities(inventaryPanel);
		messagePanel.setVisible(false);
		cargarInvent();
		cargarEscaparate();
		cargarMuniciones();
		ser.efectoPaginas(volumenEfectos);
	}

	// Carga el árbol de compra de habilidades basicas y los boost de stats con los
	// iPoints
	@FXML
	public void loadShop() throws Exception { // Añade Tabs a la tienda según los atributos del Pj (poderes y raza)
		setVisibilities(shopPanel);
		messagePanel.setVisible(true);
		cargarInspiracion(); // seteamos la accion al label de inspiración y su efecto en la BBDD
		cargarTiendas(); // Cargamos los tabs necesarios segun el pj
		ser.efectoPaginas(volumenEfectos);
	}

	// Aqui se pueden abrir cajas de 3 tipos para recibir loot de la BBDD segun su
	// rareza
	@FXML
	public void loadLootBoxes() {
		setVisibilities(lootBoxPanel);
		messagePanel.setVisible(true);
	}

	@FXML
	public void loadTaller() {
		setVisibilities(crearItemPane);
		cargarCreador();
		ser.efectoPaginas(volumenEfectos);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////// CARGAR

	@FXML
	private void cargarCreador() {
		if (selectorTipo.getItems().isEmpty()) {
			selectorTipo.getItems().addAll(ItemFamily.values());
		}
		if (itemFormBox.getChildren().size() > 0) {
			itemFormBox.getChildren().clear(); // esta es la caja en la que aparecerá el formulario
		}
		ItemFamily selectedFamily = ItemFamily.valueOf(selectorTipo.getValue().toString());
		switch (selectedFamily) {
		case EDIBLE -> mostrarFormularioEdible();
		case MELEWEAPON -> mostrarFormularioMelee();
		case RANGEWEAPON -> mostrarFormularioRange();
		case ITEM -> mostrarFormularioItem();
		case EQUIPMENT -> mostrarFormularioEquipment();
		case AMMO -> mostrarFormularioAmmo();
		case RACE -> mostrarFormularioRace();
		case BODYTYPE -> mostrarFormularioBodyType();

		default -> itemFormBox.getChildren().add(new Label("Sin hacer"));
		}
	}

	private ComboBox<Rarity> crearSelectorRareza() {
		ComboBox<Rarity> selectorRareza = new ComboBox<>();
		selectorRareza.getItems().addAll(Rarity.values());
		selectorRareza.setValue(Rarity.C);
		return selectorRareza;
	}

	private void mostrarFormularioRace() {
		TextField nombre = new TextField();
		nombre.setPromptText("Nombre de la raza");
		TextField modA = new TextField("0");
		TextField modS = new TextField("0");
		TextField modE = new TextField("0");
		TextField modM = new TextField("0");
		TextField modD = new TextField("0");
		TextField pesoBase = new TextField("0");

		itemFormBox.getChildren().addAll(new Label("Crear nueva Raza"), nombre, new Label("Mod A"), modA,
				new Label("Mod S"), modS, new Label("Mod E"), modE, new Label("Mod M"), modM, new Label("Mod D"), modD,
				new Label("Peso base"), pesoBase, guardarItemBtn);

		guardarItemBtn.setOnAction(e -> {
			try {
				Race race = new Race(nombre.getText(), Double.parseDouble(modA.getText()),
						Double.parseDouble(modS.getText()), Double.parseDouble(modE.getText()),
						Double.parseDouble(modM.getText()), Double.parseDouble(modD.getText()),
						Double.parseDouble(pesoBase.getText()));
				RaceService.create(race);
				mostrarConfirmacion("Raza creada con éxito");
			} catch (Exception ex) {
				mostrarError("Error al crear raza: " + ex.getMessage());
			}
		});
	}

	private void mostrarFormularioBodyType() {
		TextField nombre = new TextField();
		nombre.setPromptText("Nombre del tipo corporal");
		TextField modA = new TextField("0");
		TextField modS = new TextField("0");
		TextField modE = new TextField("0");
		TextField modM = new TextField("0");
		TextField modD = new TextField("0");

		itemFormBox.getChildren().addAll(new Label("Crear nuevo Tipo Corporal"), nombre, new Label("Mod A"), modA,
				new Label("Mod S"), modS, new Label("Mod E"), modE, new Label("Mod M"), modM, new Label("Mod D"), modD,
				guardarItemBtn);

		guardarItemBtn.setOnAction(e -> {
			try {
				BodyType bt = new BodyType(nombre.getText(), Double.parseDouble(modA.getText()),
						Double.parseDouble(modS.getText()), Double.parseDouble(modE.getText()),
						Double.parseDouble(modM.getText()), Double.parseDouble(modD.getText()));
				BodyTypeService.create(bt);
				mostrarConfirmacion("Tipo corporal creado con éxito");
			} catch (Exception ex) {
				mostrarError("Error al crear tipo corporal: " + ex.getMessage());
			}
		});
	}

	private void mostrarFormularioRange() {
		TextField nombre = new TextField();
		nombre.setPromptText("Nombre del arma");
		TextField descripcion = new TextField();
		descripcion.setPromptText("Descripción");
		TextField peso = new TextField();
		peso.setPromptText("Peso");
		TextField coste = new TextField();
		coste.setPromptText("Precio en glimmers");
		TextField danoBase = new TextField();
		danoBase.setPromptText("Daño base");
		ComboBox<ItemShape> forma = new ComboBox<>();
		forma.getItems().addAll(ItemShape.values());
		ComboBox<DamageType> tipoDano = new ComboBox<>();
		tipoDano.getItems().addAll(DamageType.values());
		ComboBox<Distance> distancia = new ComboBox<>();
		distancia.getItems().addAll(Distance.RANGED, Distance.MIXED);
		ComboBox<Rarity> rareza = crearSelectorRareza();

		itemFormBox.getChildren().addAll(new Label("Arma a distancia"), nombre, descripcion, peso, coste, danoBase,
				new Label("Forma del ítem"), forma, new Label("Tipo de daño"), tipoDano, new Label("Distancia"),
				distancia, new Label("Rareza"), rareza, guardarItemBtn);

		guardarItemBtn.setOnAction(e -> {
			try {
				Item i = new Item(nombre.getText(), rareza.getValue(), ItemFamily.RANGEWEAPON, forma.getValue(),
						Double.parseDouble(coste.getText()), Double.parseDouble(peso.getText()),
						Integer.parseInt(danoBase.getText()), tipoDano.getValue(), distancia.getValue(),
						descripcion.getText());
				ItemService.create(i);
				mostrarConfirmacion("Arma a distancia creada");
			} catch (Exception ex) {
				mostrarError("Error: " + ex.getMessage());
			}
		});
	}

	private void mostrarFormularioItem() {
		TextField nombre = new TextField();
		nombre.setPromptText("Nombre del ítem");
		TextField descripcion = new TextField();
		descripcion.setPromptText("Descripción");
		TextField peso = new TextField();
		peso.setPromptText("Peso");
		TextField coste = new TextField();
		coste.setPromptText("Precio en glimmers");
		ComboBox<ItemShape> forma = new ComboBox<>();
		forma.getItems().addAll(ItemShape.values());
		ComboBox<Rarity> rareza = crearSelectorRareza();

		itemFormBox.getChildren().addAll(new Label("Ítem general"), nombre, descripcion, peso, coste,
				new Label("Forma del ítem"), forma, new Label("Rareza"), rareza, guardarItemBtn);

		guardarItemBtn.setOnAction(e -> {
			try {
				Item i = new Item(nombre.getText(), rareza.getValue(), ItemFamily.ITEM, forma.getValue(),
						Double.parseDouble(coste.getText()), Double.parseDouble(peso.getText()), descripcion.getText());
				ItemService.create(i);
				mostrarConfirmacion("Ítem creado");
			} catch (Exception ex) {
				mostrarError("Error: " + ex.getMessage());
			}
		});
	}

	private void mostrarFormularioEquipment() {
		TextField nombre = new TextField();
		nombre.setPromptText("Nombre del equipo");
		TextField descripcion = new TextField();
		descripcion.setPromptText("Descripción");
		TextField peso = new TextField();
		peso.setPromptText("Peso");
		TextField coste = new TextField();
		coste.setPromptText("Precio en glimmers");
		TextField mod = new TextField();
		mod.setPromptText("Mod (A S E M D)");
		ComboBox<ItemShape> forma = new ComboBox<>();
		forma.getItems().addAll(ItemShape.values());
		ComboBox<Rarity> rareza = crearSelectorRareza();

		itemFormBox.getChildren().addAll(new Label("Equipo"), nombre, descripcion, mod, peso, coste,
				new Label("Forma del ítem"), forma, new Label("Rareza"), rareza, guardarItemBtn);

		guardarItemBtn.setOnAction(e -> {
			try {
				Item i = new Item(nombre.getText(), rareza.getValue(), mod.getText(), ItemFamily.EQUIPMENT,
						forma.getValue(), Double.parseDouble(coste.getText()), Double.parseDouble(peso.getText()),
						descripcion.getText());
				ItemService.create(i);
				mostrarConfirmacion("Equipo creado");
			} catch (Exception ex) {
				mostrarError("Error: " + ex.getMessage());
			}
		});
	}

	private void mostrarFormularioAmmo() {
		TextField nombre = new TextField();
		nombre.setPromptText("Nombre de la munición");
		TextField descripcion = new TextField();
		descripcion.setPromptText("Descripción");
		TextField peso = new TextField();
		peso.setPromptText("Peso");
		TextField coste = new TextField();
		coste.setPromptText("Precio en glimmers");
		ComboBox<ItemShape> forma = new ComboBox<>();
		forma.getItems().addAll(ItemShape.values());
		ComboBox<Rarity> rareza = crearSelectorRareza();

		itemFormBox.getChildren().addAll(new Label("Munición"), nombre, descripcion, peso, coste,
				new Label("Forma del ítem"), forma, new Label("Rareza"), rareza, guardarItemBtn);

		guardarItemBtn.setOnAction(e -> {
			try {
				Item i = new Item(nombre.getText(), rareza.getValue(), ItemFamily.AMMO, forma.getValue(),
						Double.parseDouble(coste.getText()), Double.parseDouble(peso.getText()), descripcion.getText());
				ItemService.create(i);
				mostrarConfirmacion("Munición creada");
			} catch (Exception ex) {
				mostrarError("Error: " + ex.getMessage());
			}
		});
	}

	private void mostrarFormularioEdible() {
		TextField nombre = new TextField();
		nombre.setPromptText("Nombre del item");
		TextField descripcion = new TextField();
		descripcion.setPromptText("Descripción");
		TextField efectos = new TextField();
		efectos.setPromptText("Efectos (cal hp acc)");
		TextField peso = new TextField();
		peso.setPromptText("Peso (ej: 0.5)");
		TextField coste = new TextField();
		coste.setPromptText("Precio en glimmers");
		ComboBox<Rarity> rareza = crearSelectorRareza();

		itemFormBox.getChildren().addAll(new Label("Item EDIBLE"), nombre, descripcion, efectos, peso, coste,
				new Label("Rareza"), rareza, guardarItemBtn);

		guardarItemBtn.setOnAction(e -> {
			try {
				String n = nombre.getText();
				String d = descripcion.getText();
				String eff = efectos.getText();
				double w = Double.parseDouble(peso.getText());
				int c = Math.max(1, Integer.parseInt(coste.getText()));
				Item edible = new Item(n, rareza.getValue(), null, ItemFamily.EDIBLE, ItemShape.MISC, w, 0, c, null,
						null, eff, d);
				ItemService.create(edible);
				mostrarConfirmacion("Item edible creado con éxito");
			} catch (Exception ex) {
				mostrarError("Datos inválidos: " + ex.getMessage());
			}
		});
	}

	private void mostrarFormularioMelee() {
		TextField nombre = new TextField();
		nombre.setPromptText("Nombre del arma");
		TextField descripcion = new TextField();
		descripcion.setPromptText("Descripción");
		TextField peso = new TextField();
		peso.setPromptText("Peso (ej: 2.0)");
		TextField coste = new TextField();
		coste.setPromptText("Precio en glimmers");
		TextField modStats = new TextField();
		modStats.setPromptText("Mod Stats (A S E M D)");
		ComboBox<ItemShape> forma = new ComboBox<>();
		forma.getItems().addAll(ItemShape.SWORD, ItemShape.DAGGER, ItemShape.AXE);
		ComboBox<DamageType> tipoDano = new ComboBox<>();
		tipoDano.getItems().addAll(DamageType.CUT, DamageType.STAB);
		ComboBox<Rarity> rareza = crearSelectorRareza();

		itemFormBox.getChildren().addAll(new Label("Arma cuerpo a cuerpo"), nombre, descripcion, modStats, peso, coste,
				new Label("Forma del ítem"), forma, new Label("Tipo de daño"), tipoDano, new Label("Rareza"), rareza,
				guardarItemBtn);

		guardarItemBtn.setOnAction(e -> {
			try {
				String n = nombre.getText();
				String d = descripcion.getText();
				String mod = modStats.getText();
				double w = Double.parseDouble(peso.getText());
				int c = Math.max(1, Integer.parseInt(coste.getText()));
				ItemShape sh = forma.getValue();
				DamageType dt = tipoDano.getValue();
				Item arma = new Item(n, rareza.getValue(), mod, ItemFamily.MELEWEAPON, sh, w, 4.5, c, dt,
						Distance.MELEE, "0 0 0", d);
				ItemService.create(arma);
				mostrarConfirmacion("Arma creada con éxito");
			} catch (Exception ex) {
				mostrarError("Datos inválidos: " + ex.getMessage());
			}
		});
	}

	private void mostrarConfirmacion(String mensaje) {
		Alert a = new Alert(Alert.AlertType.INFORMATION);
		a.setHeaderText(mensaje);
		a.show();
	}

	private void mostrarError(String mensaje) {
		Alert a = new Alert(Alert.AlertType.ERROR);
		a.setHeaderText("Error");
		a.setContentText(mensaje);
		a.show();
	}

	private void cargarEscaparate() throws Exception {
		try {
			todosEscPanel.getChildren().clear();
			todosEscPanel.setPrefTileHeight(-1);
			todosEscPanel.setPrefTileWidth(-1);
			weaponsEscPanel.getChildren().clear();
			ediblesEscPanel.getChildren().clear();
			itemsEscPanel.getChildren().clear();
			equipmentEscPanel.getChildren().clear();
		} catch (Exception e) {
			System.out.println("Aqui");
		}

		List<Item> todos = ItemService.getAll(); // todfos los items de objetos
		todos.forEach(e -> {
			Button b = new Button(e.getName());
			Tooltip.install(b, new Tooltip(e.showInfo()));
			b.setId("botonMasMasOpaco");
			Item tipo = e;
			// (Item) ItemService.obtenerPorId(e.getId_O())
			StyleAndEffectService.pointElement(b, tama, brillo, colorR, colorTexto);
			b.setOnAction(event -> administrarItemEsc(tipo));
			Button copy = crearBotonCopia(b);
			if (tipo.getItemFamily().equals(ItemFamily.MELEWEAPON)
					|| tipo.getItemFamily().equals(ItemFamily.RANGEWEAPON)) {
				weaponsEscPanel.getChildren().add(b);
			} else if (tipo.getItemFamily().equals(ItemFamily.EDIBLE)) {
				ediblesEscPanel.getChildren().add(b);
			} else if (tipo.getItemFamily().equals(ItemFamily.ITEM)) {
				itemsEscPanel.getChildren().add(b);
			} else if (e.getItemFamily().equals(ItemFamily.EQUIPMENT)) {
				equipmentEscPanel.getChildren().add(b);
			}
			if (!e.getItemFamily().equals(ItemFamily.AMMO)) {
				todosEscPanel.getChildren().add(copy);
			}
		});
	}

	private void cargarAction() throws Exception {
		Set<Skill> skills = selected.getSkills();
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Habilidades");
		alert.setHeaderText("Selecciona una habilidad para usar:");
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setId("panelPreview");
		VBox skillBox = new VBox();
		skillBox.setSpacing(10);
		for (Skill h : skills) {
			Button b = new Button(h.getName());
			b.setId("botonMasOpaco");
			StyleAndEffectService.pointElement(b, tama, brillo, colorR, colorTexto);
			b.setOnAction(event -> {
				posible = h.getName();
				System.out.println("Habilidad seleccionada: " + posible);
				alert.close();
				hideAction();
			});
			skillBox.getChildren().add(b);
		}
		dialogPane.setContent(skillBox);
		alert.showAndWait();
	}

	private void cargarDatosInfo() {
		if (selected.getWeapon() != null) {
			if (selected.getWeapon().getDistance().equals(Distance.MELEE)) {
				equippedNameLbl.setText(selected.getWeapon().getName());
				Tooltip.install(equippedNameLbl, new Tooltip(selected.getWeapon().showInfo()));
			} else {
				try {
					String ammo = InventoryService.cantidadAmmo(selected, selected.getWeapon().getItemShape());
					equippedNameLbl.setText(selected.getWeapon().getName() + " [" + ammo + "]");
				} catch (Exception ne) {
					System.out.println(
							"NO SE PUDO CARGAR LA MUNICION DEL ARMA EQUIPADA\n................................\n"
									+ ne.getMessage());
					equippedNameLbl.setText(selected.getWeapon().getName());
				}
			}
		} else {
			equippedNameLbl.setText(selected.getBasicHitter().toString());
		}

		barraHp.setProgress((double) selected.getHp() / selected.getMaxHp());
		barraAcc.setProgress((double) selected.getActions() / selected.getMaxActions());
		barraKcal.setProgress((double) selected.getKcal() / selected.getMaxKcal());
		hpInfo.setText(selected.getHp() + "/" + selected.getMaxHp());
		accInfo.setText(selected.getActions() + "/" + selected.getMaxActions());
		kcalInfo.setText(selected.getKcal() + "/" + selected.getMaxKcal());

		xpAtlBar.setProgress((double) selected.getXpA() / ((selected.getModA() * 2) * selected.getAtl()));
		xpStrBar.setProgress((double) selected.getXpS() / ((selected.getModS() * 2) * selected.getStr()));
		xpEndBar.setProgress((double) selected.getXpE() / ((selected.getModE() * 2) * selected.getEnd()));
		xpDexBar.setProgress((double) selected.getXpD() / ((selected.getModD() * 2) * selected.getDex()));
		xpMinBar.setProgress((double) selected.getXpM() / ((selected.getModM() * 2) * selected.getMin()));

		atlLbl.setText("ATL     " + selected.getAtl());
		strLbl.setText("STR     " + selected.getStr());
		endLBL.setText("END     " + selected.getEnd());
		dexLbl.setText("DEX     " + selected.getDex());
		minLbl.setText("MIN     " + selected.getMin());

		speedLbl.setText("SPEED     " + df.format(selected.getSpeed()));
		weightLbl.setText("WEIGHT      " + df.format(selected.getWeight()));
		freeForceLbl.setText("FREE FORCE   " + df.format(selected.getLeftStrong()));
		glimmersLbl.setText("GLIMMERS   " + df.format(selected.getGlimmers()));
		vasteLbl.setText(
				"VASTE   " + df.format(selected.getVaste()) + "p / " + df.format(selected.getVaste_Distance()) + " m");

		rellenarMods();

		cargarResumenEquipamiento();

	}

	private void rellenarMods() {
		modsPane.getChildren().clear();

		double spacing = 10;
		double labelWidth = 50;
		double labelHeight = 25;
		double startX = 2;
		double y = 10;

		List<Double> mods = selected.getMods();
		System.out.println("Mods: " + mods);

		for (int i = 0; i < mods.size(); i++) {
			Double d = mods.get(i);

			Label modLbl = new Label(df.format(d));
			modLbl.setPrefSize(labelWidth, labelHeight);
			modLbl.setAlignment(Pos.CENTER);
			modLbl.setStyle("-fx-background-color: #eee; -fx-border-radius: 4;"
					+ "-fx-background-radius: 4; -fx-font-size: 11px;");

			double x = startX + i * (labelWidth + spacing);
			modLbl.setLayoutX(x);
			modLbl.setLayoutY(y);

			modsPane.getChildren().add(modLbl);
		}
	}

	private void cargarInvent() throws Exception {
		pesoLbl.setText("" + InventoryService.getPeso(selected) + " kg / " + selected.getMaxCarry());
		todosInvPanel.getChildren().clear();
		weaponsInvPanel.getChildren().clear();
		ediblesInvPanel.getChildren().clear();
		itemsInvPanel.getChildren().clear();
		equipmentInvPanel.getChildren().clear();

		barraHpI.setProgress((double) selected.getHp() / selected.getMaxHp());
		barraAccI.setProgress((double) selected.getActions() / selected.getMaxActions());
		barraKcalI.setProgress((double) selected.getKcal() / selected.getMaxKcal());

		List<Item> equipo = ((Equipment) EquipmentService.getById(selected)).getEquip();

		HashMap<Item, Integer> todos = InventoryService.getInventarioDePJ(selected);
		todos.keySet().forEach(e -> {
			Button b = new Button(e.getName() + " [" + todos.get(e) + "]");
			Tooltip.install(b, new Tooltip(e.showInfo()));
			b.setId("botonMasMasOpaco");
			StyleAndEffectService.pointElement(b, tama, brillo, colorR, colorTexto);
			b.setOnAction(event -> administrarItem(e));
			Button copy = crearBotonCopia(b);
			if (e.getItemFamily().equals(ItemFamily.MELEWEAPON) || e.getItemFamily().equals(ItemFamily.RANGEWEAPON)) {
				if (selected.getWeapon() != null && selected.getWeapon().getName().equals(e)) {
					b.setText("[EQUIPPED]" + b.getText());
				}
				weaponsInvPanel.getChildren().add(b);
			} else if (e.getItemFamily().equals(ItemFamily.EDIBLE)) {
				ediblesInvPanel.getChildren().add(b);
			} else if (e.getItemFamily().equals(ItemFamily.ITEM)) {
				itemsInvPanel.getChildren().add(b);
			} else if (e.getItemFamily().equals(ItemFamily.EQUIPMENT)) {
				if (equipo.contains(e)) {
					b.setText("[EQUIPPED]" + b.getText());
				}
				equipmentInvPanel.getChildren().add(b);
			}
			if (!e.getItemFamily().equals(ItemFamily.AMMO)) {
				todosInvPanel.getChildren().add(copy);
			}
		});
	}

	private void cargarMuniciones() throws Exception { // Hacer que el panel se genere segun todos los enums que hay
		bowAmoBtn.setText("Bow [" + InventoryService.cantidadAmmo(selected, ItemShape.BOW) + "]");
		bowAmoBtn.setOnAction(event -> administrarAmmo(ItemShape.BOW));
		crossBowAmoBtn.setText("CrossBow [" + InventoryService.cantidadAmmo(selected, ItemShape.CROSSBOW) + "]");
		crossBowAmoBtn.setOnAction(event -> administrarAmmo(ItemShape.CROSSBOW));
		canonAmoBtn.setText("Canon [" + InventoryService.cantidadAmmo(selected, ItemShape.CANON) + "]");
		canonAmoBtn.setOnAction(event -> administrarAmmo(ItemShape.CANON));
		bulletAmoBtn.setText("Gun [" + InventoryService.cantidadAmmo(selected, ItemShape.RIFLE) + "]");
		bulletAmoBtn.setOnAction(event -> administrarAmmo(ItemShape.RIFLE));
	}

	private void cargarInspiracion() {
		try {
			System.out.println("Inspiracion cargada");
			int puntos = selected.getInspirationPoints();
			puntosInspiracionLbl.setText("Inspiración: " + puntos);
			puntosInspiracionLbl.setOnMouseClicked(event -> {
				TextInputDialog dialog = new TextInputDialog();
				dialog.setHeaderText("Cuanto pierdes(-) / ganas(+)?:");
				dialog.setContentText("Cuanto pierdes(-) / ganas(+)?:");
				dialog.showAndWait().ifPresent(input -> {
					try {
						int numero = Integer.parseInt(input);
						gastarInspiracion(numero);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////// FUNCIONAMIENTO

	private void cargarResumenEquipamiento() {
		equipmentBox.getChildren().clear();

		try {
			Equipment equipo = (Equipment) EquipmentService.getById(selected);
			System.err.println(equipo.toString());

			List<Item> piezas = equipo.getEquip();
			if (piezas == null || piezas.isEmpty()) {
				System.out.println("No hay equipo");
				equipmentSummaryPanel.setVisible(false);
				equipmentSummaryPanel.setManaged(false);
				return;
			}

			equipmentSummaryPanel.setVisible(true);
			equipmentSummaryPanel.setManaged(true);

			for (Item pieza : piezas) {
				Button b = new Button(pieza.getName());
				b.setId("botonMasMasOpaco");
				StyleAndEffectService.pointElement(b, tama, brillo, colorR, colorTexto);
				Tooltip.install(b, new Tooltip(pieza.showInfo()));
				b.setText("");
				b.setPrefSize(20, 20);
				b.setMinSize(20, 20);
				b.setOnMouseEntered(ev -> {
					b.setText(pieza.getName());
					b.setPrefWidth(120);
					b.setPrefHeight(40);
					b.setTranslateX(-100);
				});
				b.setOnMouseExited(ev -> {
					b.setText("");
					b.setPrefSize(20, 20);
					b.setTranslateX(0);
				});

				b.setOnAction(event -> {
					Alert ventanita = new Alert(Alert.AlertType.CONFIRMATION);
					ventanita.setTitle("Administrar");
					ventanita.setHeaderText("ADMINISTRAR");
					ventanita.setContentText("Selecciona una opción:");

					ButtonType verInfo = new ButtonType("Información");
					ButtonType desequipar = new ButtonType("Desequipar");
					ButtonType cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
					ventanita.getButtonTypes().setAll(verInfo, desequipar, cancelar);

					ventanita.showAndWait().ifPresent(res -> {
						switch (res.getText()) {
						case "Información":
							Alert infoVtn = new Alert(Alert.AlertType.INFORMATION);
							infoVtn.setContentText(pieza.showInfo());
							infoVtn.show();
							break;
						case "Desequipar":
							try {
								switch (pieza.getItemShape()) {
								case HEAD -> equipo.setHead(null);
								case CHEST -> equipo.setChest(null);
								case LEGS -> equipo.setLegs(null);
								case FEET -> equipo.setFeet(null);
								case EXTRA -> {
									if (equipo.getExtra1() == pieza)
										equipo.setExtra1(null);
									else if (equipo.getExtra2() == pieza)
										equipo.setExtra2(null);
									else if (equipo.getExtra3() == pieza)
										equipo.setExtra3(null);
								}
								default ->
									throw new IllegalArgumentException("Unexpected value: " + pieza.getItemShape());
								}
								selected.setEquipment(equipo);
								refresh();
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						}
					});
				});

				equipmentBox.getChildren().add(b);
			}

		} catch (Exception e) {
			e.printStackTrace();
			equipmentSummaryPanel.setVisible(false);
		}
	}

	private void cargarTiendas() throws Exception {
		List<Skill> skills = SkillService.getAll();
		List<Skill> commonSkills = new ArrayList<Skill>();
		List<Skill> powerSkills = new ArrayList<Skill>();
		List<Skill> raceSkills = new ArrayList<Skill>();

		commonSkills = skills.stream().filter(e -> e.getPower().equals("REGULAR")).collect(Collectors.toList());

		tiendasTabPanel.getTabs().clear(); // Solo limpiar si no es "G"
		rellenarTienda(commonSkills, "G");

		if (!selected.getPower().equals("REGULAR")) {
			switch (selected.getPower()) {
			case "JANO":
				powerSkills = filtrarLista("JANO", skills);
				break;
			case "BUNRAKU":
				powerSkills = filtrarLista("BUNRAKU", skills);
				break;
			case "LEIRZA":
				powerSkills = filtrarLista("LEIRZA", skills);
				break;
			}
			rellenarTienda(powerSkills, "T");
		}
		if (selected.getRace().getName().equals("HUMANO")) {
			raceSkills = filtrarLista("VASTE", skills);
			rellenarTienda(raceSkills, "VASTE");
		} else if (selected.getRace().getName().equals("SOLEO")) {
			raceSkills = filtrarLista("DENNARIS", skills);
			rellenarTienda(raceSkills, "DENNARIS");
		}
	}

	private List<Skill> filtrarLista(String string, List<Skill> skills) {
		return skills.stream().filter(e -> e.getPower().equals(string)).collect(Collectors.toList());
	}

	@FXML
	public void hideAction() {
		try {
			procesarMensaje(Habilidad.getHabilidadMethod(posible, selected, ventaja.isSelected(), 0));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void rellenarTienda(List<Skill> skills, String tipo) {
		Tab tab = new Tab();
		TilePane tienda = new TilePane();
		System.out.println(skills.toString());
		switch (tipo) {
		case "T":
			tab.setText(selected.getPower());
			break;
		case "G":
			tab = tabGeneral;
			tienda = tiendaGeneral;
			tienda.getChildren().clear();
			break;
		case "DENNARIS":
			tab.setText(tipo);
			break;
		case "VASTE":
			tab.setText(tipo);
			break;
		}
		tab.setContent(tienda);
		tiendasTabPanel.getTabs().add(tab);

		for (Skill h : skills) {
			Button boton = new Button();
			boton.setText(h.getName());
			if (!selected.getSkills().contains(h)) {
				boton.setOnAction(o -> {
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setHeaderText("¿SEGURO?");
					alert.setContentText("¿SEGURO?\nCuesta " + h.getCost() + "ip");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.isPresent() && result.get() == ButtonType.OK) {
						int puntosActuales;
						try {
							puntosActuales = selected.getInspirationPoints();
							int costo = h.getCost();
							if (puntosActuales >= costo) {
								selected.getSkills().add(h);
								gastarInspiracion(-costo);
							} else {
								Alert errorAlert = new Alert(Alert.AlertType.ERROR);
								errorAlert.setHeaderText("Puntos insuficientes");
								errorAlert.setContentText("No tienes suficientes puntos de inspiración.");
								errorAlert.showAndWait();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				tienda.getChildren().add(boton);
			} else {
				boton.setDisable(true);
			}
		}

	}

	private int getIp() {
		return Integer.parseInt(puntosInspiracionLbl.getText().split(" ")[1]);
	}

	public void gastarInspiracion(int numero) throws Exception {
		if (numero < 0 && selected.getInspirationPoints() <= 0) { // asi no pueden entrar negativos)
			throw new Exception();
		}
		selected.setInspirationPoints(selected.getInspirationPoints() + numero);
		PjService.update(selected);
		loadShop();
	}

	private void administrarAmmo(ItemShape tipoDeMunicion) {
		Dialog<String> ammo = new Dialog<>();
		ammo.setTitle("Munición");
		ammo.setHeaderText("Tirar (-) Coger (+)");
		ButtonType confirmarButton = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
		ButtonType cancelarButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
		ammo.getDialogPane().getButtonTypes().addAll(confirmarButton, cancelarButton);
		TextField cantidadInput = new TextField();
		ammo.getDialogPane().setContent(cantidadInput);
		ammo.setResultConverter(button -> {
			if (button == confirmarButton) {
				return cantidadInput.getText();
			}
			return null;
		});
		ammo.showAndWait().ifPresent(input -> {
			try {
				int cantidad = Integer.parseInt(input);
				InventoryService.usarMunicion(tipoDeMunicion, selected, cantidad);
				System.out.println("Hecho");
				loadInventory();
			} catch (Exception e1) {
				e1.printStackTrace();
				Alert infoVtn = new Alert(AlertType.INFORMATION);
				infoVtn.setContentText(e1.getMessage());
				infoVtn.show();
			}
		});
	}

	private Button crearBotonCopia(Button original) {
		Button copy = new Button(original.getText());
		copy.setId(original.getId()); // el de css
		copy.setOnAction(original.getOnAction());
		StyleAndEffectService.pointElement(copy, tama, brillo, colorR, colorTexto);
		return copy;
	}

	private void administrarItemEsc(Item tipo) {
		try {
			Alert ventanita = new Alert(AlertType.CONFIRMATION);
			ventanita.setTitle("Administrar");
			ventanita.setHeaderText("ADMINISTRAR");
			ventanita.setContentText("Selecciona una opción:");
			ButtonType verInfo = new ButtonType("Informacion");
			ButtonType coger = new ButtonType("Coger");
			ButtonType addImage = new ButtonType("FOtito Nueva");
			ButtonType eliminar = new ButtonType("Eliminar");
			if (dm) {
				ventanita.getButtonTypes().setAll(verInfo, coger, eliminar);
			} else {
				ventanita.getButtonTypes().setAll(verInfo, coger);
			}
			ventanita.showAndWait().ifPresent(presionado -> {
				switch (presionado.getText()) {
				case "Informacion":
					Alert infoVtn = new Alert(AlertType.INFORMATION);
					infoVtn.setContentText(tipo.showInfo());
					if (tipo.getImagenUrl() != null) {
						Image image = new Image(tipo.getImagenUrl(), true);
						ImageView imageView = new ImageView(image);
						imageView.setFitWidth(300);
						imageView.setPreserveRatio(true);

						VBox contenido = new VBox(imageView);
						contenido.setSpacing(10);
						infoVtn.getDialogPane().setContent(contenido);
					}
					infoVtn.show();
					break;
				case "Coger":
					try {
						int cant = InventoryService.getCantidad(selected, tipo.getName());
						if (cant >= 1) {
							InventoryService.sumar(selected, tipo, 1);
						} else {
							Inventory adquisicion = new Inventory(tipo, selected, 1, tipo.getWeight(), tipo.getName());
							InventoryService.insertar(adquisicion);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					break;
				case "Eliminar":
					try {
						ItemService.delete(tipo);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case "FOtito Nueva":
					TextInputDialog dialog = new TextInputDialog(tipo.getImagenUrl());
					dialog.setTitle("Meter imagen nueva");
					dialog.setHeaderText("Introduce la nueva URL (pinterest regular)");
					dialog.setContentText("URL:");

					dialog.showAndWait().ifPresent(url -> {
						tipo.setImagenUrl(url);
						try {
							ItemService.update(tipo);
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
					break;
				}
			});
			cargarInvent();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void administrarItem(Item tipo) {
		try {
			Alert ventanita = new Alert(AlertType.CONFIRMATION);
			ventanita.setTitle("Administrar");
			ventanita.setHeaderText("ADMINISTRAR");
			ventanita.setContentText("Selecciona una opción:");

			ButtonType verInfo = new ButtonType("Informacion");
			ButtonType tirarCantidad = new ButtonType("Tirar");
			if (tipo.getItemFamily().equals(ItemFamily.EDIBLE)) {
				ButtonType consumir = new ButtonType("Consumir");
				ventanita.getButtonTypes().setAll(verInfo, tirarCantidad, consumir);
			} else if (tipo.getItemFamily().equals(ItemFamily.MELEWEAPON)
					|| tipo.getItemFamily().equals(ItemFamily.RANGEWEAPON)
					|| tipo.getItemFamily().equals(ItemFamily.EQUIPMENT)) {
				ButtonType accion = new ButtonType("Equipar");
				if (selected.getWeapon() != null) {
					accion = new ButtonType("Cambiar por actual");
					if (selected.getWeapon().getName().equals(tipo.getName())) {
						accion = new ButtonType("Desequipar");
					}
					if (tipo.getItemFamily().equals(ItemFamily.EQUIPMENT)) {
						Equipment equipo = (Equipment) EquipmentService.getById(selected);
						if (equipo.getEquip().contains(tipo)) {
							accion = new ButtonType("Desequipar");
						}
					}
				} else {
					accion = new ButtonType("Equipar");
				}
				ventanita.getButtonTypes().setAll(verInfo, tirarCantidad, accion);
			} else if (tipo.getItemFamily().equals("Item")) {
				ventanita.getButtonTypes().setAll(verInfo, tirarCantidad);
			} else {
				ButtonType equipar = new ButtonType("Equipar");
				ventanita.getButtonTypes().setAll(verInfo, tirarCantidad, equipar);
			}
			ventanita.showAndWait().ifPresent(presionado -> {

				switch (presionado.getText()) {
				case "Tirar":
					Dialog<String> tirarVtn = new Dialog<>();
					tirarVtn.setTitle("Tirar");
					tirarVtn.setHeaderText("¿Cuántos quieres tirar?");
					ButtonType confirmarButton = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
					ButtonType cancelarButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
					tirarVtn.getDialogPane().getButtonTypes().addAll(confirmarButton, cancelarButton);
					TextField cantidadInput = new TextField();
					tirarVtn.getDialogPane().setContent(cantidadInput);

					tirarVtn.setResultConverter(button -> {
						if (button == confirmarButton) {
							return cantidadInput.getText();
						}
						return null;
					});

					tirarVtn.showAndWait().ifPresent(input -> {
						try {
							int cantidad = Integer.parseInt(input);
							InventoryService.tirar(tipo, selected, cantidad);
							if (selected.getWeapon().equals(tipo)) {
								selected.setWeapon(null);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					});
					break;
				case "Informacion":
					Alert infoVtn = new Alert(AlertType.INFORMATION);
					infoVtn.setContentText(tipo.showInfo());
					if (tipo.getImagenUrl() != null) {
						Image image = new Image(tipo.getImagenUrl(), true);
						ImageView imageView = new ImageView(image);
						imageView.setFitWidth(300);
						imageView.setPreserveRatio(true);

						VBox contenido = new VBox(imageView);
						contenido.setSpacing(10);
						infoVtn.getDialogPane().setContent(contenido);
					}
					infoVtn.show();
					break;
				case "Consumir":
					try {
						pU.makeEffect(tipo.getConsumEffect(), selected);
						InventoryService.tirar(tipo, selected, 1);
					} catch (Exception e1) {
						e1.printStackTrace();
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setHeaderText("Nanai de la china");
						alert.setContentText(e1.getMessage());
					}
					break;
				case "Desequipar":
					try {
						if (!tipo.getItemFamily().equals(ItemFamily.EQUIPMENT)) {
							envainar();
						} else {
							Equipment equipo = (Equipment) EquipmentService.getById(selected);
							if (!equipo.getEquip().contains(tipo)) {
								switch (tipo.getItemShape()) {
								case HEAD:
									equipo.setHead(null);
									break;
								case CHEST:
									equipo.setChest(null);
									break;
								case LEGS:
									equipo.setLegs(null);
									break;
								case FEET:
									equipo.setFeet(null);
									break;
								case EXTRA:
									if (equipo.getExtra1() != tipo) {
										equipo.setExtra1(null);
									} else if (equipo.getExtra2() != tipo) {
										equipo.setExtra2(null);
									} else if (equipo.getExtra3() != tipo) {
										equipo.setExtra3(null);
									} else {
										equipo.setExtra1(null);
									}
									break;
								default:
									break;
								}
							}
							selected.setEquipment(equipo);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case "Cambiar por actual", "Equipar":
					try {
						if (!tipo.getItemFamily().equals(ItemFamily.EQUIPMENT)) {
							selected.setWeapon(tipo);
							PjService.update(selected);
						} else {
							// Si no va , es new Equipment(selected) para obtener ID
							Equipment equipo = (Equipment) EquipmentService.getById(selected);
							if (!equipo.getEquip().contains(tipo)) {
								switch (tipo.getItemShape()) {
								case HEAD:
									equipo.setHead(tipo);
									break;
								case CHEST:
									equipo.setChest(tipo);
									break;
								case LEGS:
									equipo.setLegs(tipo);
									break;
								case FEET:
									equipo.setFeet(tipo);
									break;
								case EXTRA:
									if (equipo.getExtra1() != null) {
										equipo.setExtra1(tipo);
									} else if (equipo.getExtra2() != null) {
										equipo.setExtra2(tipo);
									} else if (equipo.getExtra3() != null) {
										equipo.setExtra3(tipo);
									} else {
										equipo.setExtra1(tipo);
									}
									break;
								default:
									break;
								}
							}
							selected.setEquipment(equipo);
							PjService.update(selected);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					break;
				case "Guardar en otro sitio":
					System.out.println("Todavia no hay otros sitios xd");
					break;
				}
			});

			cargarInvent();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////// OBLIGATORIOS

	@FXML
	private void subirNivel() throws Exception {
		if (getIp() >= 2) {
			gastarInspiracion(getIp() - 2);
			selected.setAtl(selected.getAtl() + 1);
			selected.setStr(selected.getStr() + 1);
			selected.setEnd(selected.getEnd() + 1);
			selected.setMin(selected.getMin() + 1);
			selected.setDex(selected.getDex() + 1);
			procesarMensaje("SUBE DE NIVEL ++1");
		} else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText("NO");
			alert.setContentText("NO HAY PUNTOS");
		}
	}

	@FXML
	private void ltbHabilidades() throws Exception {
		if (getIp() >= 3) {
			gastarInspiracion(getIp() - 3);
		} else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText("NO");
			alert.setContentText("NO HAY PUNTOS");
		}
	}

	@FXML
	private void ltbArmas() throws Exception {
		if (getIp() >= 4) {
			gastarInspiracion(getIp() - 4);
		} else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText("NO");
			alert.setContentText("NO HAY PUNTOS");
		}
	}

	@FXML
	private void ltbTalentos() throws Exception {
		if (getIp() >= 7) {
			gastarInspiracion(getIp() - 7);
		} else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText("NO");
			alert.setContentText("NO HAY PUNTOS");
		}
	}

	@FXML
	private void tirarD20() {
		procesarMensaje(" Resultado D20: " + dado.nextInt(1, 21));
	}

	@FXML
	private void tirarD16() {
		procesarMensaje(" Resultado D16: " + dado.nextInt(1, 17));
	}

	@FXML
	private void tirarD12() {
		procesarMensaje(" Resultado D12: " + dado.nextInt(1, 13));
	}

	@FXML
	private void tirarD10() {
		procesarMensaje(" Resultado D10: " + dado.nextInt(1, 11));
	}

	@FXML
	private void tirarD8() {
		procesarMensaje(" Resultado D8: " + dado.nextInt(1, 9));
	}

	@FXML
	private void tirarD6() {
		procesarMensaje(" Resultado D6: " + dado.nextInt(1, 7));
	}

	@FXML
	private void tirarD4() {
		procesarMensaje(" Resultado D4: " + dado.nextInt(1, 5));
	}

	// DADOS DE STATS

	@FXML
	private void dadoAcro() {
		selected.setXpA(selected.getXpA() + 1);

		procesarMensaje(" Resultado D" + pU.diceLooby(selected, "acr") + "-Acrobatics: "
				+ dado.nextInt(1, pU.diceLooby(selected, "acr") + 1));

	}

	@FXML
	private void dadoCari() {
		selected.setXpM(selected.getXpM() + 1);

		procesarMensaje(" Resultado D" + pU.diceLooby(selected, "cha") + "-Charisma: "
				+ dado.nextInt(1, pU.diceLooby(selected, "cha") + 1));

	}

	@FXML
	private void dadoPerc() {
		selected.setXpM(selected.getXpM() + 1);

		procesarMensaje(" Resultado D" + pU.diceLooby(selected, "per") + "-Perception: "
				+ dado.nextInt(1, pU.diceLooby(selected, "per") + 1));

	}

	@FXML
	private void dadoVast() {
		selected.setXpM(selected.getXpM() + 1);
		selected.setXpA(selected.getXpA() + 1);
		selected.setXpE(selected.getXpE() + 1);
		procesarMensaje(" Resultado D" + pU.diceLooby(selected, "vas") + "-Vaste: "
				+ dado.nextInt(1, pU.diceLooby(selected, "vas") + 1));
	}

	@FXML
	private void dadoAtl() {
		selected.setXpA(selected.getXpA() + 1);
		procesarMensaje(" Resultado D" + pU.diceLooby(selected, "alt") + "-Atl: "
				+ dado.nextInt(1, pU.diceLooby(selected, "alt") + 1));
	}

	@FXML
	private void dadoStr() {
		selected.setXpS(selected.getXpS() + 1);
		procesarMensaje(" Resultado D" + pU.diceLooby(selected, "str") + "-Str: "
				+ dado.nextInt(1, pU.diceLooby(selected, "str") + 1));
	}

	@FXML
	private void dadoEnd() {
		selected.setXpE(selected.getXpE() + 1);
		procesarMensaje(" Resultado D" + pU.diceLooby(selected, "end") + "-End: "
				+ dado.nextInt(1, pU.diceLooby(selected, "end") + 1));
	}

	@FXML
	private void dadoMin() {
		selected.setXpM(selected.getXpM() + 1);
		procesarMensaje(" Resultado D" + pU.diceLooby(selected, "min") + "-Min: "
				+ dado.nextInt(1, pU.diceLooby(selected, "min") + 1));
	}

	@FXML
	private void dadoDex() {
		selected.setXpD(selected.getXpD() + 1);

		procesarMensaje(" Resultado D" + pU.diceLooby(selected, "dex") + "-Dex: "
				+ dado.nextInt(1, pU.diceLooby(selected, "dex") + 1));
	}

	@FXML
	private void sprintMenu() { // ChatGPT de manual, cambiar y hacerlo a mano
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText("Introduce un número:");
		dialog.setContentText("Distancia:");
		dialog.showAndWait().ifPresent(input -> {
			try {
				int numero = Integer.parseInt(input);
				String nuevaPregunta = pU.checkDitance(selected, numero);
				Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
				confirmDialog.setHeaderText(nuevaPregunta);
				confirmDialog.setContentText("¿Te renta?");
				ButtonType buttonSi = new ButtonType("Sí", ButtonBar.ButtonData.YES);
				ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.NO);
				confirmDialog.getButtonTypes().setAll(buttonSi, buttonNo);
				confirmDialog.showAndWait().ifPresent(response -> {
					if (response == buttonSi) {
						try {
							String[] b = nuevaPregunta.split(" ");
							pU.useActions(Integer.parseInt(b[1]), selected);
							refresh();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			} catch (Exception e) {
			}
		});
	}

	@FXML
	private void save() throws Exception {

		PjService.update(selected);

	}

	@FXML
	private void escribirMensaje() throws Exception {
		PjService.update(selected);
	}

	@FXML
	private void rest() throws Exception {
		pU.sleep(selected, 1);
		procesarMensaje(" Ha descansado un poco");
	}

	@FXML
	private void longRest() throws Exception {
		pU.sleep(selected, 2);
		procesarMensaje(" Ha descansado bien");
	}

	@FXML
	private void refresh() { // SONIDO EFECTOS
		System.out.println("\n----------------------------------------------- PASO");
		ser.efectoClick(volumenEfectos);
		try {
			loadResume();
		} catch (Exception e) {
			System.out.println("Algo falla en el refresh");
			System.out.println(e.getMessage());
		}
	}

	@FXML
	private void saveFotito() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
		try {
			File e = fileChooser.showOpenDialog(null);
			selected.setProfile(ImagenesUtil.fileToByte(e));
			fotito.setImage(ImagenesUtil.byteArrayToImage(selected.getProfile()));
			PjService.update(selected);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void guardarAjustes() {
		animaciones = animChek.isSelected();
		volumenFondo = muSlider.getValue();
		volumenEfectos = efSlider.getValue();
		GestorFicheroConfiguracion.actualizarValor("animaciones", animaciones + "");
		GestorFicheroConfiguracion.actualizarValor("volEfectos", volumenEfectos + "");
		GestorFicheroConfiguracion.actualizarValor("volMusica", volumenFondo + "");
		ComunAlmacen.mediaPlayer.setVolume(volumenFondo);
		StyleAndEffectService.setAllStyles(absolutePane, tama, brillo, colorR, colorTexto, animaciones, false);
		refresh();
	}

	@FXML
	private void envainar() throws Exception {
		selected.setWeapon(null);
		equippedNameLbl.setText(selected.getBasicHitter().toString());
	}

	@FXML
	private void fistAttack() throws Exception { // Este hacer en el usable.
		procesarMensaje(pU.fistAttack(selected));
	}

	@FXML
	private void vanillaAttack() throws Exception {
		if (selected.getWeapon() != null) {
			if (selected.getWeapon().getDistance().equals(Distance.RANGED)) {
				TextInputDialog dialog = new TextInputDialog("10");
				dialog.setHeaderText("Dificultad");
				dialog.setContentText("Dificultad?:");
				dialog.showAndWait().ifPresent(input -> {
					try {
						int diff = Integer.parseInt(input);
						try {
							procesarMensaje("" + pU.atacar(selected, diff, ventaja.isSelected()) + "\nQuedan "
									+ InventoryService.cantidadAmmo(selected, selected.getWeapon().getItemShape())
									+ " proyectiles");
						} catch (Exception e) {
							procesarMensaje(e.getMessage());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			} else {
				try {
					procesarMensaje("" + pU.atacar(selected, 4, ventaja.isSelected()));
				} catch (Exception e) {
					procesarMensaje(e.getMessage());
				}
			}
		} else {
			fistAttack();
		}
	}

	@FXML
	public void adminGlimmers() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText("Cuanto pierdes(-) / ganas(+)?:");
		dialog.setContentText("Cuanto pierdes(-) / ganas(+)?:");
		dialog.showAndWait().ifPresent(input -> {
			int numero = Integer.parseInt(input);
			pU.useGlimmers(numero, selected);
			refresh();
		});
	}

	@FXML
	private void modifyHp() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText("Cuanto pierdes(-) / ganas(+)?:");
		dialog.setContentText("Cuanto pierdes(-) / ganas(+)?:");
		dialog.showAndWait().ifPresent(input -> {
			try {
				int numero = Integer.parseInt(input);
				System.out.println(numero + " = valor de HP para añadir");
				pU.useHp(numero, selected);
				refresh();
			} catch (Exception e) {
				System.out.println("Ha muerto"); // hacerlo con una alerta
			}
		});
	}

	@FXML
	private void modifyAcc() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText("Cuanto pierdes(-) / ganas(+)?:");
		dialog.setContentText("Cuanto pierdes(-) / ganas(+)?:");
		dialog.showAndWait().ifPresent(input -> {
			try {
				int numero = Integer.parseInt(input);
				System.out.println(numero + " = valor de ACC para añadir");
				pU.useActions(numero, selected);
				refresh();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@FXML
	private void modifyKcal() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText("Cuanto pierdes(-) / ganas(+)?:");
		dialog.setContentText("Cuanto pierdes(-) / ganas(+)?:");
		dialog.showAndWait().ifPresent(input -> {
			try {
				int numero = Integer.parseInt(input);
				System.out.println(numero + " = valor de KCAL para añadir");
				pU.useKcal(numero, selected);
				refresh();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@FXML
	public void returnToMain() throws Exception {
		selected.setAble(true);
		PjService.update(selected);
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ors/views/LoobyVista.fxml"));
			Parent root = loader.load();
			Scene ventana = new Scene(root);
			ventana.getStylesheets()
					.add(getClass().getResource(GestorFicheroConfiguracion.devolverCredencial("css")).toExternalForm());
			sesion.setTitle("LOBBY"); // esto es el primaryStage
			sesion.setScene(ventana);
			sesion.sizeToScene();
			sesion.setResizable(false);
			sesion.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////// SYNCHRO

	public void procesarMensaje(String mensaje) {
		try {
			cliente.enviarMensaje(selected.getName() + ": " + mensaje);
		} catch (Exception e) {
			e.printStackTrace();
		}
		refresh();
	}

	public void escribirConsola(String mensaje) {
		messageText.setWrapText(true);
		LocalDateTime momento = LocalDateTime.now();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");
		String f = momento.format(formato);
		messageText.setText(messageText.getText() + "\n(" + f + ") " + mensaje);
		messageText.positionCaret(messageText.getText().length());
	}

}
