package converter;

import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.File;

import converter.util.*;

public class SpriteConverter {
	
	private BufferedImage img;
	
	public SpriteConverter(BufferedImage img) {
		this.img = img;
	}

	public List convert(File destination){
		List liste = new List();
		BufferedImage[][] sprites = ImageSplitter.getImages(img, 25, 20);
		for (int row = 0; row < sprites.length; row++) {
			String color = "";
			switch (Math.floorDiv(row, 5)) {
			case 0:
				color = "DEFAULT";
				break;
			case 1:
				color = "BERRY";
				break;
			case 2:
				color = "MINT";
				break;
			case 3:
				color = "LAVENDER";
				break;
			case 4:
				color = "SUNLIGHT";
				break;
			default:
				color = "UNKNOWN";
				break;
			}
			for (int col = 0; col < sprites[row].length; col++) {
				String name = null;
				name = Naming.getName(row, col);
				if (name != null) {
					boolean done = ImageCreator.writeImage(sprites[row][col], name, color, destination);
					if (!done) {
						liste.add(name);
					} 
				} 
			}
		}
		return liste;
	}

}
