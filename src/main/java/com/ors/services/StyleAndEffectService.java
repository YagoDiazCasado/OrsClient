package com.ors.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

public class StyleAndEffectService {

	private String sonidoClick = "/com/ors/sounds/switch-sound.mp3";
	private String sonidoPagina = "/com/ors/sounds/pagina.mp3";
	private String sonidoEntrar = "/com/ors/sounds/Voicy_E Shop Start.mp3";
	private String sonidoDinero = "/com/ors/sounds/collect-points-190037.mp3";
	private String sonidoNivel = "/com/ors/sounds/Victory Music - Sound Effect for editing.mp3";
	private String sonidoPasar = "/com/ors/sounds/nintendo_switch_logo.mp3";
	private String sonidoAumentar = "/com/ors/sounds/Voicy_Back .mp3";

	public void efectoClick(Double volumenEfectos) {
		play(volumenEfectos, sonidoClick);
	}

	public void efectoPaginas(Double volumenEfectos) {
		play(volumenEfectos, sonidoPagina);
	}

	public void efectoPasar(Double volumenEfectos) {
		play(volumenEfectos, sonidoPasar);
	}

	public void efectoEntrar(Double volumenEfectos) {
		play(volumenEfectos, sonidoEntrar);
	}

	public void efectoDinero(Double volumenEfectos) {
		play(volumenEfectos, sonidoDinero);
	}

	public void efectoNivel(Double volumenEfectos) {
		play(volumenEfectos, sonidoNivel);
	}

	public void efectoEleccion(Double volumenEfectos) {
		play(volumenEfectos, sonidoClick);
	}

	public void efectoAumentar(Double volumenEfectos) {
		play(volumenEfectos, sonidoAumentar);
	}

	public void play(Double volumenEfectos, String sonido) {
		try {
			AudioClip clip = new AudioClip(getClass().getResource(sonido).toString());
			clip.setVolume(volumenEfectos);
			clip.play();
		} catch (Exception e) {
			AudioClip clip = new AudioClip(getClass().getResource(sonidoClick).toString());
			clip.setVolume(volumenEfectos);
			clip.play();
		}
	}

	public File[] checkFotosCarpeta() {
		File carpetita = new File("src/main/resources/com/ors/images/Fondos");
		File[] todas = carpetita.listFiles();

		return todas;
	}
	
	public static void fadeIn(Node node, double durationInSeconds) {
		FadeTransition fadeIn = new FadeTransition(Duration.seconds(durationInSeconds), node);
		fadeIn.setFromValue(0.5);
		fadeIn.setToValue(1);
		fadeIn.play();
	}

