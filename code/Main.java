package code;

import java.util.Date;

import controllers.popNotificationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Display another window for notification if popNotification for any competition == true
		// we need to use the method for every competition in the CompetitionManager
		for (Competition c: CompetitionManager.getCompetitions()) {
			boolean s = popNotification(c);
			if (s) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/notificationPop.fxml"));
				Parent root1 = loader.load();
				popNotificationController controller = loader.getController();
				controller.popNotification(c);
				Scene scene1 = new Scene(root1);
				Stage popStage = new Stage();
				popStage.initModality(Modality.APPLICATION_MODAL);
				popStage.setScene(scene1);
				popStage.showAndWait();
			}
		}

		// the 5 lines below will load the interface of the main menu
		// the main menu interface is linked to MainController.class
		Parent root = FXMLLoader.load(getClass().getResource("/interfaces/mainMenu.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Competitions Organaizer");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		// loading the excel file
		CompetitionManager.setup();

		// START OF THE PROGRAM
		launch(args);
	}

	private boolean popNotification(Competition competition) {
		String rank = "";
		if (competition instanceof SoloCompetition) {
			for (Student st : ((SoloCompetition) competition).getStudents()) {
				rank = st.getRank();
				if (rank.equals("-"))
					break;
			}
		} else {
			for (Team team : ((TeamCompetition) competition).getTeams()) {
				rank = team.getRank() + "";
				if (rank.equals("-"))
					break;
			}
		}
		return rank.equals("-") && competition.getDate().compareTo(new Date()) <= 0;
	}

}