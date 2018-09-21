package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import model.MemberRegistry;

public class Console implements ViewInterface {
	private MemberRegistry memberRegistry;
	
	public Console(MemberRegistry memberReg) {
		memberRegistry = memberReg;
	}

	public void displayWelcomeMsg() {
		System.out.println("Whalecum to \"The Jolly Pirate\" boatclub's member registry!");
	}
	
	public void displayMenu(ArrayList<String> options) {
		int numOfOptions = options.size();
		
		this.displayMenuOptions(options);
		
		System.out.println("What would you like to do?");
	}
	
	public String getInputString() {
	    InputStreamReader reader = new InputStreamReader(System.in);
	    BufferedReader in = new BufferedReader(reader);
		String input = "";
		
		boolean validInput = false;
		while (!validInput) {
			try {
				input = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (input.isEmpty() || input.charAt(0) == '\n') {
				System.err.println("Input cannot be empty! Try again:");
			}
			
			else {
				validInput = true;
			}
		}

		return input;
	}
	
	public int getInputInt() {
	    InputStreamReader reader = new InputStreamReader(System.in);
	    BufferedReader in = new BufferedReader(reader);
	    String stringInput = "";
	    int input = -1;
		
		boolean validInput = false;
		while (!validInput) {
			try {
				stringInput = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				input = Integer.parseInt(stringInput);
				
				validInput = true;
					
			} catch (NumberFormatException e) {
				displayError("Input have to be a number");
			}
		}
		
		return input;
	}

	public int getInputInt(int minimum, int maximum) {
	    InputStreamReader reader = new InputStreamReader(System.in);
	    BufferedReader in = new BufferedReader(reader);
	    String stringInput = "";
	    int input = -1;
		
		boolean validInput = false;
		while (!validInput) {
			try {
				stringInput = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				input = Integer.parseInt(stringInput);
				
				if (input >= minimum && input <= maximum) {
					validInput = true;
				} else {
					displayError("Input out of bound");
				}
			} catch (NumberFormatException e) {
				displayError("Input have to be a number");
			}
		}
		
		return input;
	}

	public String[] displayAddMember() {
		String[] nameAndPnr = new String[2];

		System.out.println("Enter new member's name: ");
		nameAndPnr[0] = this.getInputString();
		System.out.println("Enter new member's social security number: ");
		nameAndPnr[1] = this.getInputString();
		
		return nameAndPnr;
	}
	
	public int displayEditMemberMenu() {
		ArrayList<String> options = new ArrayList<String>();
		int currentOption = -1;
		int numOfOptions = -1;
		
		options.add("Edit name");
		options.add("Edit social security number");
		numOfOptions = options.size();
		
		this.displayMenuOptions(options);
		
		currentOption = this.getInputInt(1, numOfOptions);
		
		return currentOption;
	}
	
	public String displayEditName() {		
		System.out.println("Enter new name:");
		return this.getInputString();
	}
	
	public String displayEditPnr() {
		
		return "asd";
	}
	
	public int displayEnterMemberId() {
		System.out.println("Enter member ID:");
		return this.getInputInt();
	}
	
	public void displayMembersVerbose() {
		// TODO Auto-generated method stub
	}

	public void displayMembersCompact() {
		// TODO Auto-generated method stub
		
	}

	public void displayMemberReg() {
		// TODO Auto-generated method stub
	}
	
	public void displayMessage(String m) {
		System.out.println(m);
	}
	
	public void displayError(String e) {
		System.err.println(e+"\n");
	}
	
	private void displayMenuOptions(ArrayList<String> options) {
		int numOfOptions = options.size();
		
		for (int i = 0; i < numOfOptions; i++) {
			System.out.print(i+1+": "); //Displays the number of the option
			System.out.println(options.get(i)); //Displays the option
		}
	}

}
