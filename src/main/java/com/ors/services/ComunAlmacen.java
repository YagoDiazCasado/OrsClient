package com.ors.services;

import java.text.DecimalFormat;
import java.util.Random;

import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import com.ors.usables.PjUsables;
import com.ors.utiles.GestorFicheroConfiguracion;
import com.ors.vo.Adventure;
import com.ors.vo.PJ;

public class ComunAlmacen {

	public static Stage sesion;

	public static StyleAndEffectService ser = new StyleAndEffectService();
	public static PjUsables pU = new PjUsables();
	public static Random dado = new Random();
	public static String colorR = GestorFicheroConfiguracion.devolverCredencial("colorR");
	public static String colorTexto = GestorFicheroConfiguracion.devolverCredencial("colorTexto");
	public static MediaPlayer mediaPlayer;
	public static Double volumenFondo = Double.parseDouble(GestorFicheroConfiguracion.devolverCredencial("volMusica"));
	public static Double volumenEfectos = Double
			.parseDouble(GestorFicheroConfiguracion.devolverCredencial("volEfectos"));
	public static boolean animaciones = "true".equals(GestorFicheroConfiguracion.devolverCredencial("animaciones"));
	public static boolean dm;
	public static double tama = 0.04;
	public static double brillo = 0.7;
	public static DecimalFormat df = new DecimalFormat("#.##"); // Limita a 2 decimales
	public static String rutaMusica = "/com/ors/sounds/Sis Puella Magica!.mp3";
	public static PJ selected;
	public static Adventure adventure;

	public static String urlBase;

}
