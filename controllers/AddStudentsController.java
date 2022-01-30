package controllers;

import java.io.IOException;
import code.Competition;
import code.CompetitionManager;
import code.SoloCompetition;
import code.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddStudentsController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField idField;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblMessage;
    @FXML
    private TextField majorField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField rankField;
    
    private SoloCompetition competition;

    // this will store the competition object into the class, and modify the title
    void setupScene(Competition competition) {
        this.competition = (SoloCompetition) competition;
        lblTitle.setText("Adding a Student to " + competition.getName());
    }

    @FXML // this is linked to the Add button
    void addCompetition(ActionEvent event) {
        Student student;

        // checking if the All fields are entered (except rank)
        if (nameField.getText().isBlank() || idField.getText().isBlank() || majorField.getText().isBlank()) {
            lblMessage.setText("Please Enter All Information");
            return;
        }

        // Checking if the enter ID is a number
        if (!idField.getText().matches("[0-9]+")) { // ID is not a number
            lblMessage.setText("ID Value Should Contain Numbers Only");
            return;
        }

        // now we can create student object because all fields are valid
        student = new Student(nameField.getText(), idField.getText(), majorField.getText().toUpperCase());

        // if the user enter a value for the rank (optional)
        if (!rankField.getText().isBlank()) {
            if (rankField.getText().matches("[0-9]+")) // if the rank in a number
                student.setRank(rankField.getText());
            else if(rankField.getText().equals("-")) {
                student.setRank("-");
            }
            else {// the rank is not a number
                lblMessage.setText("Rank Value Should Be a Number");
                return;
            }
        }
        else {
            student.setRank("-");
        }

        // now all fields are valid

        // clear the field so the user can add another student
        nameField.clear();
        idField.clear();
        majorField.clear();
        rankField.clear();
        lblMessage.setText("Student \"" + student.getName() + "\" has been added to the competition");
        
        // the student will be added to the competition
        CompetitionManager.addStudentToComp(competition, student);
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