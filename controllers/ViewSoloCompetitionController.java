package controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;

import code.Competition;
import code.SoloCompetition;
import code.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ViewSoloCompetitionController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private Button btBrowse;
	@FXML
	private Label lblCompName;
	@FXML
	private Label lblDate;
	@FXML
	private Label lblWebsite;

	@FXML
	private TableView<Student> studentsTable;
	@FXML
	private TableColumn<Student, String> majorColumn;
	@FXML
	private TableColumn<Student, String> nameColumn;
	@FXML
	private TableColumn<Student, Integer> rankColumn;
	@FXML
	private TableColumn<Student, String> idColumn;

	SoloCompetition competition;

	ObservableList<Student> studentsList;

	public void setupScene(Competition comp) {
		
		this.competition = (SoloCompetition) comp;
		lblCompName.setText(competition.getName() + " (Solo)");
		lblWebsite.setText(competition.getLink());

		String dateString = new SimpleDateFormat("d-MMM-yyyy").format(competition.getDate());
		lblDate.setText("Due date: " + dateString);

		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
		rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

		studentsList = FXCollections.observableArrayList(competition.getStudents());
		studentsTable.setItems(studentsList);
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
