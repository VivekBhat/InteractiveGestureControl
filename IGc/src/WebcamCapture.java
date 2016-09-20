
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

import com.github.sarxos.webcam.Webcam;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;


public class WebcamCapture implements Runnable
{
	String filename = null;
	Webcam webcam = null;
	Dimension size = null;
			
	public WebcamCapture(String filename,Webcam webcam,Dimension size)
	{
		this.filename = filename;
		this.webcam = webcam;
		this.size = size;
				
	}

	public void run()
	{
		File file = new File(filename+".ts");
		IMediaWriter writer = ToolFactory.makeWriter(file.getName());
		writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, size.width, size.height);

		long start = System.currentTimeMillis();
		
		for (int i = 0; i < 120; i++)
		{
			BufferedImage image = ConverterFactory.convertToType(webcam.getImage(), BufferedImage.TYPE_3BYTE_BGR);
			IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);

			IVideoPicture frame = converter.toPicture(image, (System.currentTimeMillis() - start) * 1000);
			frame.setKeyFrame(i == 0);
			frame.setQuality(0);

			writer.encodeVideo(0, frame);

			// 40 FPS
			try 
			{
				Thread.sleep(25);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		writer.close();
	}
}