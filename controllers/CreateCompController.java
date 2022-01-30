package controllers;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import code.Competition;
import code.CompetitionManager;
import code.SoloCompetition;
import code.TeamCompetition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class CreateCompController {
    // these 3 objects are needed to load a new interface
    private Stage stage;
    private Scene scene;
    private Parent root;

    // some elements of the interface
    @FXML
    private Label lblError;
    @FXML
    private Button btSave;
    @FXML
    private DatePicker compDate;
    @FXML
    private TextField compName;
    @FXML
    private TextField compWebsite;
    @FXML
    private ToggleGroup type;

    Competition competition;

    @FXML // this is linked to the save button
    void saveCompetitionInfo(ActionEvent event) throws IOException {
        // most of the code is for validation

        // checking that all fields are not empty
        if (compName.getText().isBlank() || compWebsite.getText().isBlank() || compDate.getValue() == null) {
            lblError.setText("Please Enter All Information");
            return;
        }
        
        if (!(compWebsite.getText().startsWith("https://") || compWebsite.getText().startsWith("http://"))) {
        	lblError.setText("Website Should Starts with https:// or http://");
        	return;
        }

        // Convert from (LocalDate) to (Date)
        Instant instant = Instant.from(compDate.getValue().atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);

        // creating a competition based on the selected type
        if (((RadioButton) type.getSelectedToggle()).getText().equals("Solo-based"))
            competition = new SoloCompetition(compName.getText(), compWebsite.getText(), date);
        else
            competition = new TeamCompetition(compName.getText(), compWebsite.getText(), date);

        // adding the competition to the array and excel
        CompetitionManager.addCompetition(competition);

        // load the "promptToAddStudents" interface
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/promptToAddStudents.fxml"));
        root = loader.load();

        // the 2 lines below will send the competition object to another controller class
        promptAddCompController controller = loader.getController();
        controller.setCompetition(competition); // pass competition to the other scene

        // go to the loaded interface
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML // this is linked to the "back to main menu" button
    void backToMainMenu(ActionEvent event) throws IOException {
        // the 5 lines below will load the main menu interface
        Parent root = FXMLLoader.load(getClass().getResource("/interfaces/mainMenu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}