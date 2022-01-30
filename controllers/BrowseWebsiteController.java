package controllers;

import java.io.IOException;

import code.Competition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class BrowseWebsiteController {
	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private Label lblTitle;
	@FXML
	private WebView websiteViewer;
	private WebEngine engine;

	void loadPage(Competition competition) {
		try {
			lblTitle.setText(competition.getName() + " website");
			engine = websiteViewer.getEngine();
			engine.load(competition.getLink());
			engine.reload();
		} catch (Exception e) {
		}
	}

	@FXML
	void goBack(ActionEvent event) {
		try {
			engine.getHistory().go(-1);
		} catch (Exception e) {
		}
	}

	@FXML
	void goForward(ActionEvent event) {
		try {
			engine.getHistory().go(1);
		} catch (Exception e) {
		}
	}

	@FXML
	void refresh(ActionEvent event) {
		engine.reload();
	}

	@FXML
	void backToBrowseMenu(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/interfaces/browseScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void backToMainMenu(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/interfaces/mainMenu.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
