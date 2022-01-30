package controllers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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

public class SendEmailSoloController {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private Button btSend;
	@FXML
	private Label lblCompName;
	@FXML
	private Label lblDate;
	@FXML
	private Label lblWebsite;
	@FXML
	private Label lblMessage;

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
	Student selectedStudent;
	ObservableList<Student> studentsList;

	public void setupScene(Competition comp) {
		// setting up texts for the competition information
		this.competition = (SoloCompetition) comp;
		lblCompName.setText(competition.getName() + " (Solo)");
		lblWebsite.setText(competition.getLink());

		String dateString = new SimpleDateFormat("d-MMM-yyyy").format(competition.getDate());
		lblDate.setText("Due date: " + dateString);

		// specifying data fields for each column
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
		rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

		// adding students to the table
		studentsList = FXCollections.observableArrayList(competition.getStudents());
		studentsTable.setItems(studentsList);

		// saving the selected student from the table
		studentsTable.getSelectionModel().selectedItemProperty().addListener(e -> {
			selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
			btSend.setDisable(false);
		});
	}

	@FXML
	void sendEmail(ActionEvent event) throws IOException {
		if (selectedStudent.getRank().equals("-")) {
			lblMessage.setText("Cannot Send an email if student does not have a rank");
			return;
		}
		
		Desktop desktop;
		String subject;

		String rank = selectedStudent.getRank();
		String name = selectedStudent.getName().replace(" ", "%20");
		String competitionName = competition.getName().replace(" ", "%20");
		String email = "s" + selectedStudent.getId().trim() + "@kfupm.edu.sa";

		// converting message content to the proper form
		String messageContent = "Dear " + name + ",\r\n" + "\r\n" + "Conguratulation on your achievement in "
				+ competitionName
				+ ". This achievement is deeply appreciated by the unversity and we will announce it in the approbrite medias.\r\n"
				+ "\r\n"
				+ "In case you have Photos you want to share with the news post, reply to this email with the photos.\r\n"
				+ "\r\n" + "Regards and Congrats,\r\n" + "KFUPM News Team";
		messageContent = messageContent.replaceAll("\r", "%0D").replaceAll("\n", "%0A").replaceAll(" ", "%20");


		// creating the subject based on the rank.
		subject = "Congratulation%20on%20achieving%20" + rank + "*%20place%20in%20" + competitionName;
		
		if (rank.equals("3")) {
			subject = subject.replace("*", "rd");
		} else if (rank.equals("2")) {
			subject = subject.replace("*", "nd");
		} else if (rank.equals("1")) {
			subject = subject.replace("*", "st");
		} else {
			subject = subject.replace("*", "th");
		}
		
		// to check if the computer support mailto.
		if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
			URI mailto;
			try {
				// writing the email in the required form to use in URI.
				String message = "mailto:" + email + "?subject=" + subject + "&body=" + messageContent;
				// Creating URIObject.
				mailto = new URI(message);
				// sending the email by using mail.
				desktop.mail(mailto);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

		} else {
			throw new RuntimeException("desktop doesn't support mailto");
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