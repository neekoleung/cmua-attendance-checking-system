package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This application simulates a biometric user identification and information
 * system in JAVA. administrators. The system can detect and recognise faces
 * using a web camera and automatically open a dashboard of information for a
 * CMUA student.
 * 
 * Use case: 1. A CMUA student visits the reception and faces the camera. 2. The
 * system identifies the student and displays a dashboard of information about
 * the student to the receptionist. 3. If the system cannot identify the person
 * then the system alerts the reception.
 * 
 * @author Neeko
 *
 */
public class MainController extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		// LAUNCH THE MAIN ADMIN WINDOW
		new LoginController();
		Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
