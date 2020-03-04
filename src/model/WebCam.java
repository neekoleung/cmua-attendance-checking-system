package model;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

/**
 * A utility class for taking a picture from the camera and saving it to
 * snapshot.png
 * 
 * @author Neeko
 *
 */

public class WebCam {

	/**
	 * Capture the snapshot from the camera.
	 * 
	 * @return a writableImage
	 */
	public static WritableImage capureSnapShot() {
		WritableImage WritableImage = null;

		// Loading the OpenCV core library
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		// Instantiating the VideoCapture class (camera:: 0)
		VideoCapture capture = new VideoCapture(0);

		// Reading the next video frame from the camera
		Mat matrix = new Mat();
		capture.read(matrix); //just to open the camera
		capture.read(matrix); //just to open the camera
		
		// If camera is open
		if (capture.isOpened()) {
			
			// If there is next video frame
			if (capture.read(matrix)) {
				
				// Creating BuffredImage from the matrix
				BufferedImage image = new BufferedImage(matrix.width(), matrix.height(), BufferedImage.TYPE_3BYTE_BGR);

				WritableRaster raster = image.getRaster();
				DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
				byte[] data = dataBuffer.getData();
				matrix.get(0, 0, data);

				// Creating the Writable Image
				WritableImage = SwingFXUtils.toFXImage(image, null);
			}
		}
		saveImage(matrix);
		return WritableImage;
	}

	/**
	 * Save the image that captured into the system.
	 */
	private static void saveImage(Mat matrix) {

		Imgcodecs imageCodecs = new Imgcodecs();
		Imgcodecs.imwrite("snapshot.png", matrix);
	}
}
