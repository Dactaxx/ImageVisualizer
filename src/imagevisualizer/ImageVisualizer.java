package imagevisualizer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

public class ImageVisualizer {
	public static void main(String[] args) throws IOException {
		BufferedImage bi = ImageIO.read(new File("input.png"));
		ArrayList<String> colorsHex = new ArrayList<String>();
		ArrayList<Color> colorsRGB = new ArrayList<Color>();
		
		System.out.println("Getting Colors...");
		
		for(int row = 0; row < bi.getHeight(); row++) {
			for(int col = 0; col < bi.getWidth(); col++) {
				Color color = new Color(bi.getRGB(col, row));
				String hex = String.format("%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());  
				colorsHex.add(hex);
				colorsRGB.add(color);
//				colors.add(color.getRed() + color.getGreen() * 256 + color.getBlue() * 65536);
				
			}
		}
		
		System.out.println("Sorting...");
		
		Collections.sort(colorsHex);
		
		boolean complete = false;
		for(int k = 0; k < 10; k++) {
			for(int i = 0; i < colorsRGB.size(); i++) {
				System.out.println(i);
				Color main = colorsRGB.get(i);
				double distMin = 4073;
				int goTo = i;
				for(int j = 0; j < colorsRGB.size() - 1; j++) {
					if(j == i) j++;
					Color sec = colorsRGB.get(j);
					double dist = Math.sqrt(main.getRed() - sec.getRed()) + Math.sqrt(main.getGreen() - sec.getGreen()) + Math.sqrt(main.getBlue() - sec.getBlue());
					if(dist < distMin) {
						distMin = dist;
						goTo = j;
					}
					
				}
				if(goTo == 0) goTo = colorsRGB.size() - 1;
				Color exchange = colorsRGB.get(goTo - 1);
				
				colorsRGB.set(goTo, main);
				colorsRGB.set(i, exchange);
				
			}
			
		}
		
		ArrayList<Integer> colors = new ArrayList<Integer>();
		
		System.out.println("Converting to hex...");
		
		for(int i = 0; i < colorsHex.size(); i++) {
			colors.add(Integer.valueOf(colorsHex.get(i), 16));

		}
		
		System.out.println("Writing Image...");
		
		BufferedImage bia = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_ARGB);
		int inc = 0;
		for(int row = 0; row < bi.getHeight(); row++) {
			for(int col = 0; col < bi.getWidth(); col++) {
//				bia.setRGB(col, row, hexToRGB(colorsHex.get(inc)).getRGB());
				bia.setRGB(col, row, colorsRGB.get(inc).getRGB());
				inc++;
				
			}
		}
		
		ImageIO.write(bia, "png", new File("output.png"));
		
		System.out.println("Complete!");
		
	}
	
	public static Color hexToRGB(String hex) {
	    return new Color (
	            Integer.valueOf(hex.substring(0, 2), 16),
	            Integer.valueOf(hex.substring(2, 4), 16),
	            Integer.valueOf(hex.substring(4, 6), 16));
	}

	
}
