package model;

public class Boat {
	private Types type;
	private double length;

	public enum Types { Sailboat, Motorsailer, KayakCanoe, Other };
	
	public Boat(Types newType, double newLength) {
		type = newType;
		length = newLength;
	}
	
	public Types getType() {
		return type;
	}
	
	public double getLength() {
		return length;
	}
	
	public String toString() {
		return type + ", " + length + "m";
	}
}

