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
		String input;
		int n;
		
		while (true) {
			System.out.println("Enter choice: ");
			input = readKGB.nextLine();
			try {
				n = Integer.parseInt(input);
				if (n <= 0) {
					System.out.println("Number must be large than 0");
				}
				else {
					break;
				}
			}
			catch (Exception e) {
				System.out.println("You must enter a numerical value");
			}
		}
		return n;
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
