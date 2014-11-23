package algorithm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.semanticmetadata.lire.DocumentBuilder;
import net.semanticmetadata.lire.ImageSearchHits;
import net.semanticmetadata.lire.ImageSearcher;
import net.semanticmetadata.lire.ImageSearcherFactory;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

public class Searcher {
	
	public Searcher(File[] f) throws IOException {
		
		// Check if there is there is a file in f[0]
		BufferedImage img = null;
		boolean passed = false;
		
		if (f.length > 0) {
			File file = f[0];
			
			if (file.exists()) {
				try {
					img = ImageIO.read(file);
					passed = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		if (!passed) {
			System.out.println("No image is given as first file");
			System.out.println("Rule \"Searcher <query image>\" to search for <query image>.");
			System.exit(1);
		}
		
		// Read the images
		IndexReader ir = DirectoryReader.open(FSDirectory.open(new File("index")));
		
		// Create a query of 10 images
		ImageSearcher searcher = ImageSearcherFactory.createCEDDImageSearcher(10);
		
		// Get the hits
		ImageSearchHits hits = searcher.search(img, ir);
		
		for (int i = 0; i < hits.length(); i++) {
			String fileName = hits.doc(i).getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)[0];
			System.out.println(hits.score(i) + ": \t" + fileName);
		}
		
	}
	

}
