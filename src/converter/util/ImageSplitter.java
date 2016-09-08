package converter.util;

import java.awt.image.BufferedImage;

public class ImageSplitter {
	public static BufferedImage[][] getImages(BufferedImage image, int rows, int cols){
		BufferedImage[][] sprites = new BufferedImage[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				sprites[i][j] = image.getSubimage(j*32, i*32, 32, 32);
			}
		}
		return sprites;
	}
	
}
