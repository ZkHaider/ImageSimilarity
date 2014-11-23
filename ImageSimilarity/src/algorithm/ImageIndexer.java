package algorithm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import net.semanticmetadata.lire.DocumentBuilder;
import net.semanticmetadata.lire.DocumentBuilderFactory;
import net.semanticmetadata.lire.utils.LuceneUtils;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

// Simple Index Creation

public class ImageIndexer {
	
	
	public ImageIndexer(File[] f) throws IOException {
		// Checking if File[] is there and if there is a directory 
		boolean passed = false;
		
		File file = f[0];
			
		System.out.println("Indexing images in " + f[0]);
			
			
		// If the file exists and the file is in a directory
		if (file.exists() && file.isDirectory()) {
			// Set the boolean to true
			passed = true;
		}
		
		
		// If no directory is available then throw this error message
		if (!passed) {
			System.out.println("No directory given as first argument");
			System.out.println("Run \"Indexer <directory>\" to index files of a directory");
			System.exit(1);
		}
		
		
		// Once the check has been passed, store file paths into an arraylist...
		
		// Get all the images from the directory and its subdirectories 
		ArrayList<String> images = new ArrayList<String>();
		
		// Create a CEDD Document Builder and indexing all the files
		DocumentBuilder builder = DocumentBuilderFactory.getFCTHDocumentBuilder();
		// Create a Lucene IndexWriter
		IndexWriterConfig config = new IndexWriterConfig(LuceneUtils.LUCENE_VERSION,
				new WhitespaceAnalyzer(LuceneUtils.LUCENE_VERSION));
		
		IndexWriter iw = new IndexWriter(FSDirectory.open(new File("index")), config);
		
		// Iterate through the images and build the low level features
		for (Iterator<String> it = images.iterator(); it.hasNext(); ) {
			String imageFilePath = it.next(); // Grab the next file path
			System.out.println("Indexing: " + imageFilePath);
			
			// Try 
			try {
				// Grab the image from the image file path and store into a bufferedimage
				BufferedImage img = ImageIO.read(new FileInputStream(imageFilePath));
				// Get that image and store into a document with its file path.
				Document document = builder.createDocument(img, imageFilePath);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Error reading image or indexing it.");
			}
		}
		
		// Close out the index writer
		iw.close();
		System.out.println("Finished Indexing");
		
	}
	

}
