package controller;

import model.EmotionRecognizer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import model.JDBC;

/**
 * @author Neeko
 *
 */
public class ConfirmationController implements Initializable {
	@FXML
	private Label lbResultText;

	@FXML
	private ImageView ivPhoto;

	@FXML
	private Label lbName;

	@FXML
	private Label lbEmotion;

	@FXML
	private Label lbReasonofVisit;

	@FXML
	private MenuButton mbReasonofVisit;

	@FXML
	private MenuItem one;

	@FXML
	private MenuItem two;

	@FXML
	private MenuItem three;

	@FXML
	private MenuItem four;

	@FXML
	private MenuItem five;

	@FXML
	private MenuItem six;

	@FXML
	private MenuItem seven;

	@FXML
	private MenuItem eight;

	@FXML
	private MenuItem nine;

	@FXML
	private MenuItem ten;

	@FXML
	private Button btnOperation;

	private int reason;

	public ConfirmationController() {
	}

	public void recognitionConfirmation() throws IOException, Exception {

		// Condition 1 : The visitor's profile doesn't exist. Ask user to create a new
		// record of the visitor.

		if (LoginController.student == null) {
			lbResultText.setText("The visitor's profile doesn't exist.");

			// Set the photo.
			Image image = new Image("images/blank.png");
			ivPhoto.setImage(image);

			lbName.setText("Unknown");

			// Hide the setting of visiting reason.
			lbReasonofVisit.setVisible(false);
			mbReasonofVisit.setVisible(false);

			// EMOTION DETECTION
			lbEmotion.setText(EmotionRecognizer.detectEmotion(LoginController.FACE_FILE));

			btnOperation.setText("Create a record");

		}
		// Condition 2 : The visitor is a student in the database. Ask user to confirm
		// the information of the visitor and open the dashboard.
		else {

			lbResultText.setText("The visitor is:");

			// SET PHOTO
			System.out.println("/images/" + LoginController.student.getPhoto());
			System.out.println(LoginController.student.getName());
			Image image = new Image("/images/" + LoginController.student.getPhoto());
			ivPhoto.setImage(image);

			lbName.setText(LoginController.student.getName());

			// EMOTION DETECTION
			lbEmotion.setText(EmotionRecognizer.detectEmotion(LoginController.FACE_FILE));

			btnOperation.setText("Go to Dashboard");
		}
	}

	/**
	 * Handles the actions for different events.
	 * 
	 * @param actionEvent
	 * @throws IOException
	 */
	public void handleClicks(ActionEvent actionEvent) throws IOException {

		if (actionEvent.getSource() == one) {
			reason = 1;
			mbReasonofVisit.setText("Ask for stapler.");

		} else if (actionEvent.getSource() == two) {
			reason = 2;
			mbReasonofVisit.setText("Hand in or collect assignment.");

		} else if (actionEvent.getSource() == three) {
			reason = 3;
			mbReasonofVisit.setText("Meet a faculty.");

		} else if (actionEvent.getSource() == four) {
			reason = 4;
			mbReasonofVisit.setText("Request for a locker.");

		} else if (actionEvent.getSource() == five) {
			reason = 5;
			mbReasonofVisit.setText("Chat with Kimberly.");

		} else if (actionEvent.getSource() == six) {
			reason = 6;
			mbReasonofVisit.setText("Complaints.");

		} else if (actionEvent.getSource() == seven) {
			reason = 7;
			mbReasonofVisit.setText("Ask questions (finance).");

		} else if (actionEvent.getSource() == eight) {
			reason = 8;
			mbReasonofVisit.setText("Ask questions (IT).");

		} else if (actionEvent.getSource() == nine) {
			reason = 9;
			mbReasonofVisit.setText("Ask questions (marketing).");

		} else if (actionEvent.getSource() == ten) {
			reason = 10;
			mbReasonofVisit.setText("Ask questions (career services).");

		}

		if (actionEvent.getSource() == btnOperation) {

			if (LoginController.student == null) {
				System.out.println("About to launch NewUser.fxml");

				// SHOW NEW USER DETAILS FORM
				LoginController.newUserStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/view/NewUser.fxml"));
				LoginController.newUserStage.setTitle("Create a Record");
				LoginController.newUserStage.setScene(new Scene(root));
				LoginController.confirmationStage.hide();
				LoginController.newUserStage.show();

			} else {

				// SAVE NEW VISIT RECORD
				JDBC.InsertRecord(LoginController.student.getStudentID(), reason);

				// SHOW DASHBOARD
				Stage dashboardStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
				dashboardStage.setTitle("Visitor Dashboard");
				dashboardStage.setScene(new Scene(root));
				new DashboardController();
				LoginController.confirmationStage.hide();
				dashboardStage.show();
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			recognitionConfirmation();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
