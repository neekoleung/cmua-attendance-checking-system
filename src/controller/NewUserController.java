package controller;

import model.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * @author Neeko
 *
 */
public class NewUserController implements Initializable {
	@FXML
	private TextField andrewID;

	@FXML
	private TextField studentID;

	@FXML
	private TextField name;

	@FXML
	private TextField age;

	@FXML
	private TextField nationality;

	@FXML
	private TextField program;

	@FXML
	private TextField startdate;

	@FXML
	private MenuButton gender;

	@FXML
	private MenuItem male;

	@FXML
	private MenuItem female;

	@FXML
	private Button btnConfirm;

	/**
	 * @param actionEvent
	 * @throws IOException
	 */
	public void handleClicks(ActionEvent actionEvent) throws IOException {

		if (actionEvent.getSource() == male) {
			gender.setText("Male");
		} else if (actionEvent.getSource() == female) {
			gender.setText("Female");
		} else if (actionEvent.getSource() == btnConfirm) {
			// Create a pattern of pure number tester.
			Pattern pattern = Pattern.compile("[0-9]*");

			// Rules of input: 1. Should not be null; 2.StudentID and age should be numbers.
			if (andrewID.getText().equals("") || studentID.getText().equals("") || name.getText().equals("")
					|| age.getText().equals("") || nationality.getText().equals("") || program.getText().equals("")
					|| startdate.getText().equals("") || gender.getText().equals("Please Select")
					|| !pattern.matcher(age.getText()).matches()) {
				// Ask use to reenter the data if it is invalid. (error handling)
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Alert");
				alert.setHeaderText("Some of the inputs are Invalid. Please check again.");
				alert.setContentText(
						"Note: \n Each field should not be empty.\n Field \"StudentID\" and \"age\" should be Integers.");
				// Show the alert window and stop user from operating other stages before close
				// it.
				alert.showAndWait();
			} else {
				// rename FACE_FILE to
				String newFileName = "std" + studentID.getText() + ".png";
				File oldFile = new File(LoginController.FACE_FILE);
				File newFile = new File(newFileName);
				oldFile.renameTo(newFile);

				String photo = newFileName;
				String genderChar = (gender.getText().equals("Male")) ? "M" : "F";
				JDBC.InsertStudent(Integer.parseInt(studentID.getText()), name.getText(), andrewID.getText(),
						genderChar, Integer.parseInt(age.getText()), program.getText(), nationality.getText(),
						startdate.getText(), photo);

				LoginController.newUserStage.hide();

				// Show the window of successful information.
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Congratulations");
				alert.setHeaderText("The record is created in database successfully!");
				alert.showAndWait();
			}
		}
	}

	/**
	 * Clear the data in the input area.
	 */
	public void resetData() {
		andrewID.setText("");
		studentID.setText("");
		name.setText("");
		age.setText("");
		nationality.setText("");
		program.setText("");
		startdate.setText("");
		gender.setText("Please Select");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
