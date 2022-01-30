package controllers;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import code.Competition;
import code.CompetitionManager;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class EditTeamCompetitionController {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private Button btSave;
	@FXML
	private Label lblMessage;

	@FXML
	private TextField compNameField;
	@FXML
	private DatePicker dateField;
	@FXML
	private TextField websiteField;

	@FXML
	private TableColumn<Student, String> idColumn;
	@FXML
	private TableColumn<Student, String> majorColumn;
	@FXML
	private TableColumn<Student, String> studentNameColumn;
	@FXML
	private TableView<Student> studentsTable;
	ObservableList<Student> studentsList;

	@FXML
	private TableColumn<Team, String> teamNameColumn;
	@FXML
	private TableColumn<Team, String> teamNumberColumn;
	@FXML
	private TableColumn<Team, String> teamRankColumn;
	@FXML
	private TableView<Team> teamsTable;
	ObservableList<Team> teamsList;
	
	TeamCompetition competition;
	TeamCompetition oldComp;
	
	public void setupScene(Competition comp) {
		this.oldComp = (TeamCompetition) comp;
		this.competition = ((TeamCompetition) comp).clone();

		// inserting the old values into the text fields
		compNameField.setText(competition.getName());
		websiteField.setText(competition.getLink());
		LocalDate date = competition.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		dateField.setValue(date);

		// specifying data fields for each column (students table)
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));

		// specifying data fields for each column (teams table)
		teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("teamName"));
		teamNumberColumn.setCellValueFactory(new PropertyValueFactory<>("teamNumber")); //
		teamRankColumn.setCellValueFactory(new PropertyValueFactory<>("rank")); //

		// adding teams to the table
		teamsList = FXCollections.observableArrayList(competition.getTeams());
		teamsTable.setItems(teamsList);

		// adding students to the second table when a team is selected
		teamsTable.getSelectionModel().selectedItemProperty().addListener(e -> {
			studentsList = FXCollections
					.observableArrayList(teamsTable.getSelectionModel().getSelectedItem().getStudents());
			studentsTable.setItems(studentsList);
			System.out.println(teamsTable.getSelectionModel().getSelectedItem().getStudents().get(0).getId());
		});

		// allowing the table to be edited
		idColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		studentNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		majorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		teamNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		teamNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		teamRankColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	}

	@FXML
	void saveChanges(ActionEvent event) {
		// checking if fields are empty or not
		if (compNameField.getText().toString().isBlank() || websiteField.getText().toString().isBlank()) {
			lblMessage.setText("Competition Information Cannot be Empty");
		} else {
			competition.setName(compNameField.getText());
			competition.setLink(websiteField.getText());
			Instant instant = Instant.from(dateField.getValue().atStartOfDay(ZoneId.systemDefault()));
			Date date = Date.from(instant);
			competition.setDate(date);

			CompetitionManager.updateCompetition(oldComp, competition);
			oldComp = competition;
			lblMessage.setText("Changes Saved");
		}
	}

	@FXML
	void editStudentId(CellEditEvent event) {
		// checking if id consist of numbers only
		String newId = event.getNewValue().toString();
		if (newId.matches("[0-9]+")) {
			studentsTable.getSelectionModel().getSelectedItem().setId(newId);
			btSave.setDisable(false);
		} else {
			lblMessage.setText("Only Numbers Are Permitted in the ID Slot");
			btSave.setDisable(true);
		}
	}

	@FXML
	void editStudentMajor(CellEditEvent event) {
		// check if the field is empty or not
		if (!event.getNewValue().toString().isBlank()) {
			studentsTable.getSelectionModel().getSelectedItem().setMajor(event.getNewValue().toString());
			btSave.setDisable(false);
		} else {
			lblMessage.setText("One of the Major Fields is Empty");
			btSave.setDisable(true);
		}
	}

	@FXML
	void editStudentName(CellEditEvent event) {
		// check if the field is empty or not
		if (!event.getNewValue().toString().isBlank()) {
			studentsTable.getSelectionModel().getSelectedItem().setName(event.getNewValue().toString());
			btSave.setDisable(false);
		} else {
			lblMessage.setText("One of the Major Fields is Empty");
			btSave.setDisable(true);
		}
	}

	@FXML
	void editTeamName(CellEditEvent event) {
		// check if the field is empty or not
		if (!event.getNewValue().toString().isBlank()) {
			teamsTable.getSelectionModel().getSelectedItem().setTeamName(event.getNewValue().toString());
		} else {
			lblMessage.setText("One of the Major Fields is Empty");
			btSave.setDisable(true);
		}
	}

	@FXML
	void editTeamNumber(CellEditEvent event) {
		// checking if the field consist of numbers only
		if (event.getNewValue().toString().matches("[0-9]+")) {
			teamsTable.getSelectionModel().getSelectedItem().setTeamNumber(event.getNewValue().toString());
			btSave.setDisable(false);
		} else {
			lblMessage.setText("Only Numbers Are Permitted in the ID Slot");
			btSave.setDisable(true);
		}
	}

	@FXML
	void editTeamRank(CellEditEvent event) {
		// checking if rank consist of numbers only or (-) which means no rank
		String newRank = event.getNewValue().toString();
		if (newRank.matches("[0-9]+") || newRank.equals("-")) {
			teamsTable.getSelectionModel().getSelectedItem().setRank(event.getNewValue().toString());
			btSave.setDisable(false);
		} else {
			lblMessage.setText("Only Numbers Are Permitted in the Rank Slot (If There is no rank please enter '-')");
			btSave.setDisable(true);
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
