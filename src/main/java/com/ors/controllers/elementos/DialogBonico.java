package com.ors.controllers.elementos;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class DialogBonico extends Dialog<Object> {

	public VBox content;
	public ScrollPane scroll;

	public DialogBonico() {
		this.getDialogPane().getStylesheets().add(getClass().getResource("/css/lobbyStyles.css").toExternalForm());
		this.getDialogPane().setId("dialogStyle");
		this.content = new VBox(10);
		content.setAlignment(Pos.CENTER);
		content.setPadding(new Insets(20));
		content.setStyle("-fx-background-color: transparent;");
		this.scroll = new ScrollPane(content);
		scroll.setFitToWidth(true);
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		// scroll.setPrefViewportHeight(200);
		scroll.setStyle("-fx-background-color: transparent;");

		this.getDialogPane().setContent(scroll);

		this.getDialogPane().setMinWidth(300);
		this.getDialogPane().setPrefWidth(Region.USE_COMPUTED_SIZE);
		this.getDialogPane().setMaxWidth(600);

		this.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		this.getDialogPane().setPrefHeight(Region.USE_COMPUTED_SIZE);
		this.getDialogPane().setMaxHeight(Region.USE_COMPUTED_SIZE);
		this.setResizable(true);
	}

}
