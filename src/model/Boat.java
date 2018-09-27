package model;

public class Boat {
	private BoatType type;
	private double size;

	public enum BoatType { Sailboat(1), Motorsailer(2), KayakCanoe(3), Other(4);
		
        private int value;

        private BoatType(int val) {
                this.value = val;
        }
    };
	
	public Boat(BoatType newType, double newSize) {
		type = newType;
		size = newSize;
	}

	public Boat() {
		
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