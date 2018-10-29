package model;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Member {
	private String name;
	private String pnr;
	private ArrayList<Boat> boats = new ArrayList<>();
	private int id;

	public Member() { // Needed for json object mapper
	}

	public Member(String newName, String newPNr) {
		id = -1;
		name = newName;
		pnr = newPNr;
	}

	public Member(Member member, int newId) { // used by MemberRegistry since its handling the id
		id = newId;
		name = member.name;
		pnr = member.pnr;
	}

	public void editName(String newName) {
		name = newName;
	}

	public void editPNr(String newPNr) {
		pnr = newPNr;
	}

	public void editMember(String newName, String newPNr) {
		name = newName;
		pnr = newPNr;
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

	public void addBoat(Boat b) {
		boats.add(b);
	}

	public void removeBoat(Boat boat) {
		for (Boat b : boats) {
			if (boat == b) {
				boats.remove(b);
				return;
			}
		}
		throw new NoSuchElementException();
	}

	public ArrayList<Boat> getBoats() {
		return new ArrayList<Boat>(boats);
	}

	public String toString() {
		return id + ". " + name + " - " + pnr;
	}

	public static boolean isValidName(String input) {
		String str = input.replaceAll("[0-9]", "");

		return (input.trim().length() >= 2 && input.equals(str)); // input has >= 2 characters and contains no digits
	}

	public static boolean isValidPNr(String nr) {
		if(nr.length() != 11) {
			return false;
		}
		try {
			int firstPart = Integer.parseInt(nr.substring(0, 6));
			int secondPart = Integer.parseInt(nr.substring(7, 11));
			String separator = nr.substring(6, 7);

			int year = firstPart / 10000;
			int month = firstPart % 10000 / 100;
			int day = firstPart % 100;


			// Basic date checks
			if (month < 1 || month > 12) {
				return false;
			}
			if (day < 1 || day > 31) {
				return false;
			}	
		
			switch (month) {
				case 2: // If february
					if(year % 4 == 0) {
						if (day > 29) // If leap year and day > 29
							return false;
					}
					else if (day > 28) { // if not leap year, but day > 28
						return false;
					}
					break;
				case 4:
				case 6:
				case 9:
				case 11:
					if (day > 30) { // If april, june, september, or november and day > 30
						return false; 
					}
					break;
			}
			
			if(!separator.equals("-")) {
				return false;
			}

		// Calculates and compares the control nr
			int first = firstPart;
			int last = secondPart / 10;
			int sum = 0;

			// Birth date
			for (int i = 0; i < 6; i++) {
				int digit = first % 10; // Picks last digit
				first /= 10;
				if (i % 2 != 0) // Multiply digit with 2 if it's on an even position
					digit *= 2;
				sum += digit % 10 + digit / 10; // Divide two digit numbers and add them individually
			}

			// Four last
			for (int i = 0; i < 4; i++) {
				int digit = last % 10;
				last /= 10;
				if (i % 2 == 0)
					digit *= 2;
				sum += digit % 10 + digit / 10;
			}
			int control = (10 - (sum % 10)) % 10; // Calculate control nr base on the sum above
			if (control != secondPart % 10) {// Compare the calculated control nr with the control nr in the personal code nr
				return false;
			}
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
}