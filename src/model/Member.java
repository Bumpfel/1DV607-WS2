package model;

import java.util.ArrayList;

public class Member {
	private static int nextId = 1; // should be set to last id found in the text file

	private String name;
	private String pnr;
	private ArrayList<Boat> boats = new ArrayList<>();
	private int id;

	public Member() { // Needed for json object mapper
		nextId = this.id++;
	}

	public Member(String newName, String newPNr) {
		nextId++;

		id = nextId;
		name = newName;
		pnr = newPNr;
	}

	public void editName(String newName) throws IllegalArgumentException {
		if (newName.length() >= 2)
			name = newName;
		else
			throw new IllegalArgumentException("The name must be at least 2 characters long");
	}

	public void editPNr(String newPNr) throws IllegalArgumentException {
		if (newPNr.length() >= 10) // simplest possible check
			pnr = newPNr;
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
		return pnr;
	}

	public void registerBoat(Boat.BoatType type, double size) {
		boats.add(new Boat(type, size));
	}

	public void removeBoat(int index) {
		boats.remove(index);
	}

	public ArrayList<Boat> getBoats() {
		return new ArrayList<Boat>(boats);
	}

	// public String toString() {
	// return id + ": " + name + ". Has " + boats.size() + " boats registered to the
	// club";
	// }

	void setNextId(int nextid) {
		id = nextid;
	}

	//TODO implementera i senare skede?
	private boolean pnrIsCorrect(String nr) {
		int firstPart = Integer.parseInt(nr.substring(6,10));
		int secondPart = Integer.parseInt(nr.substring(0,6));

		int month = firstPart % 10000 / 100;
		int day = secondPart % 100;
		
		//Kontrollera att födelsedatumet är rimligt
		if (month < 1 || month > 12) 
			return false;		
		if (day < 1 || day > 31) 
			return false;
				
		switch (month) {
		case 2: 
			if (day > 28) return false; //Om dag är över 28 i kombination med månad 2
			break;
		case 4:		
		case 6:
		case 9:
		case 11: 
			if (day > 30) return false; //Om dag är över 30 i kombination med månad 4,6,9,11
			break;
		}
		
		//Beräkna och jämför kontrollsiffra
		int first = firstPart;
		int last = secondPart / 10;
		int sum = 0;
		
		//Födelsedatum
		for (int i = 0; i < 6; i++) {
			int digit = first % 10; //Plocka ut en siffra
			first /= 10;			
			if (i % 2 != 0)  //Kontrollera om siffran ska multipliceras med 2			
				digit *= 2;						
			sum += digit % 10 + digit / 10; //Tvåsiffriga tal delas upp innan de adderas till summan
			
		//Fyra sista 	
		}		
		for (int i = 0; i < 4; i++) {
			int digit = last % 10;
			last /= 10;
			if (i % 2 == 0) 				
				digit *= 2; 			
			sum += digit % 10 + digit / 10;
		}		
		int control = (10 - (sum % 10)) % 10; //Räkna ut kontrollsiffra baserad på ovanstående summa		
		if (control != secondPart % 10)  //Jämför kontrollsiffra med angivet personnummer
			return false;				
		return true;
	}	
}