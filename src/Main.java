import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	
	public static void main(String[] args) throws IOException {
		
		BufferedImage img = null;
		File in;
		int width = 1920, height = 1080;
		String dir = System.getProperty("user.dir")+"\\res\\";
		
		for(int runs = 1; runs < 101; runs+=10) {
			try {
				in = new File(dir+"image.jpg");
				img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				img = ImageIO.read(in);
				
				//Edit
				int square_size = runs;
				int[] rgbtotal;
				int num_w = (int)Math.ceil((double)width/square_size);
				int num_h = (int)Math.ceil((double)height/square_size);
				
				for(int i = 0; i < num_w; i++) {
					for(int j = 0; j < num_h; j++) {
						
						Color c;
						rgbtotal = new int[3];
						int final_x = (i*square_size+square_size > width)?width:i*square_size+square_size;
						int final_y = (j*square_size+square_size > height)?height:j*square_size+square_size;
						int numpixels = 0;
						
						for(int x = i*square_size; x < final_x; x++) {
							for(int y = j*square_size; y < final_y; y++) {
								c = new Color(img.getRGB(x, y));
								rgbtotal[0] += c.getRed();
								rgbtotal[1] += c.getGreen();
								rgbtotal[2] += c.getBlue();
								numpixels++;
							}
						}
						
						for(int z = 0; z < 3; z++)
							rgbtotal[z] /= numpixels;
						
						//Bitshifts the RGB values into an int
						int rgb = rgbtotal[0]; //Red
						rgb = (rgb<<8) + rgbtotal[1]; //Green
						rgb = (rgb<<8) + rgbtotal[2]; //Blue
						
						for(int x = i*square_size; x < final_x; x++) {
							for(int y = j*square_size; y < final_y; y++) {
								img.setRGB(x, y, rgb);
							}
						}
						
					}
				}
				
				File out = new File(dir+String.format("output%d.jpg",runs));
				ImageIO.write(img, "jpg", out);
			}
			catch(IOException e) {
				System.out.println("Error: " + e);
			}
		}
		System.out.println("Finished!");
	}
}
