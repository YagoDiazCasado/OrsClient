module ORS_FX_V2 {

	exports main.java.com.ors.application to javafx.graphics;
	exports main.java.com.ors.controllers;
	exports main.resources.com.ors.images;
	exports main.java.com.ors.services;
	exports main.java.com.ors.usables;
	exports main.java.com.ors.utiles;
	exports main.java.com.ors.vo;

	// AQUI ESTAMOS ABRIENDO ESTAS CLASES AL fxmlLOader
	opens main.java.com.ors.application to javafx.fxml;
	opens main.java.com.ors.controllers to javafx.fxml;
	opens main.resources.com.ors.views to javafx.fxml;
	opens main.java.com.ors.vo;
	
	requires java.desktop;
	
	requires java.sql;
	requires javafx.base;
	requires javafx.graphics;
	requires javafx.media;
	requires transitive javafx.controls; //mirar porque con esto no va
	requires javafx.fxml;
	requires javafx.swing;
	requires java.net.http;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.core;
	requires org.apache.httpcomponents.client5.httpclient5;
	requires org.apache.httpcomponents.core5.httpcore5;
}