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
		// Checking the eclipse link
		try {
			Thread.sleep(2000);

			voce.SpeechInterface.synthesize("Begin");
			Thread.sleep(2000);

			contactListener("START" + lighting + orienation + position + gesture + sample);
			String timestamp = new Date(System.currentTimeMillis() + 3000).toString();
			filename = timestamp + "_L" + lighting + "_O" + orienation + "_P" + position + "_G" + gesture + "_S"
					+ sample;
			WebcamCapture wc = new WebcamCapture(filename, webcamObject, size);
			new Thread(wc).start();

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
