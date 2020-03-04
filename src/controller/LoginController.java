package controller;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.FacialDetector;
import model.JDBC;
import model.Student;
import model.FaceRecognizerLBPH;

/**
 * @author Neeko, Peter
 *
 */
public class LoginController implements Initializable{

	static FaceRecognizerLBPH faceRecognizer;
	
	public static Student student;
	public static String FACE_FILE = "face.png";
	public static Stage cameraStage;
	public static Stage confirmationStage;
	public static Stage newUserStage;

	@FXML
	private Button adminbtn;

	@FXML
	private Button checkinbtn;

	/**
	 * Takes a picture and checks if the face is recognized.
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	private void openCheckin(MouseEvent event) throws Exception {

		LoginController.student = null;
		
		// TAKE PICTURE - will be saved to snapshot.png
		new VerificationController();
		Parent root = FXMLLoader.load(getClass().getResource("/view/Verification.fxml"));
		cameraStage = new Stage();
		cameraStage.setTitle("Validating...");
		cameraStage.setScene(new Scene(root));
		cameraStage.show();

		// RETRIEVE TAKEN PICTURE
		File rawImageFile = new File("snapshot.png");
		BufferedImage testImage = ImageIO.read(rawImageFile);

		// FACIAL DETECTION - will be saved to face.png
		Mat face = FacialDetector.detectFace(testImage);

		if (face != null) {
			// FACIAL RECOGNITION
			BufferedImage faceImage = ImageIO.read(new File(FACE_FILE));
			int studentID = faceRecognizer.recognizeFace(convertBufImg2Mat(faceImage));

			if (studentID > 0) {
				student = JDBC.StudentInfo(studentID);
			}
			new ConfirmationController();
			confirmationStage = new Stage();
			Parent root2 = FXMLLoader.load(getClass().getResource("/view/Confirmation.fxml"));
			confirmationStage.setTitle("Confirmation");
			confirmationStage.setScene(new Scene(root2));
			cameraStage.hide();
			confirmationStage.show();
			
		} else {
			// Show the window of successful information.
			cameraStage.hide();
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("No face detected");
			alert.setHeaderText("Please try again");
			alert.showAndWait();

		}
	}

	/**
	 * Shows the reports: a) Bar chart of categories, b) Pie charts of categories by
	 * gender
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void openAdmin(MouseEvent event) throws IOException {
		Stage tableReportStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/view/TableReport.fxml"));
		tableReportStage.setScene(new Scene(root));
		tableReportStage.show();

		Stage pieReportStage = new Stage();
		Parent root2 = FXMLLoader.load(getClass().getResource("/view/PieReport.fxml"));
		pieReportStage.setScene(new Scene(root2));
		pieReportStage.show();
	}

	private static Mat convertBufImg2Mat(BufferedImage image) {
		byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
		mat.put(0, 0, data);
		return mat;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			faceRecognizer = new FaceRecognizerLBPH();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}