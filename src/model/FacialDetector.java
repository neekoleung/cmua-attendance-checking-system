package model;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;

/**
 * The facial detection engine based on OpenCV. It can detect faces in a image
 * and then saves them in the local file system.
 * 
 * @author Neeko
 *
 */

public class FacialDetector {

	static String haarcascadeXML = "C:\\Users\\pafranci\\Documents\\opencv-4.1.2\\build\\etc\\haarcascades\\haarcascade_frontalface_alt.xml";

	/**
	 * Convert a BufferedImage into Mat.
	 * 
	 * @param image
	 * @return
	 */
	private static Mat convertBufImg2Mat(BufferedImage image) {
		byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
		mat.put(0, 0, data);
		return mat;
	}

	/**
	 * Control the whole process of facial detection.
	 * 
	 * @param image
	 * @return
	 * @throws IOException
	 */
	public static Mat detectFace(BufferedImage image) throws IOException {

		CascadeClassifier faceDetector = new CascadeClassifier(haarcascadeXML);
		if (!faceDetector.load(haarcascadeXML)) {
			System.out.println("Error: can't load " + haarcascadeXML);
			return null;
		}

		Mat matImage = convertBufImg2Mat(image);
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(matImage, faceDetections);

		boolean oneFaceDetected = faceDetections.toArray().length == 1;
		if (oneFaceDetected) {
			System.out.println("Detected " + String.format("%d face(s)", faceDetections.toArray().length));

			if (faceDetections.toArray().length > 0) {
				// Write face(s)
				String newTrainingFileName = "";
				for (Rect rect : faceDetections.toArray()) {
					newTrainingFileName = "_-" + rect.x;
					writeFrame(newTrainingFileName, matImage, rect);
					writeFrame("face", matImage, rect);
				}
			}
		} else {
			System.out.println("No face detected. Please try again.");
			return null;
		}
		return matImage;
	}

	/**
	 * Extract the face part in the image and crop it as a BufferedImage.
	 * 
	 * @param src
	 * @param rect
	 * @return the cropped image
	 */
	private static BufferedImage cropImage(BufferedImage src, Rect rect) {
		BufferedImage dest = src.getSubimage(rect.x, rect.y, rect.width, rect.height);
		return dest;
	}

	/**
	 * Saves the data into the local system.
	 * 
	 * @param filename
	 * @param mat
	 * @param rect
	 */
	public static void writeFrame(String filename, Mat mat, Rect rect) {
		byte[] data = new byte[mat.rows() * mat.cols() * (int) (mat.elemSize())];
		mat.get(0, 0, data);
		if (mat.channels() == 3) {
			for (int i = 0; i < data.length; i += 3) {
				byte temp = data[i];
				data[i] = data[i + 2];
				data[i + 2] = temp;
			}
		}
		BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), BufferedImage.TYPE_3BYTE_BGR);
		image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);
		BufferedImage frame = cropImage(image, rect);
		try {
			ImageIO.write(frame, "png", new File(filename + ".png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}