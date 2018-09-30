package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import model.Boat;
import model.Member;

public class EngConsole implements ViewInterface {

	public void displayWait() {
		System.out.println("Press enter to continue...");

		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(reader);

		try {
			in.read();
		}
		catch(Exception e) {
		}
	}

	private void pause() {
		try {
			Thread.sleep(750);
		}
		catch(InterruptedException e) {
		}
	}

	private void shortPause() {
		try {
			Thread.sleep(500);
		}
		catch(InterruptedException e) {
		}
	}

	// public String[] getMainMenuOptions() {
	// 	return new String[] { "Add member", "Edit member", "View member", "Delete member", "List members", "Register Boat", "Edit boat", "Remove boat", "Exit" };	
	// }

	// public String[] getEditMemberOptions() {
	// 	return new String[] { "Edit name", "Edit social security number" };
	// }
	
	// public String[] getListMemberOptions() {
	// 	return new String[] { "View compact list", "View verbose list" };
	// }
	
	
		
	
	//-----------------
	// Menu methods
	//-----------------
	private void displayMenuOptions(String[] options) {
		int numOfOptions = options.length;

		for (int i = 0; i < numOfOptions; i++) {
			System.out.print(i + 1 + ": "); //Displays the number of the option
			System.out.println(options[i]); //Displays the option
		}
	}

	public int displayMainMenu() {
		System.out.println("-----------------");
		System.out.println(" -- Main Menu -- ");
		System.out.println("-----------------");
		String[] options = { "Add member",
							 "Edit member",
							 "View member",
							 "Delete member",
							 "List members",
							 "Register Boat",
							 "Edit boat",
							 "Remove boat",
							 "Exit",
							};
		
		displayMenuOptions(options);

		return getMenuInput();
		// System.out.println("What would you like to do?");
	}
	
	public int displaySubMenu(String...options) {
		displayMenuOptions(options);
		
		return getMenuInput();
	}
	
	public String[] displayAddMember() {
		System.out.println("------------------");
		System.out.println(" -- Add Member -- ");
		System.out.println("------------------");

		return promptForMemberDetails();
	}

	public String[] displayEditMember() {
		System.out.println("-------------------");
		System.out.println(" -- Edit Member -- ");
		System.out.println("-------------------");

		return promptForMemberDetails();
	}

	private String[] promptForMemberDetails() {
		String newName = displayEnterName();
		while(!Member.isValidName(newName)) {
			displayInvalidNameError();
			newName = displayEnterName();
		}

		String newPNr = displayEnterPNr();
		while(!Member.isValidPNr(newPNr)) {
			displayInvalidPNrError();
			newPNr = displayEnterPNr();
		}

		return new String[] { newName, newPNr };
	}

	// not used
	// public String[] displayEditMemberMenu(String name, String pNr) { 
	// 	String[] options = { "Edit name", "Edit social security number" };

	// 	displayMenuOptions(options);
	// 	int chosenOption = getMenuInput();
		
	// 	// if(!isValidMenuChoice(chosenOption, 1, options.length)) {
	// 	//  	displayEditMemberMenu();
	// 	// }
	// 	switch(chosenOption) {
	// 		case 1: name = displayNewNameInput();
	// 				break;	
	// 		case 2: pNr = displayNewPNrInput();
	// 				break;
	// 		default: displayInvalidMenuChoiceError();
	// 				 displayEditMemberMenu(name, pNr);
	// 	}
	// 	return new String[] { name, pNr };
	// }

	public void displayViewMembersList(ArrayList<Member> list) {
		String[] options = { "View compact list", "View verbose list" };
		
		System.out.println("---------------------");
		displayMenuOptions(options);
		int chosenOption = getMenuInput();

		switch(chosenOption) {
			case 1: displayMembersCompact(list);
					break;
			case 2: displayMembersVerbose(list);
					break;
			default: displayInvalidMenuChoiceError();
					 displayViewMembersList(list);
		}

	}
	
	public Object[] displayRegisterBoat(Object[] availableBoatTypes) {
		System.out.println("---------------------");
		System.out.println(" -- Register Boat -- ");
		System.out.println("---------------------");
		
		// Lists available boat types
		displayBoatList(availableBoatTypes);
		
		// Prompts to choose boat type
		int chosenOption = getMenuInput();
		while(!isValidMenuChoice(chosenOption, 1, availableBoatTypes.length)) {
			displayInvalidMenuChoiceError();
			displayBoatList(availableBoatTypes);
			chosenOption = getMenuInput();
		}
		Object chosenBoatType = availableBoatTypes[(chosenOption - 1)];
		
		// Prompts to enter size
		double enteredBoatSize = displayEnterBoatSize();
		while (enteredBoatSize <= 0) {
			displayInvalidInputError();
			enteredBoatSize = displayEnterBoatSize();
		}
		
		return new Object[] { chosenBoatType, enteredBoatSize };
	}

