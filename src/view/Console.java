package view;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class Console implements ViewInterface {
	
	public Console() {
		
	}

	public void displayWelcomeMsg() {
		System.out.println("Whalecum to \"The Jolly Pirate\" boatclub's member registry!");
	}
	
	public void displayMenu(ArrayList<String> options) {
		int numOfOptions = options.size();
		
		for (int i = 0; i < numOfOptions; i++) {
			System.out.print(i+1+": "); //Displays the number of the option
			System.out.println(options.get(i)); //Displays the option
		}
		
		System.out.println("What would you like to do?");
	}
	
	public String getInputString() {
		String input = "";
		Scanner readKGB = new Scanner(System.in);

		input = readKGB.nextLine();
		readKGB.close();
		
		System.out.println("Returning..."+input);
		return input;
	}
	
	public int getInputInt() {
		int input = -1;
		
		while (input <= 0) {
			try {
				input = this.stringToInt(this.waitForInput());
			}
			
			catch (NumberFormatException | NoSuchElementException e) {
				this.displayError("Input is not a number!");
			}
		}
		
		return input;
	}
		
	private String waitForInput() throws NumberFormatException {
		String input;
		Scanner readKB = new Scanner(System.in);
		
		input = readKB.nextLine();
		readKB.close();
		
		return input;
	}

	public void displayMembersVerbose() {
		// TODO Auto-generated method stub
	}

	public void DisplayMembersCompact() {
		// TODO Auto-generated method stub
		
	}

	public void displayMemberReg() {
		// TODO Auto-generated method stub
	}
	
	public void displayError(String e) {
		System.err.println(e);
	}
	
	private int stringToInt(String s) throws NumberFormatException {
		int intToReturn = -1;
		
		intToReturn = Integer.parseInt(s);
		
		return intToReturn;
	}
}
