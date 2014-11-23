package model;

import java.util.Comparator;
import java.util.Date;

public class Cluster {

	private int id;
	double distance;
	private Date time;
	private String url;

	public Cluster(int id, double distance, Date time, String url) {
		this.id = id;
		this.distance = distance;
		this.time = time;
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public Date getTime() {
		return time;
	}
	
	public void setTime(Date time) {
		 this.time = time;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

}
