package view;

import java.util.Scanner;
import java.util.ArrayList;

public class console implements ViewInterface {
	

	public void displayWelcomeMsg() {
		System.out.println("Whalecum to \"The Jolly Pirate\" boatclub's member registry!");
	}
	
	public void displayMenu() {
		int numOfOptions;
		ArrayList<String> options = new ArrayList<String>();
		
		numOfOptions = 9;
		
		options.add("Add member");
		options.add("Edit member");
		options.add("View member");
		options.add("Delete member");
		options.add("List members (Verbose");
		options.add("List members (Compact)");
		options.add("Register boat");
		options.add("Edit boat");
		options.add("Remove boat");
		
		for (int i = 0; i < numOfOptions; i++) {
			System.out.println(i+1); //Displays the number of the option
			System.out.println(options.get(i)); //Displays the option
		}
		
	}
	
	public String getInput() {
		String input = "";
		Scanner readKGB = new Scanner(System.in);

		input = readKGB.nextLine();
		readKGB.close();
		
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
