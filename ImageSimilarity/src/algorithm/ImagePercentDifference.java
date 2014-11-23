package algorithm;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.eclipse.jetty.util.thread.ThreadPool;

public class ImagePercentDifference {
	
	private BufferedImage img1 = null;
	private BufferedImage img2 = null;
	
	public ImagePercentDifference(BufferedImage img1, BufferedImage img2) {
		this.img1 = img1;
		this.img2 = img2;
		
		// Get the width of the images
		int width1 = img1.getWidth(null);
	    int width2 = img2.getWidth(null);
	    
	    // Get the height of the images
	    int height1 = img1.getHeight(null);
	    int height2 = img2.getHeight(null);
	    
	    // If the width of img1 does not equal width2 and same for height...
	    if ((width1 != width2) || (height1 != height2)) {
	      // Image dimensions do not match...
	      System.err.println("Error: Images dimensions mismatch");
	      System.exit(1);
	    }
	    // Variable for difference
	    long diff = 0;
	    
	    // Loop around the height and the width 
	    for (int i = 0; i < height1; i++) {
	      for (int j = 0; j < width1; j++) {
	    	
	    	// Grab the RGB pixel 
	        int rgb1 = img1.getRGB(i, j);
	        int rgb2 = img2.getRGB(i, j);
	        
	        // Convert to unsigned bytes 
	        int r1 = (rgb1 >> 16) & 0xff;
	        int g1 = (rgb1 >>  8) & 0xff;
	        int b1 = (rgb1      ) & 0xff;
	        int r2 = (rgb2 >> 16) & 0xff;
	        int g2 = (rgb2 >>  8) & 0xff;
	        int b2 = (rgb2      ) & 0xff;
	        diff += Math.abs(r1 - r2);
	        diff += Math.abs(g1 - g2);
	        diff += Math.abs(b1 - b2);
	      }
	    }
	    // Get the average difference 
	    double n = width1 * height1 * 3;
	    double p = diff / n / 255.0;
	    System.out.println("diff percent: " + (p * 100.0));
	}

}
