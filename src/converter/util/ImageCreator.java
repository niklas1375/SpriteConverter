package converter.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageCreator {

	public static boolean writeImage(BufferedImage image, String name, String color, File destination) {
		String filename = destination.getAbsolutePath() + "\\";
		name = filename + color + "\\" + name + ".png";
		File outputfile = new File(name);
		try {
			File parentDir = outputfile.getParentFile();
			if (parentDir != null && !parentDir.exists()) {
				if (!parentDir.mkdirs()) {
					throw new IOException("error creating directories");
				}
			}
			return ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
