package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.Boat;
import model.Member;

public class EngConsole implements ViewInterface {

	// ---------------
	// Misc methods
	//--------------
	public void displayWait() {
		System.out.println("Press enter to continue...");

		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(reader);

		try {
			in.read();
		}
		catch(IOException e) {
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
	

	//----------------
	// Menu methods
	//--------------
	private void displayMenuOptions(Object[] options) {
		for (int i = 0; i < options.length; i++) {
			System.out.println(i + 1 + ". " + options[i]);
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
	}
	
	public String[] displayAddMember() {
		return promptForMemberDetails();
	}

	public String[] displayEditMember() {
		return promptForMemberDetails();
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

	public void displayMembersList(ArrayList<Member> list) {
		String[] options = { "View compact list", "View verbose list" };
		
		displayMenuOptions(options);
		int chosenOption = getMenuInput();

		switch(chosenOption) {
			case 1: displayMembersCompact(list);
					break;
			case 2: displayMembersVerbose(list);
					break;
			default: displayInvalidMenuChoiceError();
					 displayMembersList(list);
		}
	}
	
	public Object[] displayRegisterBoat(Object[] availableBoatTypes) {
		displayMenuOptions(availableBoatTypes);
		
		Object chosenBoatType = promptForBoatType(availableBoatTypes);
		double enteredBoatSize = promptForBoatSize();
		
		return new Object[] { chosenBoatType, enteredBoatSize };
	}

	public int displayBoatSelection(Object[] availableBoats) {
		System.out.println(" -- Available boat(s) -- ");

		displayMenuOptions(availableBoats);
		
		int chosenOption = displayBoatChoicePrompt();
		while(!isValidMenuChoice(chosenOption, 1, availableBoats.length)) {
			displayInvalidMenuChoiceError();
			displayMenuOptions(availableBoats);
			chosenOption = displayBoatChoicePrompt();
		}
		return chosenOption;
	}

	public Object[] displayEditBoat(Object[] availableBoatTypes) {
		displayMenuOptions(availableBoatTypes);
				
		Object chosenBoatType = promptForBoatType(availableBoatTypes);
		double enteredBoatSize = promptForBoatSize();

		return new Object[] { chosenBoatType, enteredBoatSize };
	}


	//---------------------
	// Get input methods
	//-------------------
	private String getInput() {
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(reader);
		String input = new String();

		try {
			input = in.readLine();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}
	
	private int getMenuInput() {
		System.out.print(": ");
		return getInputInt();
	}

	private int getInputInt() {
		int input = -1;
		try {
			input = Integer.parseInt(getInput());
		}
		catch(NumberFormatException e) {
		}
		return input; 
	}
	
	private double getInputDouble() {
		double input = -1;
		try {
			input = Double.parseDouble(getInput());
		}
		catch(NumberFormatException e) {
		}
		return input; 
	}
	
	
	//------------------
	// Input Prompts. Most of them are private methods made to avoid a small bit of code duplication
	//----------------
	public int displayMemberIdPrompt() {
		System.out.print("Enter member ID: ");
		return getInputInt();
	}


	private String[] promptForMemberDetails() {
		String newName = displayNamePrompt();
		while(!Member.isValidName(newName)) {
			displayInvalidNameError();
			newName = displayNamePrompt();
		}

		String newPNr = displayPNrPrompt();
		while(!Member.isValidPNr(newPNr)) {
			displayInvalidPNrError();
			newPNr = displayPNrPrompt();
		}

		return new String[] { newName, newPNr };
	}

	private String displayNamePrompt() {
		System.out.print("Enter new name: ");
		return getInput();
	
	}
	private String displayPNrPrompt() {
		System.out.print("Enter new personal code number (YYMMDD-XXXX): ");
		return getInput();
	}

	private Object promptForBoatType(Object[] availableBoatTypes) {
		int chosenOption = displayBoatTypePrompt();
		while(!isValidMenuChoice(chosenOption, 1, availableBoatTypes.length)) {
			displayInvalidMenuChoiceError();
			displayMenuOptions(availableBoatTypes);
			chosenOption = displayBoatTypePrompt();
		}
		Object chosenBoatType = availableBoatTypes[(chosenOption - 1)];
		return chosenBoatType;
	}

	private double promptForBoatSize() {
		double enteredBoatSize = displayBoatSizePrompt();
		while (enteredBoatSize <= 0) {	// Probably ought to use a validity method here
			displayInvalidInputError();
			enteredBoatSize = displayBoatSizePrompt();
		}
		return enteredBoatSize;
	}

	private int displayBoatChoicePrompt() {
		System.out.print("Choose boat: ");
		return getInputInt();
	}
	
	private int displayBoatTypePrompt() {
		System.out.print("Choose new boat type: ");
		return getInputInt();
	}

	private double displayBoatSizePrompt() {
		System.out.print("Enter new boat size (in meters): ");
		return getInputDouble();
	}


	//---------------------------
	// Console validity checks
	//-------------------------
	private boolean isValidMenuChoice(int input, int min, int max) {
		return input >= min && input <= max;
	}


	//----------------------------
	// Results (member details)
	//--------------------------
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

	public void displayMemberInfo(Member member) {
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
		System.out.format("%-12s %-26s %-16s \n", "MemberID", "Name", "Number of boats");
		System.out.println("----------------------------------------------------------");
		for (Member member : list) {
			System.out.format("%-12s %-26s %-16s \n", member.getId(), member.getName(), member.getBoats().size());
		}
		displayWait();
	}

	
	//------------
	// Messages
	//----------
	public void displayTitle(Title action) {
		System.out.println(action.getMsg());
	}

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


	public void displayMemberCreatedConfirmation() {
		System.out.println("Member created\n");
		pause();
	}

	public void displayMemberEditedConfirmation() {
		System.out.println("Member details has been changed!\n");
		pause();
	}

	// public void displayNameChangedConfirm() {
	// 	System.out.println("Name has been changed!");
	// 	pause();
	// }
	
	// public void displayPNrChangedConfirm() {
	// 	System.out.println("Personal code number has been changed!");
	// 	pause();
	// }
	
	public void displayMemberDeletedConfirmation() {
		System.out.println("Member has been deleted!\n");
		pause();
	}
	

	public void displayMemberHasNoBoatsMsg() {
		System.out.println("Member has no boats!");
		pause();
	}

	public void displayBoatRegisteredConfirmation() {
		System.out.println("Boat has been registered!\n");
		pause();
	}
	
	public void displayBoatEditedConfirmation() { // either this or the type, size should exist. not both
		System.out.println("Boat has been edited!\n");
		pause();
	}
	
	// public void displayEditBoatTypeConfirm() {
	// 	System.out.println("Boat type has been changed!\n");
	// 	pause();
	// }

	// public void displayEditBoatSizeConfirm() {
	// 	System.out.println("Boat size has been changed!\n");
	// 	pause();
	// }

	public void displayBoatDeletedConfirmation() {
		System.out.println("Boat has been deleted!\n");
		pause();
	}

	
	//----------
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
		System.out.println("-- Invalid menu choice -- \n");
		shortPause();
	}

	public void displayInvalidInputError() {
		System.out.println("-- Invalid input --");
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


	public void displayMessage(String m) { // should not be used
		System.out.println(m);
		displayWait();
	}

	public void displayError(String e) {
		System.out.println(e + "\n");
	}

}