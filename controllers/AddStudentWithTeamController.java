package controllers;

import java.io.IOException;

import code.Competition;
import code.CompetitionManager;
import code.Student;
import code.Team;
import code.TeamCompetition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddStudentWithTeamController {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private Label lblTitle;
	@FXML
	private TextField majorField;
	@FXML
	private TextField idField;
	@FXML
	private TextField teamNumberField;
	@FXML
	private TextField rankField;
	@FXML
	private TextField studentNameField;
	@FXML
	private ComboBox<String> teamList;
	@FXML
	private Label lblMessage;

	private TeamCompetition competition;
	private Team team;

	// this will store the competition object into the class, and modify the title
	void setupScene(Competition competition) {
		this.competition = (TeamCompetition) competition;
		lblTitle.setText("Add Student to " + competition.getName());

		teamList.getItems().addAll(this.competition.getAllTeamNames());
	}

	@FXML // this is linked to the Add button
	void addToCompetition(ActionEvent event) {
		Student student;

		// checking if the All fields are entered (except rank)
		if (studentNameField.getText().isBlank() || idField.getText().isBlank() || majorField.getText().isBlank()
				|| teamNumberField.getText().isBlank() || teamList.getSelectionModel().getSelectedItem() == null
				|| teamList.getSelectionModel().getSelectedItem().isBlank()) {
			lblMessage.setText("Please Enter All Information");
			return;
		}

		// Checking if the entered ID is a number
		if (!idField.getText().matches("[0-9]+")) { // ID is not a number
			lblMessage.setText("ID Value Should Contain Numbers Only");
			return;
		}

		// now we can create a student object because its data is valid
		student = new Student(studentNameField.getText(), idField.getText(), majorField.getText().toUpperCase());

		// the entered team is a new team
		if (team == null) {

			// checking if the entered team number is a number
			if (!teamNumberField.getText().matches("[0-9]+")) { // team No. value is not a number
				lblMessage.setText("Team No. value Should Be a Number");
				return;
			}

			// now we can create a team object because its data is valid
			team = new Team(teamList.getSelectionModel().getSelectedItem(), teamNumberField.getText());

			// if rank value is entered (optional)
			if (!rankField.getText().isBlank()) {
				if (rankField.getText().equals("-")) {
					team.setRank("-"); 
				} else if (!rankField.getText().matches("[0-9]+")) { 
					lblMessage.setText("Rank Value Should Be a Number (If it's empty Enter '-'");
					return;
				} else { // the rank is a number
					team.setRank(rankField.getText());
				}
			} else {
				team.setRank("-");
			}
		}

		// adding student to team and competition
		CompetitionManager.addStudentToComp(competition, student, team);

		// clear the field so the user can add another student
		majorField.clear();
		idField.clear();
		teamNumberField.clear();
		rankField.clear();
		studentNameField.clear();
		teamList.valueProperty().set(null);

		lblMessage.setText("Student " + student.getName() + " has been added to the competition");

		// updating the teams list
		teamList.getItems().setAll(competition.getAllTeamNames()); // updating the team selection box

	}

	@FXML // this method will autofill the team number and rank field, when the user choose or type a team
	void autoFill() {
		// finding the selected team index
		int selectedTeamIndex = teamList.getSelectionModel().getSelectedIndex();

		if (selectedTeamIndex != -1) { // entered team exists
			Team selectedTeam = competition.getTeams().get(selectedTeamIndex);
			// autofilling
			teamNumberField.setText(selectedTeam.getTeamNumber() + "");
			teamNumberField.setDisable(true);
			rankField.setText(selectedTeam.getRank() + "");
			rankField.setDisable(true);

			team = selectedTeam;
		} else { // entered a new team
			teamNumberField.setDisable(false);
			rankField.setDisable(false);

			team = null;
		}
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