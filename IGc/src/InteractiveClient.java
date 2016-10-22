import java.awt.Dimension;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import com.github.sarxos.webcam.Webcam;

public class InteractiveClient {
	private static String filename = null;

	public static void captureMethod(int lighting, int orienation, int position, int gesture, int sample, int subjectID,
			Webcam webcamObject, Dimension size) {
		int gestureDuration = 3;
		// Checking the eclipse link
		try {
			Thread.sleep(300);

			
			String timestamp = new Date(System.currentTimeMillis() + 5000).toString();

			filename = timestamp + "_L" + lighting + "_O" + orienation + "_P" + position + "_G" + gesture + "_S"
					+ sample + "_ID" + subjectID;
			WebcamCapture wc = new WebcamCapture(filename, webcamObject, size);
			new Thread(wc).start();
			voce.SpeechInterface.synthesize("Begin");
			Thread.sleep(700);
			
			contactListener("START" + lighting + orienation + position + gesture + sample + subjectID);
			
			
			Thread.sleep(gestureDuration * 1000);
			voce.SpeechInterface.synthesize("Stop");

			contactListener("STOP");

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
