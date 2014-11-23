package sample;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import lire.ClusterSort;
import model.Media;



public class ImageSimilarity {
	
	private ArrayList<Media> mediaImages = new ArrayList<Media>();
	private ArrayList<String> imageUrls = new ArrayList<String>();
	private ArrayList<BufferedImage> imageList = new ArrayList<BufferedImage>();
	
	// Method to execute
	public static void main(String[] args) {
		new ImageSimilarity();
		
		// Implement the sorting algorithm. 
	}
	
	// Constructor to initiate the call to grab the data form the DB
	public ImageSimilarity() {
		
		// Connection objec to connect to the postgres database
		Connection conn = null;
		ArrayList<Media> mediaList = new ArrayList<Media>();
		
		// Connect to the database
		conn = connectToTheDatabase();
		
		// Grab the data from the database
		populateArrayList(conn, mediaList);
		
		// Print out results for test purposes
		// printUrlResults(mediaList); 
		for (int i = 0; i < mediaImages.size(); i++) {
			
			BufferedImage img = null;
			
			try {
				URL url = new URL(mediaImages.get(i).getMediaUrl());
				img = ImageIO.read(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (img == null) {
				System.out.println("Image [" + i + "] is null");
			} else {
				//System.out.println("Image [" + i + "] is not null"); // Works
			}
			
//			System.out.println("Image [" + i +"]:" + "Height = " + img.getHeight() 
//					+ ", Width = " + img.getWidth()); // Works
			
//			System.out.println(imageUrls.get(i)); // Stored
			imageList.add(img);
		}
		
		/*
		 * Run a quick sample app to see if code works
		 */
		Media initial = mediaImages.get(0);
		new ClusterSort(imageList, initial);
		
	}
	
	private void createLireIndex() {
		// Use
	}
	
	// Print out the results -- don't need to use this since it works
	private void printUrlResults(ArrayList<Media> listOfMediaUrls) {
		Iterator it = listOfMediaUrls.iterator();
		while (it.hasNext()) {
			Media media = (Media) it.next();
			System.out.println("Event id: " + media.getEventId() + ", Social id: " + 
					media.getSocialId() + ", Media URL: " + media.getMediaUrl());
		}
	}
	
	// Populate the ArrayList from the query
	private void populateArrayList(Connection conn, ArrayList<Media> mediaList) {
		this.mediaImages = mediaList;
		
		Statement st;
		ResultSet rs;
		
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery("SELECT id, event_id, social_id, media_url FROM media");
			// st.execute("INSERT INTO media_clusters VALUES ()");
			// SELECT id FROM MEDIA_CLUSTERS ORDER BY ID DESC LIMIT 1
			while (rs.next()) {
				
				int id = rs.getInt("id");
				int eventId = rs.getInt("event_id");
				String socialId = rs.getString("social_id");
				String mediaUrl = rs.getString("media_url");
				
				//System.out.println(id + ": event id: " + eventId + " ,media url: " + mediaUrl
				//			+ "  , social id: " + socialId); // Works
				
				Media media = new Media(
						id,
						eventId,
						socialId,
						mediaUrl
						);
				imageUrls.add(mediaUrl);
				mediaImages.add(media);
			}
			
			rs.close();
			st.close();
			
			
		} catch (SQLException e) {
			System.err.println("SQLException while creating arraylist of media objects");
			System.err.println(e.getMessage());
		}
		
	}
	
	private Connection connectToTheDatabase() {
		Connection conn = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			String username = "suywpxmdpalteb";
			String password = "RHncVMM2AWUw4BUO7APpWidkMO";
			
			String dburl = "jdbc:postgresql://ec2-54-243-245-159.compute-1.amazonaws.com:5432/dfct9618chnf84?user=suywpxmdpalteb&password=RHncVMM2AWUw4BUO7APpWidkMO&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
			
			String url = "jdbc:postgresql://suywpxmdpalteb:RHncVMM2AWUw4BUO7APpWidkMO@ec2-54-243-245-159.compute-1.amazonaws.com:5432/dfct9618chnf84";
			
			conn = DriverManager.getConnection(dburl, username, password);
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(2);
		}
		
		return conn;
	}

}
