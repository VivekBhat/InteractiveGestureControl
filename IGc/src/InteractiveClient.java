import java.awt.Dimension;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import com.github.sarxos.webcam.Webcam;

public class InteractiveClient {
	private static String filename = null;

	public static void captureMethod(int lighting, int orienation, int position, int gesture, int sample,
			Webcam webcamObject, Dimension size) {
		int gestureDuration = 3;

		// voce.SpeechInterface.init("./src/lib/voce-0.9.1/lib", true, true,
		// "./src/lib/voce-0.9.1/lib/gram", "response");

		// voce.SpeechInterface
		// .synthesize("Welcome. You are now collecting data for the ambient
		// light gesture recongition system.");
		// voce.SpeechInterface.synthesize("Each gesture will be measured upto "
		// + gestureDuration + " seconds");

		try {
			// Thread.sleep(1000);
			// int sampleCount = 1;
//
//			voce.SpeechInterface.synthesize("Get ready!");
//
//			 voce.SpeechInterface.setRecognizerEnabled(true);
//			 Thread.sleep(2000);
//
//			 String s = null;
//			
//			 int i = 0;
//			 /*
//			 * This while loop is now useless as we are not taking
//			 voice
//			 * inputs anymore. Nevertheless, it is useful to make the
//			 * system 'wait' for some time before user gets ready at
//			 * next position
//			 */
//			 while (true) {
//			 System.out.println(voce.SpeechInterface.getRecognizerQueueSize());
//			 if (voce.SpeechInterface.getRecognizerQueueSize() > 0)
//			 break;
//			 i++;
//			 if (i > 700)
//			 break;
//			 }
//			
//			 s = voce.SpeechInterface.popRecognizedString();
//			 voce.SpeechInterface.setRecognizerEnabled(false);
//			 System.out.println(s);
//			
//			 // TODO Delete this line later.
//			 s = "okay";
//			
//			 // if (-1 != s.indexOf("okay"))
//			 // {
			Thread.sleep(2000);

			voce.SpeechInterface.synthesize("Begin");
			Thread.sleep(2000);

			contactListener("START" + lighting + orienation + position + gesture + sample);
			String timestamp = new Date(System.currentTimeMillis() + 3000).toString();
			filename = timestamp + "_L" + lighting + "_O" + orienation + "_P" + position + "_G" + gesture + "_S"
					+ sample;
			WebcamCapture wc = new WebcamCapture(filename, webcamObject, size);
			new Thread(wc).start();
			// start = true;
			// }
			/*
			 * else if (-1 != s.indexOf("no")) { Thread.sleep(200); break; }
			 */

			Thread.sleep(gestureDuration * 1000);
			voce.SpeechInterface.synthesize("Stop");

			contactListener("STOP");

			// voce.SpeechInterface.synthesize("You have collected " + (sample)
			// + "samples");
			// Thread.sleep(5000);
			// // start = false;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void contactListener(String msg) throws IOException {
		String listenerIP = "152.14.92.243";
		int listenerPort = 7331;

		Socket s = new Socket(listenerIP, listenerPort);
		PrintWriter out = new PrintWriter(s.getOutputStream(), true);
		out.println(msg);
		s.close();
	}

}
