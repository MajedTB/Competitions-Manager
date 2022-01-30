package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import code.Competition;
import code.CompetitionManager;
import code.SoloCompetition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class PromptSendEmailController implements Initializable {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private Button btConfirm;

	@FXML
	private Label lblCompDate;

	@FXML
	private Label lblCompName;

	@FXML
	private Label lblWebsite;

	@FXML
	private ListView<Competition> listViewComp;

	Competition selectedComp;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblCompName.setText("Select a Competition");
		lblCompDate.setText("");
		lblWebsite.setText("");
		btConfirm.setDisable(true);

		// adding the competition names to the list in the left of the interface
		listViewComp.getItems().addAll(CompetitionManager.getCompetitions());

		// modifying the menu based on the selected competition
		listViewComp.getSelectionModel().selectedItemProperty().addListener(e -> {
			selectedComp = listViewComp.getSelectionModel().getSelectedItem();
			lblCompName.setText(selectedComp.getName());
			lblWebsite.setText(selectedComp.getLink());

			String dateString = new SimpleDateFormat("d-MMM-yyyy").format(selectedComp.getDate());
			lblCompDate.setText("Due date: " + dateString);
			btConfirm.setDisable(false);
		});
	}

	@FXML
	void confirm(ActionEvent event) throws IOException {
		if (selectedComp instanceof SoloCompetition) {
			// loading the interface to add students to solo competition,
			// it is linked to AddStudentsController.class
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/sendEmailToSolo.fxml"));
			root = loader.load();
			// sending the competition object to the controller class
			SendEmailSoloController controller = loader.getController();
			controller.setupScene(selectedComp);
		} else {
			// loading the interface to add students to team competition,
			// it is linked to AddStudentsWithTeamController.class
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/sendEmailToTeam.fxml"));
			root = loader.load();
			// sending the competition to the controller
			SendEmailTeamController controller = loader.getController();
			controller.setupScene(selectedComp);
		}
		// entering the interface
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
