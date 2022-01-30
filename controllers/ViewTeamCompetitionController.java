package controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;

import code.Competition;
import code.Student;
import code.Team;
import code.TeamCompetition;
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

public class ViewTeamCompetitionController {

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
	private TableColumn<Student, String> idColumn;
	@FXML
	private TableColumn<Student, String> majorColumn;
	@FXML
	private TableColumn<Student, String> studentNameColumn;
	ObservableList<Student> studentsList;

	@FXML
	private TableView<Team> teamsTable;
	@FXML
	private TableColumn<Team, String> teamNameColumn;
	@FXML
	private TableColumn<Team, Integer> teamNumberColumn;
	@FXML
	private TableColumn<Team, Integer> rankColumn;

	TeamCompetition competition;

	ObservableList<Team> teamsList;

	public void setupScene(Competition comp) {
		this.competition = (TeamCompetition) comp;
		
		lblCompName.setText(competition.getName() + " (Teams)");
		lblWebsite.setText(competition.getLink());
		String dateString = new SimpleDateFormat("d-MMM-yyyy").format(competition.getDate());
		lblDate.setText("Due date: " + dateString);

		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));

		teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("teamName"));
		teamNumberColumn.setCellValueFactory(new PropertyValueFactory<>("teamNumber"));
		rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

		teamsList = FXCollections.observableArrayList(competition.getTeams());
		teamsTable.setItems(teamsList);
		teamsTable.getSelectionModel().selectedItemProperty().addListener(e -> {
			studentsList = FXCollections
					.observableArrayList(teamsTable.getSelectionModel().getSelectedItem().getStudents());
			studentsTable.setItems(studentsList);
		});
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
