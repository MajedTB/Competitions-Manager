package controllers;

import java.io.IOException;

import code.Competition;
import code.SoloCompetition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class promptAddCompController {

	private Stage stage;
	private Scene scene;
	private Parent root;

	private Competition competition;

	void setCompetition(Competition competition) {
		this.competition = competition;
	}

	@FXML // this is linked to "Add Students" button
	void addStudentsScene(ActionEvent event) throws IOException {
		// loading the appropiate interface based on the competition type
		if (competition instanceof SoloCompetition) {
			// loading the interface to add students to solo competition,
			// it is linked to AddStudentsController.class
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/addStudentScene.fxml"));
			root = loader.load();
			// sending the competition object to the controller class
			AddStudentsController controller = loader.getController();
			controller.setupScene(competition);
		} else {
			// loading the interface to add students to team competition,
			// it is linked to AddStudentsWithTeamController.class
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/addStudentWithTeamScene.fxml"));
			root = loader.load();
			// sending the competition to the controller
			AddStudentWithTeamController controller = loader.getController();
			controller.setupScene(competition);
		}

		// going to the scene
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