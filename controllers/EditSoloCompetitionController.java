package controllers;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import code.Competition;
import code.CompetitionManager;
import code.SoloCompetition;
import code.Student;
import code.Team;
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

public class EditSoloCompetitionController {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private TextField nameField;
	@FXML
	private TextField websiteField;
	@FXML
	private TableColumn<Team, String> idColumn;
	@FXML
	private TableColumn<Team, String> majorColumn;
	@FXML
	private TableColumn<Team, String> nameColumn;
	@FXML
	private TableColumn<Team, String> rankColumn;
	@FXML
	private TableView<Student> studentsTable;
	@FXML
	private Button btSave;
	@FXML
	private Label lblMessage;
	@FXML
	private DatePicker dateField;

	SoloCompetition competition;
	SoloCompetition oldComp;
	ObservableList<Student> studentsList;

	public void setupScene(Competition comp) {
		this.oldComp = (SoloCompetition) comp;
		this.competition = ((SoloCompetition) comp).clone();

		// inserting the old values into the text fields
		nameField.setText(competition.getName());
		websiteField.setText(competition.getLink());
		LocalDate date = competition.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		dateField.setValue(date);

		// specifying data fields for each column (students table)
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
		rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

		// adding students to table
		studentsList = FXCollections.observableArrayList(competition.getStudents());
		studentsTable.setItems(studentsList);

		// allowing the columns to be edited
		idColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		majorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		rankColumn.setCellFactory(TextFieldTableCell.forTableColumn());

	}

	@FXML
	void saveChanges(ActionEvent event) throws IOException {
		// checking if fields are empty or not
		if (nameField.getText().toString().isBlank() || websiteField.getText().toString().isBlank()) {
			lblMessage.setText("Competition Information Cannot be Empty");
		} else {
			competition.setName(nameField.getText());
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
	void editId(CellEditEvent event) {
		String newId = event.getNewValue().toString();
		// checking if id consist of numbers only
		if (newId.matches("[0-9]+")) {  // id contains numbers only
			studentsTable.getSelectionModel().getSelectedItem().setId(newId);
			btSave.setDisable(false);
		} else { 
			lblMessage.setText("Only Numbers Are Permitted in the ID Slot");
			btSave.setDisable(true);
		}
	}

	@FXML
	void editMajor(CellEditEvent event) {
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
	void editName(CellEditEvent event) {
		// check if the field is empty or not
		if (!event.getNewValue().toString().isBlank()) {
			studentsTable.getSelectionModel().getSelectedItem().setName(event.getNewValue().toString());
			btSave.setDisable(false);
		} else {
			lblMessage.setText("One of the Name Fields is Empty");
			btSave.setDisable(true);
		}
	}

	@FXML
	void editRank(CellEditEvent event) {
		// checking if rank consist of numbers only or (-) which means no rank
		String newRank = event.getNewValue().toString();
		if (newRank.matches("[0-9]+") || newRank.equals("-")) { 
			studentsTable.getSelectionModel().getSelectedItem().setRank(newRank);
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