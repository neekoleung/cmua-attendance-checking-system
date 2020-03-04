package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import model.WebCam;

/**
 * @author Neeko
 *
 */
public class VerificationController implements Initializable{

	@FXML
	private ImageView imgSnapshot;
	
	/**
	 * Triggers a snapshot capture from the web camera.
	 */
	private void takePicture() {

		WritableImage writableImage = WebCam.capureSnapShot();
		imgSnapshot.setImage(writableImage);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		takePicture();
	}

}
