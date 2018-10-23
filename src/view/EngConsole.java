package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import model.Boat;
import model.Member;
import model.Boat.BoatType;

public class EngConsole implements ViewInterface {

	private final int SHORT_PAUSE = 750;
	private final int MEDIUM_PAUSE = 1000;
	private final int LONG_PAUSE = 1500;

	// ---------------
	// Misc methods
	//--------------
	private void displayTitle(String title) {
		int cols = title.length() + 8;
		StringBuffer lines = new StringBuffer();
		for(int i = 0; i < cols; i ++) {
			lines.append("-");
		}
		System.out.println(lines + "\n" 
				+ " -- " + title + " -- \n"
				+ lines);
	}

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

	private void pause(int time) {
		try {
			Thread.sleep(time);
		}
		catch(InterruptedException e) {
		}
	}
	

	//----------------
	// Menu methods
	//--------------
	private void printMenuSeparation() {
		for(int i = 0; i < 25; i ++) {
			System.out.println();
		}
	}

	private void displayMenuOptions(Object[] options) {
		for (int i = 0; i < options.length - 1; i++) {
			System.out.println(i + 1 + ". " + options[i]);
		}
		System.out.println("0. " + options[options.length - 1]);
	}

	public int displayMainMenu() {
		printMenuSeparation();

		displayTitle("Main menu");
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
		printMenuSeparation();
		displayTitle("Add Member");
		
		String newName = promptForMemberName();
		if(newName.equals("0")) {
			return new String[] { "0" };
		}
		String newPNr = promptForMemberPNr();
		
		return new String[] { newName, newPNr };
	}

	// public String[] displayEditMember() {
	// 	printMenuSeparation();
	// 	displayTitle("Edit Member");
		
	// 	String newName = promptForMemberName();
	// 	String newPNr = promptForMemberPNr();

	// 	return new String[] { newName, newPNr };
	// }


	public String[] displayEditMember(Member m) { 
		String[] options = { "Edit name", "Edit personal code", "Back" };

		displayMenuOptions(options);
		int chosenOption = getMenuInput();
		
		// if(!isValidMenuChoice(chosenOption, options.length)) {
		//  	displayEditMemberMenu();
		// }
		String name = m.getName();
		String pNr = m.getPNr();
		switch(chosenOption) {
			case 1: name = promptForMemberName();
					break;
			case 2: pNr = promptForMemberPNr();
					break;
			case 0: break;
			default: displayInvalidMenuChoiceError();
					 displayEditMember(m);
		}
		return new String[] { name, pNr };
	}

	public void displayDeleteMember() {
		printMenuSeparation();
		displayTitle("Delete Member");
	}

	public void displayMembersList(ArrayList<Member> list) {
		printMenuSeparation();
		displayTitle("List Members");
		
		String[] options = { "View compact list", "View verbose list", "Back" };
		
		displayMenuOptions(options);
		int chosenOption = getMenuInput();

		switch(chosenOption) {
			case 1: displayMembersCompact(list);
					break;
			case 2: displayMembersVerbose(list);
					break;
			case 0: break;
			default: displayInvalidMenuChoiceError();
					 displayMembersList(list);
		}
	}
	
	public Object[] displayRegisterBoat(BoatType[] availableBoatTypes) {
		printMenuSeparation();
		displayTitle("Register Boat");

		int size = availableBoatTypes.length;
		Object[] menuOptions = new Object[size + 1];
		for(int i = 0; i < size; i ++) {
			menuOptions[i] = availableBoatTypes[i];
		}
		menuOptions[size] = "Back";

		displayMenuOptions(menuOptions);
		
		Object chosenBoatType = promptForBoatType(availableBoatTypes);
		if(chosenBoatType.equals(0)) {
			return new Object[] { "0" };
		}
		double enteredBoatSize = promptForBoatSize();
		
		return new Object[] { chosenBoatType, enteredBoatSize };
	}