	public static void fadeOut(Node node, double durationInSeconds) {
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(durationInSeconds), node);
		fadeOut.setFromValue(node.getOpacity());
		fadeOut.setToValue(0);
		fadeOut.play();
	}

	public static void entradaEpica(Node e, int j) {
		new Thread(() -> {
			Platform.runLater(() -> apoyoAnim(e, j)); // Ejecuta en el hilo de JavaFX
		}).start();
	}

	public static void apoyoAnim(Node e, int j) {
		int signo = 1;
		if (j < 0) {
			signo = -1;
		}
		TranslateTransition translateTransition = new TranslateTransition();
		translateTransition.setNode(e);
		Random dado = new Random();
		translateTransition.setDuration(Duration.seconds(dado.nextDouble(0.1, 0.4)));
		translateTransition.setFromX(j);
		translateTransition.setToX(signo * 10);
		translateTransition.setFromY(0);
		translateTransition.setToY(0);

		Glow glowEffect = new Glow(0.6);
		e.setEffect(glowEffect);
		Timeline iluminar = new Timeline(
				new KeyFrame(Duration.millis(100), new KeyValue(glowEffect.levelProperty(), 1)));
		Timeline desiluminar = new Timeline(
				new KeyFrame(Duration.millis(600), new KeyValue(glowEffect.levelProperty(), 0)));

		translateTransition.setOnFinished(event -> {
			e.setTranslateX(0);
			e.setTranslateY(0);
			iluminar.playFromStart();
			desiluminar.playFromStart();
		});
		translateTransition.play();
	}

	public static void pointElement(Node e, double hastaT, double hastaL, String colorR, String colorTexto) {
		Glow glowEffect = new Glow(0.0);
		e.setEffect(glowEffect);
		ScaleTransition arriba = new ScaleTransition(Duration.millis(200), e);
		arriba.setToX(1 + hastaT);
		arriba.setToY(1 + hastaT);
		ScaleTransition abajo = new ScaleTransition(Duration.millis(200), e);
		abajo.setToX(1);
		abajo.setToY(1);
		Timeline iluminar = new Timeline(
				new KeyFrame(Duration.millis(200), new KeyValue(glowEffect.levelProperty(), hastaL)));
		Timeline desiluminar = new Timeline(
				new KeyFrame(Duration.millis(200), new KeyValue(glowEffect.levelProperty(), 0)));
		e.setOnMouseEntered(event -> {
			arriba.playFromStart();
			iluminar.playFromStart();
			e.setStyle("-fx-text-fill:" + colorR + ";");
		});
		e.setOnMouseExited(event -> {
			abajo.playFromStart();
			desiluminar.playFromStart();
			e.setStyle("-fx-text-fill:" + colorTexto + ";");
		});
	}
	
	public void animateButtonSize(Button button, double targetWidth, double targetHeight) {
	    Timeline timeline = new Timeline(
	        new KeyFrame(Duration.millis(200),
	            new KeyValue(button.prefWidthProperty(), targetWidth, Interpolator.EASE_BOTH),
	            new KeyValue(button.prefHeightProperty(), targetHeight, Interpolator.EASE_BOTH)
	        )
	    );
	    timeline.play();
	}

	public static void softPointElement(Node e, double brillo, String colorR, String colorTexto) { // pa cuando se
																									// desactivan
																									// animaciones
		Glow glowEffect = new Glow(0.0);
		e.setEffect(glowEffect);
		Timeline iluminar = new Timeline(
				new KeyFrame(Duration.millis(200), new KeyValue(glowEffect.levelProperty(), brillo)));
		Timeline desiluminar = new Timeline(
				new KeyFrame(Duration.millis(200), new KeyValue(glowEffect.levelProperty(), 0)));
		e.setOnMouseEntered(event -> {
			iluminar.playFromStart();
			e.setStyle("-fx-text-fill:" + colorR + ";");
		});
		e.setOnMouseExited(event -> {
			desiluminar.playFromStart();
			e.setStyle("-fx-text-fill:" + colorTexto + ";");
		});
	}

	public static void parpadeoConstante(Node e) {
		Glow glowEffect = new Glow(0.0);
		e.setEffect(glowEffect);
		Timeline parpadeo = new Timeline(new KeyFrame(Duration.millis(0), new KeyValue(glowEffect.levelProperty(), 0)),
				new KeyFrame(Duration.millis(2000), new KeyValue(glowEffect.levelProperty(), 0.6)));
		parpadeo.setCycleCount(Timeline.INDEFINITE); // bucle, como l ode la muscia
		parpadeo.setAutoReverse(true); // vuelve atrás como otro key frame para que no de salto
		parpadeo.play();
	}

	public static List<Node> getComponentesAumentables(Node n, boolean fotos) {
		List<Node> compos = new ArrayList<>();
		meterCompos(n, compos, fotos);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return compos;
	}

	private static void meterCompos(Node nod, List<Node> compos, boolean fotos) {
		if (nod instanceof Pane) {
			Pane panel = (Pane) nod;
			for (Node n : panel.getChildren()) {
				if (n instanceof Pane || n instanceof TabPane || n instanceof TilePane) { // VUELVE A MIRARLO con el
																							// hijo
					if (Runtime.getRuntime().availableProcessors() > 1) {
						Thread fasilito = new Thread(() -> {
							meterCompos(n, compos, fotos);
						});
						fasilito.start();
					} else {
						meterCompos(n, compos, fotos);
					}
				} else {
					if (fotos) {
						synchronized (compos) {
							compos.add(n);
						}
					} else {
						if (!(n instanceof ImageView)) {
							synchronized (compos) {
								compos.add(n);
							}
						}
					}
				}
			}
		}
	}

	public static void setAllStyles(Pane absolutePane, double tama, double brillo, String colorR, String colorTexto,
			boolean animaciones, boolean fotos) { // CAMBIAR, HACER QUE DEPENDIENDO DEL TIPO DE ELEMENTO CREZCA MAS O
													// MENOS, HACERLO EN EL FOR EACH DE GOLPE. USAR LO OTRO SOLO PARA
													// SACAR LOS NODES
		if (animaciones) {
			getComponentesAumentables(absolutePane, fotos)
					.forEach(e -> StyleAndEffectService.pointElement(e, tama, brillo, colorR, colorTexto));
		} else {
			getComponentesAumentables(absolutePane, fotos)
					.forEach(e -> StyleAndEffectService.softPointElement(e, brillo, colorR, colorTexto));
		}

	}

}
