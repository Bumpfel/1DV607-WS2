package view;

import java.util.Scanner;
import java.util.ArrayList;

public class Console implements ViewInterface {
	Scanner readKGB = new Scanner(System.in);
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
		
		input = readKGB.nextLine();
				
		return input;
	}
		
	public int getInputInt() {		
		int input = -1;

		
		while(true) {
			System.out.println("Waiting for input....");
			if(readKGB.hasNextInt()) {
				input = readKGB.nextInt();
				if(input > 0) {
					break;
				}
				else {
					displayError("Input must be larger than 0");
				}
			}
			else {
				displayError("Input must be a number");				
			}
		}

		readKGB.nextLine();
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
