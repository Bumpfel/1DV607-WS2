package view;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

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
		
		return input;
	}
	
	public int getInputInt_old() {
		int input = -1;
		Scanner readKGB = new Scanner(System.in);

		try {
			input = readKGB.nextInt();
		}
		
		catch (InputMismatchException e) {
			this.displayError("Input was not a number!");
		}
		
		readKGB.close();
		
		return input;
	}

	public int getInputInt() {
		Scanner in = new Scanner(System.in);
		int input = -1;

		boolean receivedValidInput = false;
		while(!receivedValidInput) {
			System.out.println("Waiting for input....");
			if(in.hasNextInt()) {
				input = in.nextInt();
				if(input > 0) {
					receivedValidInput = true;
				}
				else {
					displayError("Input must be larger than 0");
				}
			}
			else {
				displayError("Input must be a number");
				in.nextLine();
			}
		}

		in.close();
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
		System.out.println(e);
	}
}
