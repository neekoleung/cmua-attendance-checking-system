package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * The facial recognition engine that uses OpenCV's LBPH algorithm library.
 * 
 * @author Peter
 *
 */
public class FaceRecognizerLBPH {

	private static final double CONFIDENCE_THRESHOLD = 40;
	
	ArrayList<Mat> images = new ArrayList<>();
	List<String> names = new ArrayList<>();
	List<Integer> labels = new ArrayList<>();
	FaceRecognizer faceRecognizer;

	public FaceRecognizerLBPH() throws IOException {
		// Load the OpenCV core library
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		this.train();
	}

	/**
	 * Trains the AI with images at the 'training' folder.
	 * 
	 * @throws IOException
	 */
	private void train() throws IOException {

		Path path = Paths.get("training"); // the folder where training images are

		Files.list(path).forEach(file -> {
			String filename = file.getFileName().toString();

			if (filename.contains("-") && filename.endsWith("png")) {
				String name = filename.substring(0, filename.indexOf("-")); // e.g. edd from edd-1.jpg
				if (!names.contains(name)) {
					names.add(name);
				}

				Mat image = Imgcodecs.imread(file.toString());
				Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY); // Convert image to grayscale or it won't work
																		// with the face learner

				images.add(image);
				labels.add(names.indexOf(name));
			}
		});

		faceRecognizer = LBPHFaceRecognizer.create();

		MatOfInt labelsMat = new MatOfInt();
		labelsMat.fromList(labels);
		faceRecognizer.train(images, labelsMat);

		System.out.println();
	}

	/**
	 * Checks if face is among the known faces.
	 * 
	 * @param face
	 * @return studentID if the face is recognized according to
	 *         CONFIDENCE_THRESHOLD, -1 otherwise.
	 */
	public int recognizeFace(Mat face) {

		int[] outLabel = new int[1];
		double[] outConf = new double[1];

		Imgproc.cvtColor(face, face, Imgproc.COLOR_BGR2GRAY); // Convert image to grayscale or it won't work
		faceRecognizer.predict(face, outLabel, outConf);

		int studentID = Integer.valueOf(names.get(outLabel[0]));
		double confidenceValue = 100 - outConf[0];

		System.out.println("Confidence value: " + String.format("%.2f", confidenceValue) + "%");

		if (confidenceValue > CONFIDENCE_THRESHOLD) {
			System.out.println("This is student with studentID " + studentID + ".");
			return studentID;
		} else {
			System.out.println("No records for this person.");
			return -1;
		}
	}
}
