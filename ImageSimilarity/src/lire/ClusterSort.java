package lire;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

import model.Cluster;
import model.ClusterComparator;
import model.Media;
import net.semanticmetadata.lire.DocumentBuilder;
import net.semanticmetadata.lire.DocumentBuilderFactory;
import net.semanticmetadata.lire.imageanalysis.FCTH;

import org.apache.lucene.document.Document;

public class ClusterSort {
	
	double[] fcthFeatureVector;
	double distanceToSearchImage;
	
	// ArrayList of Cluster Objects
	ArrayList<ArrayList<Cluster>> clusters = new ArrayList<ArrayList<Cluster>>();
	
	public ClusterSort(ArrayList<Media> images) {
		
		// Check
		if (images.isEmpty()) {
			throw new RuntimeException("The media array is empty");
		}
		
		
		// Loop on the arraylist
		for (int i = 0; i < images.size(); i++) {
			
			double[] searchImage = getFCTHFeatureVector(images.get(i), 
					images.get(i).getMediaUrl());
			// Declare Cluster array
			ArrayList<Cluster> cluster = new ArrayList<Cluster>();
			
			for (int j = 0; j < images.size(); j++) {
				
			
				// Declare an array to hold vector data
				double[] fcthFeatureVector = getFCTHFeatureVector(images.get(j), images.get(j).getMediaUrl());
				double distanceToSearchImage = calculateEuclideanDistance(fcthFeatureVector, searchImage);
				
				// Add to the Cluster ArrayList
				Cluster obj = new Cluster(i, distanceToSearchImage, images.get(i).getTime(), images.get(i).getMediaUrl());
				obj.setDistance(distanceToSearchImage);
				
				//System.out.println(obj.getTime());
				
				//System.out.println(distanceToSearchImage);
				cluster.add(obj);
			}
			
			Collections.sort(cluster, new ClusterComparator());
			
			clusters.add(cluster);
		}
		
		
		
		// Print time to see values
//		for (int i = 0; i < cluster.size(); i++) {
//			System.out.println(cluster.get(i).getDistance() + " at Time: " + 
//						cluster.get(i).getTime());
//		}
				
	}
	
	public static double[] getFCTHFeatureVector(Media img, String url) {
		
		// Get the FCTH Document builder
		DocumentBuilder builder = DocumentBuilderFactory.getFCTHDocumentBuilder();
		
		BufferedImage bImg = null;
		
		Document document = null;
		
		// Add it to the Lucene Document
		try {
			URL imageUrl = new URL(img.getMediaUrl());
			bImg = ImageIO.read(imageUrl);
			document = builder.createDocument(bImg, url);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		if (document == null) {
			throw new RuntimeException("Document was null");
		}
		FCTH fcthDescriptor = new FCTH();
		
		// Get the DoubleHistogram vector
		return fcthDescriptor.Apply(bImg);
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
	
	public ArrayList<ArrayList<Cluster>> getClusters() {
		return clusters;
	}
}
