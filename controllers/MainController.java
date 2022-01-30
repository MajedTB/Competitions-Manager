package controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainController {
	// these 3 objects are needed to load a new interface
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	// some elements of the interface
	@FXML
	private Button btBrowse;
	@FXML
	private Button btSendEmail;
	@FXML
	private Label lblWelcome;


	
	@FXML  // This is linked to create competition button
	void createCompetitionScene(ActionEvent event) throws IOException {
		// loading the interface, it is linked to CreateCompController.class
		Parent root = FXMLLoader.load(getClass().getResource("/interfaces/createCompetition.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML  // This is linked to browse competition button
	void browseScene(ActionEvent event) throws IOException {
		// loading the interface, it is linked to BrowseController.class
		Parent root = FXMLLoader.load(getClass().getResource("/interfaces/BrowseScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void sendEmailScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/interfaces/promptSendEmail.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
