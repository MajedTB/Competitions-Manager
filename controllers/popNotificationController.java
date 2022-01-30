package controllers;

import java.io.IOException;

import code.Competition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class popNotificationController {

	@FXML
	private Button btClose;

	@FXML
	private Label lblMessage;

	public void popNotification(Competition comp) {
		// Edit label message
		lblMessage.setText("Competition " + comp.getName() + " is due, please update the ranks");
	}

	@FXML
	void close(ActionEvent event) throws IOException {
		// Close window
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

}
