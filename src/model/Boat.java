package model;

public class Boat {
	private BoatType type;
	private double size;

	public enum BoatType { Sailboat, Motorsailer, KayakCanoe, Other };
	
	public Boat(BoatType newType, double newSize) {
		type = newType;
		size = newSize;
	}
	
	public BoatType getType() {
		return type;
	}
	
	public double getSize() {
		return size;
	}
	
	public void editType(BoatType newType) {
		type = newType;
	}
	
	public void editSize(double newSize) throws IllegalArgumentException {
		if (newSize <= 0) {
			throw new IllegalArgumentException("Size cannot be 0 or negative!");
		}
		
		size = newSize;
	}
	
}