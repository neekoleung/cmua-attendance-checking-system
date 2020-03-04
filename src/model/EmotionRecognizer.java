package model;

import java.io.*;
import java.util.*;
import com.google.cloud.vision.v1.*;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.protobuf.ByteString;

/**
 * The facial emotion detecion engine. This is based on Google Cloud Vision -
 * https://cloud.google.com/vision/docs/detecting-faces
 * 
 * @author Wendy
 *
 */
public class EmotionRecognizer {

	static Likelihood happy;
	static Likelihood shock;
	static Likelihood annoyed;
	static Likelihood sad;

	static int sadP;
	static int happyP;
	static int shockP;
	static int annoyedP;

	/**
	 * Tries to determine the emotion of the face image at filePath. Four emotions
	 * are detected: happy, sad, suprised, and angry. This is based on Google Cloud
	 * Vision.
	 * 
	 * @param filePath
	 * @return the most probable emotion detected in the face image at filePath
	 * @throws Exception
	 * @throws IOException
	 */
	public static String detectEmotion(String filePath) throws Exception, IOException {

		List<AnnotateImageRequest> requests = new ArrayList<>();

		ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

		Image img = Image.newBuilder().setContent(imgBytes).build();
		Feature feat = Feature.newBuilder().setType(Type.FACE_DETECTION).build();
		AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
		requests.add(request);

		String likelyEmotion = "";

		try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {

			BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
			List<AnnotateImageResponse> responses = response.getResponsesList();

			for (AnnotateImageResponse res : responses) {
				if (res.hasError()) {
					System.out.println(res.getError().getMessage());
					return "";
				}

				// For full list of available annotations, see http://g.co/cloud/vision/docs

				for (FaceAnnotation annotation : res.getFaceAnnotationsList()) {

					annoyed = annotation.getAngerLikelihood();
					happy = annotation.getJoyLikelihood();
					shock = annotation.getSurpriseLikelihood();
					sad = annotation.getSorrowLikelihood();

					annoyedP = annotation.getAngerLikelihoodValue();
					happyP = annotation.getJoyLikelihoodValue();
					shockP = annotation.getSurpriseLikelihoodValue();
					sadP = annotation.getSorrowLikelihoodValue();

					if (annoyedP > sadP && annoyedP > happyP && annoyedP > shockP) {
						likelyEmotion = "Angry";
					} else if (sadP > annoyedP && sadP > happyP && sadP > shockP) {
						likelyEmotion = "Sad";
					} else if (happyP > sadP && happyP > annoyedP && happyP > shockP) {
						likelyEmotion = "Happy";
					} else if (shockP > sadP && shockP > happyP && shockP > annoyedP) {
						likelyEmotion = "Surprised";
					} else {
						likelyEmotion = "Ambiguous";
					}

					System.out.println("Annoyed: " + annotation.getAngerLikelihood() + "\n" + "Happy: "
							+ annotation.getJoyLikelihood() + "\n" + "Shocked: " + annotation.getSurpriseLikelihood()
							+ "\n" + "Sad: " + annotation.getSorrowLikelihood());

					System.out.println("Likely Emotion: " + likelyEmotion);
				}
			}
		}

		return likelyEmotion;
	}
}