	private void displayBoatList(Object[] availableBoatTypes) {
		for(int i = 0; i < availableBoatTypes.length; i ++) {
			System.out.println((i + 1) + ". " + availableBoatTypes[i]);
		}
	}

	public Object[] displayEditBoat(ArrayList<Boat> boats) {
		
		for(int i = 0; i < boats.size(); i ++) {
			System.out.println(i + 1 + ". " + Boat.BoatType.values()[i]);
		}

		int menuChoice = getMenuInput();

		switch(menuChoice) {
			case 1: 

		}

		// double newSize = displayEnterBoatSize();
		// while(!Member.isValidPNr(newSize)) {
		// 	displayInvalidPNrError();
		// 	newSize = displayNewPNrInput();
		// }

		return new String[] { };
		
	}
	



	

	
	// private void displayMenuOptions(ArrayList<String> options) {
	// 	int numOfOptions = options.size();
		
	// 	for (int i = 0; i < numOfOptions; i++) {
	// 		System.out.print(i + 1 + ": "); //Displays the number of the option
	// 		System.out.println(options.get(i)); //Displays the option
	// 	}
	// }
		


	
	//-------------------
	// Get input from kb methods
	//-------------------
	public String getInput() {
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(reader);
		String input = new String();

		// System.out.print("> ");
		try {
			input = in.readLine();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}
	
	/**
	 * Returns an int from kb input. Exceptions are silenced.
	 */
	public int getMenuInput() {
		System.out.print(": ");
		int input = -1;
		try {
			input = Integer.parseInt(getInput());
		}
		catch(NumberFormatException e) {
		}
		return input;
	}

	/**
	 * Returns an int from kb input or shows invalid input error if kb input is not an int
	 */
	public int getInputInt() {
		int input = -1;
		try {
			input = Integer.parseInt(getInput());
		}
		catch(NumberFormatException e) {
			// displayInvalidInputError();
		}
		return input; 
	}
	
	public double getInputDouble() {
		double input = -1;
		try {
			input = Double.parseDouble(getInput());
		}
		catch(NumberFormatException e) {
			// displayInvalidInputError();
		}
		return input; 
	}
		
	
	//-------
	// Simple inputs
	//-------
	// public String displayNewNameInput() {
	// 	System.out.print("Enter new name: ");
	// 	return getInput();
	// }

	// public String displayNewPNrInput() {
	// 	System.out.print("Enter new personal code number (YYMMDD-XXXX): ");
	// 	return getInput();
	// }
	
	public int displayEnterMemberIdInput() {
		System.out.print("Enter member ID: ");
		return getInputInt();
	}

	private String displayEnterName() {
		System.out.print("Enter new name: ");
		return getInput();
	
	}
	private String displayEnterPNr() {
		System.out.print("Enter new personal code number (YYMMDD-XXXX): ");
		return getInput();
	}

	private double displayEnterBoatSize() {
		System.out.print("Enter boat size (in meters): ");
		return getInputDouble();
	}

	public Boat.BoatType displayEnterBoatType_() {
		Boat.BoatType boatType = Boat.BoatType.Other;

		System.out.println("Available boat-types: ");

		//Saves types for ordinal check and prints the types
		ArrayList<Boat.BoatType> boatTypes = new ArrayList<Boat.BoatType>();
		for (Boat.BoatType type : Boat.BoatType.values()) {
			boatTypes.add(type);
			System.out.println(type.ordinal()+1+". "+type);
		}

		System.out.println("Enter boat type: ");
		boolean validType = false;
		while (!validType) {
			int choiceAsInt = -1;
			String stringType = this.getInput();

			try {
				try {
					choiceAsInt = Integer.valueOf(stringType);
				} catch (NumberFormatException e) {

				}

				if (choiceAsInt >= 1 && choiceAsInt <= boatTypes.size()) {
					boatType = boatTypes.get(choiceAsInt-1);
					validType = true;
				}

				else {
					boatType = Enum.valueOf(Boat.BoatType.class,stringType);
					validType = true;
				}
			} catch (IllegalArgumentException e) {
				System.out.println("Not a correct boat type. Try again: ");
			}
		}

		return boatType;
	}

	public Boat displayEnterBoatInput(ArrayList<Boat> boats) {
		System.out.println("Enter boat ID:");
		int input = getInputInt();
		Boat boat = null;
		try {
			boat = boats.get(input - 1);
		}
		catch(NoSuchElementException e) {
			displayBoatDoesNotExistError();
		}
		return boat;
	}


	
	private boolean isValidMenuChoice(int input, int min, int max) {
		return input >= min && input <= max;
	}

	private boolean isValidMenuChoice(String input, int min, int max) {
		try {
			int inputInt = Integer.parseInt(input);
			if(inputInt >= min && inputInt <= max) {
				return true;
			}
			displayInvalidMenuChoiceError();
			return false;
		}
		catch(Exception e) {
			displayInvalidMenuChoiceError();
			return false;
		}
	}


	
	//--------
	// Results
	//--------
	private void displayMemberFullInformation(Member member) {
		System.out.format("%-12s %-26s %-16s \n", member.getId(), member.getName(), member.getPNr());
		ArrayList<Boat> memberBoats = member.getBoats();
		if(memberBoats.size() > 0) {
			System.out.format("\n%-11s %-26s %-16s \n"," > Boats", "Type", "Size (m)");
			for(Boat boat : memberBoats) {
				System.out.format("%-12s %-26s %.16s \n", "", boat.getType(), boat.getSize());
			}
			System.out.println(" Member has " + memberBoats.size() + " boat(s) registered in total");
		}
		else {
			System.out.println(" Member has no registered boats");
		}
		System.out.println("----------------------------------------------------------");
	}

	public void displayViewMember(Member member) {
		System.out.println("----------------------");
		System.out.println(" -- Member details -- ");
		System.out.println("----------------------");
		System.out.format("%-12s %-26s %-16s \n","MEMBER ID","NAME","PERSONAL NUMBER");
		displayMemberFullInformation(member);
		
		displayWait();
	}	
	
	public void displayMembersVerbose(ArrayList<Member> list) {
		System.out.format("%-12s %-26s %-16s \n", "MEMBER ID", "NAME", "PERSONAL NUMBER");
		System.out.println("----------------------------------------------------------");
		for (Member member : list) {
			displayMemberFullInformation(member);
		}
		displayWait();
	}
	
	public void displayMembersCompact(ArrayList<Member> list) {
		System.out.println("-----------------------");
		System.out.println(" -- List of members -- ");
		System.out.println("-----------------------");
		System.out.format("%-12s %-26s %-16s \n", "MemberID", "Name", "Number of boats");
		System.out.println("----------------------------------------------------------");
		for (Member member : list) {
			System.out.format("%-12s %-26s %-16s \n", member.getId(), member.getName(), member.getBoats().size());
		}
		displayWait();
	}

	public void displayBoatList(ArrayList<Boat> boats) { // private? if used at all
		// ArrayList<Boat> boats = currentMember.getBoats();

		if (boats.size() == 0) {
			System.out.println("Member has no boats!");
		}
		else {
			System.out.println();
			for (int i = 0; i < boats.size(); i ++) {
				Boat boat = boats.get(i);
				System.out.println(i + 1 + ". " + boat.getType());
			}
		}
	}
	

	
	//---------
	// Messages
	//---------
	public void displayWelcomeMsg() {
		System.out.println("##############################################################");
		System.out.println();
		System.out.println(" Welcome to \"The Jolly Pirate\" boat club's member registry! ");
		System.out.println();
		System.out.println("##############################################################");
	}

	public void displayExitMsg() {
		System.out.println("-- Ending session! --");
	}

	public void displayMemberCreatedConfirm() {
		System.out.println("Member created\n");
		pause();
	}

	public void displayMemberEditedConfirm() {
		System.out.println("Member details has been changed!\n");
		pause();
	}

	public void displayNameChangedConfirm() {
		System.out.println("Name has been changed!");
		pause();
	}
	
	public void displayPNrChangedConfirm() {
		System.out.println("Personal code number has been changed!");
		pause();
	}
	
	public void displayDeleteMemberConfirmation() {
		System.out.println("Member has been deleted!\n");
		pause();
	}

	public void displayBoatRegisteredConfirmation() {
		System.out.println("Boat has been registed!\n");
		pause();
	}

	public void displayDeleteBoatConfirm() {
		System.out.println("Boat has been deleted!\n");
		pause();
	}

	public void displayEditBoatTypeConfirm() {
		System.out.println("Boat type has been changed!\n");
		pause();
	}

	public void displayEditBoatSizeConfirm() {
		System.out.println("Boat size has been changed!\n");
		pause();
	}
		
	//--------
	// Errors
	//--------
	public void displayMemberDoesNotExistError() {
		System.out.println(" -- Member does not exist! --");
		shortPause();
	}

	public void displayBoatDoesNotExistError() {
		System.out.println(" -- Boat does not exist --");
		shortPause();
	}

	public void displayInvalidMenuChoiceError() {
		System.out.println("-- Invalid menu choice --\n");
		shortPause();
	}

	public void displayInvalidNameError() {
		System.out.println("-- Name contains forbidden characters or has less than two characters --");
		shortPause();
	}

	public void displayInvalidPNrError() {
		System.out.println("-- Personal code number must be on the form YYMMDD-XXXX --");
		shortPause();
	}

	public void displayInvalidInputError() {
		System.out.println("-- Invalid input --");
		shortPause();
	}

	public void displayMessage(String m) { // should not be used
		System.out.println(m);
		displayWait();
	}

	public void displayError(String e) {
		System.out.println(e + "\n");
	}

}