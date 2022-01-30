package controllers;

import java.io.IOException;

import code.Competition;
import code.CompetitionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class promptDeleteController {
	Stage stage;
	Scene scene;
	Parent root;
	@FXML
	private Label lblMessage;

	Competition competition;

	public void setCompetition(Competition competition) {
		this.competition = competition;
		lblMessage.setText("Are you sure you want to delete " + competition.getName() + " ?");

	}

	@FXML // linked to cancel button
	void backToBrowseMenu(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/interfaces/browseScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML // linked to delete button
    void deleteCompetition(ActionEvent event) throws IOException {
        CompetitionManager.deleteCompetition(competition);
        backToBrowseMenu(event);
    }

}
