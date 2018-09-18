package model;

import java.util.ArrayList;

public class Member {
	private static int nextId = 0; // should be set to last id found in the text file

	private String name;
	private String personalNumber;
	private ArrayList<Boat> boats = new ArrayList<>();
	private int id;
	
	public Member(String newName, String newPNr) {
		nextId ++;
		
		id = nextId;
		name = newName;
		personalNumber = newPNr;
	}
	
	protected void editName(String newName) throws IllegalArgumentException {
		if(newName.length() >= 2)
			name = newName;
		else
			throw new IllegalArgumentException("The name must be at least 2 characters long");
	}
	
	protected void editPNr(String newPNr) throws IllegalArgumentException {
		if(newPNr.length() >= 10) // simplest possible check
			personalNumber = newPNr;
		else
			throw new IllegalArgumentException("The personal number you entered is invalid");
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getPNr() {
		return personalNumber;
	}
	
	public ArrayList<Boat> getBoats() {
		return new ArrayList<Boat>(boats);
	}

//	public String toString() {
//		return id + ": " + name + ". Has " + boats.size() + " boats registered to the club";
//	}
	
}