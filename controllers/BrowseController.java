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

public class BrowseController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private Label lblCompName;
	@FXML
	private Label lblCompDate;
	@FXML
    private Label lblWebsite;
	@FXML
	private Button btAddStudents;
	@FXML
	private Button btDelete;
	@FXML
	private Button btEdit;
	@FXML
	private Button btView;
	@FXML
	private Button btVisitWebsite;
	@FXML
	private ListView<Competition> listViewComp;

	Competition selectedComp;

	@Override // this method will start when the interface is loaded, it will setup the scene
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblCompName.setText("Select a Competition");
		lblCompDate.setText("");
		lblWebsite.setText("");

		// adding the competition names to the list in the left of the interface
		listViewComp.getItems().addAll(CompetitionManager.getCompetitions());

		// modifying the menu based on the selected competition
		listViewComp.getSelectionModel().selectedItemProperty().addListener(e -> {
			selectedComp = listViewComp.getSelectionModel().getSelectedItem();
			lblCompName.setText(selectedComp.getName());
			lblWebsite.setText(selectedComp.getLink());

			// displaying date 
			String dateString = new SimpleDateFormat("d-MMM-yyyy").format(selectedComp.getDate());
			lblCompDate.setText("Due date: " + dateString);

			// enabling the buttons to be clicked
			btAddStudents.setDisable(false);
			btView.setDisable(false);
			btVisitWebsite.setDisable(false);
			btEdit.setDisable(false);
			btDelete.setDisable(false);
		});
	}

	@FXML // this is linked to the "browse website" button
	void browseWebsiteScene(ActionEvent event) throws IOException {
		// loading the interface, it is linked to browseWebsiteController class
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/browseWebsite.fxml"));
		root = loader.load();
		
		// sending the link to the controller
		BrowseWebsiteController controller = loader.getController();
		controller.loadPage(this.selectedComp);

		// enter the interface
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML // this is linked to "add students" button
	void addStudentsScene(ActionEvent event) throws IOException {
		// loading the appropiate interface based on the competition type
		if (selectedComp instanceof SoloCompetition) {
			// loading the interface to add students to solo competition,
			// it is linked to AddStudentsController.class
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/addStudentScene.fxml"));
			root = loader.load();
			// sending the competition object to the controller class
			AddStudentsController controller = loader.getController();
			controller.setupScene(selectedComp);
		} else {
			// loading the interface to add students to team competition,
			// it is linked to AddStudentsWithTeamController.class
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/addStudentWithTeamScene.fxml"));
			root = loader.load();
			// sending the competition to the controller
			AddStudentWithTeamController controller = loader.getController();
			controller.setupScene(selectedComp);
		}
		// entering the interface
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML // this is linked to "View details" button
	void viewScene(ActionEvent event) throws IOException {
		if (selectedComp instanceof SoloCompetition) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/viewSoloCompetition.fxml"));
			root = loader.load();
			ViewSoloCompetitionController controller = loader.getController();
			controller.setupScene(selectedComp);
		} else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/viewTeamCompetition.fxml"));
			root = loader.load();
			ViewTeamCompetitionController controller = loader.getController();
			controller.setupScene(selectedComp);
		}

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void editScene(ActionEvent event) throws IOException {
		if (selectedComp instanceof SoloCompetition) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/editSoloCompetition.fxml"));
			root = loader.load();
			EditSoloCompetitionController controller = loader.getController();
			controller.setupScene(selectedComp);
		} else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/editTeamCompetition.fxml"));
			root = loader.load();
			EditTeamCompetitionController controller = loader.getController();
			controller.setupScene(selectedComp);
		}

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML // this is linked to the "delete" button
	void deleteCompetition(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/promptDelete.fxml"));
		root = loader.load();
		promptDeleteController controller = loader.getController();
		controller.setCompetition(selectedComp); // sending the competition object to the controller
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
