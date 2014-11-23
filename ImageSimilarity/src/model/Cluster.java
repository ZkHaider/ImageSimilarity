package model;

import java.util.Comparator;

public class Cluster implements Comparator<Cluster> {

	private int id;
	private double distance;

	public Cluster(int id, double distance) {
		this.id = id;
		this.distance = distance;
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

	@Override
	public int compare(Cluster obj1, Cluster obj2) {
		// TODO Auto-generated method stub
		// If the distanceToSearchImage is less than the next object return -1
		if (obj1.distance < obj2.distance) {
			return -1;
		} else if (obj1.distance > obj2.distance) {
			// Else if ...
			return 1;
		} else {
			// Else...
			return 0;
		}
	}

}
