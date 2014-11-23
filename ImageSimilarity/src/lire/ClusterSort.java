package lire;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import model.Cluster;
import model.Media;
import net.semanticmetadata.lire.DocumentBuilder;
import net.semanticmetadata.lire.DocumentBuilderFactory;
import net.semanticmetadata.lire.imageanalysis.FCTH;

import org.apache.lucene.document.Document;

public class ClusterSort {
	
	double[] fcthFeatureVector;
	double distanceToSearchImage;
	
	// ArrayList of Cluster objects
	ArrayList<Cluster> clusters = new ArrayList<Cluster>();	
	
	public ClusterSort(ArrayList<BufferedImage> images, Media media) {
		
		// Check
		if (images.isEmpty()) {
			throw new RuntimeException("The media array is empty");
		}
		
		double[] searchImage = getFCTHFeatureVector(images.get(0), media.getMediaUrl());
		
		// Loop on the arraylist
		for (int i = 0; i < images.size(); i++) {
			
			// Declare an array to hold vector data
			double[] fcthFeatureVector = getFCTHFeatureVector(images.get(i), media.getMediaUrl());
			double distanceToSearchImage = calculateEuclideanDistance(fcthFeatureVector, searchImage);
			
			System.out.println(distanceToSearchImage);
			
		}
				
	}
	
	public static double[] getFCTHFeatureVector(BufferedImage img, String url) {
		
		// Get the FCTH Document builder
		DocumentBuilder builder = DocumentBuilderFactory.getFCTHDocumentBuilder();
		
		Document document = null;
		
		// Add it to the Lucene Document
		try {
			document = builder.createDocument(img, url);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if (document == null) {
			throw new RuntimeException("Document was null");
		}
		FCTH fcthDescriptor = new FCTH();
		
		// Get the DoubleHistogram vector
		return fcthDescriptor.Apply(img);
	}
	
	/*
	 * Calculate the Euclidean Distance in the images
	 */
	public static double calculateEuclideanDistance(double[] vector1, double[] vector2) {
		double innerSum = 0.0;
		for (int i = 0; i < vector1.length; i++) {
			innerSum += Math.pow(vector1[i] - vector2[i], 2.0);
		}
		
		return Math.sqrt(innerSum);
	}
	
	

}