	public int displayBoatSelection(ArrayList<Boat> availableBoats) {
		System.out.println(" -- Available boat(s) -- ");

		ArrayList<Object> menuOptions = new ArrayList<>(availableBoats);
		menuOptions.add("Cancel");
		displayMenuOptions(menuOptions.toArray());
		
		int chosenOption = displayBoatChoicePrompt();
		while(!isValidMenuChoice(chosenOption, availableBoats.size())) {
			displayInvalidMenuChoiceError();
			displayMenuOptions(availableBoats.toArray());
			chosenOption = displayBoatChoicePrompt();
		}
		return chosenOption;
	}

	public Object[] displayEditBoat(BoatType[] availableBoatTypes) {
		printMenuSeparation();
		displayTitle("Edit Boat");

		ArrayList<Object> menuOptions = new ArrayList<>(Arrays.asList(availableBoatTypes));
		menuOptions.add("Cancel");
		displayMenuOptions(menuOptions.toArray());
		
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

	private String promptForMemberName() {
		String newName = displayNamePrompt();
		while(!Member.isValidName(newName) && !newName.equals("0")) {
			displayInvalidNameError();
			newName = displayNamePrompt();
		}
		return newName;
	}
	
	private String promptForMemberPNr() {
		String newPNr = displayPNrPrompt();
		while(!Member.isValidPNr(newPNr)  && !newPNr.equals("0")) {
			displayInvalidPNrError();
			newPNr = displayPNrPrompt();
		}
		return newPNr;
	}

	private String displayNamePrompt() {
		System.out.print("Enter new name: ");
		return getInput();
	}
	
	private String displayPNrPrompt() {
		System.out.print("Enter new personal code number (YYMMDD-XXXX): ");
		return getInput();
	}

	private Object promptForBoatType(BoatType[] availableBoatTypes) {
		int chosenOption = displayBoatTypePrompt();
		if(!isValidMenuChoice(chosenOption, availableBoatTypes.length)) {
			displayInvalidMenuChoiceError();
			displayRegisterBoat(availableBoatTypes);
			return 0;
			// displayMenuOptions(menuOptions);
			// chosenOption = displayBoatTypePrompt();
		}
		else if(chosenOption == 0) {
			return 0;
		}
		else {
			Object chosenBoatType = availableBoatTypes[(chosenOption - 1)];
			return chosenBoatType;
		}
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
	private boolean isValidMenuChoice(int input, int max) {
		return input >= 0 && input <= max;
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
		System.out.println("  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .");
	}

	public void displayMemberInfo(Member member) {
		printMenuSeparation();

		displayTitle("View Member");
		System.out.format("%-12s %-26s %-16s \n","MEMBER ID","NAME","PERSONAL NUMBER");
		displayMemberFullInformation(member);
		
		displayWait();
	}	
	
	public void displayMembersVerbose(ArrayList<Member> list) {
		printMenuSeparation();

		displayTitle("List Members - Verbose");
		System.out.format("%-12s %-26s %-16s \n", "MEMBER ID", "NAME", "PERSONAL NUMBER");
		System.out.println("----------------------------------------------------------");
		for (Member member : list) {
			displayMemberFullInformation(member);
		}
		displayWait();
	}
	
	public void displayMembersCompact(ArrayList<Member> list) {
		printMenuSeparation();

		displayTitle("List Members - Compact");
		System.out.format("%-12s %-26s %-16s \n", "MemberID", "Name", "Number of boats");
		System.out.println("----------------------------------------------------------");
		for (Member member : list) {
			System.out.format("%-12s %-26s %-16s \n", member.getId(), member.getName(), member.getBoats().size());
		}
		displayWait();
	}

	// ---------
	// Titles
	//--------
	public void displayEditMemberTitle() {
		printMenuSeparation();
		displayTitle("Edit Member");
	}

	public void displayDeleteMemberTitle() {
		printMenuSeparation();
		displayTitle("Delete Member");
	}

	public void displayViewMemberTitle() {
		printMenuSeparation();
		displayTitle("View Member");
	}
	
	public void displayRegisterBoatTitle() {
		printMenuSeparation();
		displayTitle("Register Boat");
	}
	
	public void displayEditBoatTitle() {
		printMenuSeparation();
		displayTitle("Edit Boat");
	}

	public void displayRemoveBoatTitle() {
		printMenuSeparation();
		displayTitle("Remove Boat");
	}

	//------------
	// Messages
	//----------
	public void displayWelcomeMsg() {
		printMenuSeparation();
		
		System.out.println("###############################################################");
		System.out.println();
		System.out.println(" Welcome to \"The Jolly Pirates\" boat club's member registry! ");
		System.out.println();
		System.out.println("###############################################################");
		System.out.println("");
		pause(LONG_PAUSE);
	}

	public void displayExitMsg() {
		System.out.println("-- Ending session! --");
	}


	public void displayMemberCreatedConfirmation() {
		System.out.println("Member created\n");
		pause(MEDIUM_PAUSE);
	}

	public void displayMemberEditedConfirmation() {
		System.out.println("Member details has been changed!\n");
		pause(MEDIUM_PAUSE);
	}

	public void displayNameChangedConfirmation() {
		System.out.println("Name has been changed!");
		pause(MEDIUM_PAUSE);
	}
	
	public void displayPNrChangedConfirmation() {
		System.out.println("Personal code number has been changed!");
		pause(MEDIUM_PAUSE);
	}
	
	public void displayMemberDeletedConfirmation() {
		System.out.println("Member has been deleted!\n");
		pause(MEDIUM_PAUSE);
	}
	

	public void displayMemberHasNoBoatsMsg() {
		System.out.println("Member has no boats!");
		pause(MEDIUM_PAUSE);
	}

	public void displayBoatRegisteredConfirmation() {
		System.out.println("Boat has been registered!\n");
		pause(MEDIUM_PAUSE);
	}
	
	public void displayBoatEditedConfirmation() { // either this or the type & size should exist. not both
		System.out.println("Boat has been edited!\n");
		pause(MEDIUM_PAUSE);
	}
	
	// public void displayEditBoatTypeConfirm() {
	// 	System.out.println("Boat type has been changed!\n");
	// 	pause(MEDIUM_PAUSE);
	// }

	// public void displayEditBoatSizeConfirm() {
	// 	System.out.println("Boat size has been changed!\n");
	// 	pause(MEDIUM_PAUSE);
	// }

	public void displayBoatDeletedConfirmation() {
		System.out.println("Boat has been deleted!\n");
		pause(MEDIUM_PAUSE);
	}

	
	//----------
	// Errors
	//--------
	public void displayMemberDoesNotExistError() {
		System.out.println(" -- Member does not exist! --");
		pause(SHORT_PAUSE);
	}

	public void displayBoatDoesNotExistError() {
		System.out.println(" -- Boat does not exist --");
		pause(SHORT_PAUSE);
	}


	public void displayInvalidMenuChoiceError() {
		System.out.println("-- Invalid menu choice -- \n");
		pause(SHORT_PAUSE);
	}

	public void displayInvalidInputError() {
		System.out.println("-- Invalid input --");
		pause(SHORT_PAUSE);
	}


	public void displayInvalidNameError() {
		System.out.println("-- Name contains forbidden characters or has less than two characters --");
		pause(SHORT_PAUSE);
	}

	public void displayInvalidPNrError() {
		System.out.println("-- Personal code number must be on the form YYMMDD-XXXX --");
		pause(SHORT_PAUSE);
	}


	public void displayMessage(String m) { // should not be used
		System.out.println(m);
		displayWait();
	}

	public void displayError(String e) {
		System.out.println(e + "\n");
	}

}