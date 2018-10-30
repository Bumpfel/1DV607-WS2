package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import model.Boat;
import model.Member;
import model.MemberRegistry;
import model.Boat.BoatType;

public class EngConsole implements ViewInterface {

	private final int SHORT_PAUSE = 750;
	private final int MEDIUM_PAUSE = 1250;

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
	
	private void printSeparation() {
		for(int i = 0; i < 25; i ++) {
			System.out.println();
		}
	}

	//----------------
	// Menu methods
	//--------------
	private void displayMenuOptions(Object[] options) {
		for (int i = 0; i < options.length - 1; i++) {
			System.out.println(i + 1 + ". " + options[i]);
		}
		System.out.println("0. " + options[options.length - 1]);
	}

	public MainAction displayMainMenu() {
		printSeparation();

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

		MainAction[] mainActions = MainAction.values();
		int choice = getMenuInput();
		if(!isValidMenuChoice(choice, mainActions.length - 2)) {
			displayInvalidMenuChoiceError();
			return MainAction.INVALID_CHOICE;
		}
		return mainActions[choice];
	}
	
	public Member displayAddMember() {
		printSeparation();
		displayTitle("Add Member");
		
		String newName = promptForValidMemberName();
		if(newName == null) {
			return null;
		}
		String newPNr = promptForValidMemberPNr();
		if(newPNr == null) {
			return null;
		}

		return new Member(newName, newPNr);
	}

	public Member displayEditMember(Member m) {
		printSeparation();

		displayTitle("Edit \'" + m + "\'");

		Member tempMember = new Member(m.getName(), m.getPNr());
		String[] options = { "Edit name", "Edit personal code", "Back" };

		displayMenuOptions(options);
		
		int chosenOption = promptForValidMenuInput("", options.length - 1);

		switch(chosenOption) {
			case 1: 
				String name = promptForValidMemberName();
				if(name != null)
					tempMember.editName(name.trim());
				break;
			case 2:
				String pNr = promptForValidMemberPNr();
				if(pNr != null)
					tempMember.editPNr(pNr);
				break;
			case 0:
				return null;
		}
		return tempMember;
	}

	public void displayDeleteMember(ArrayList<Member> membersList) {
		printSeparation();
		displayTitle("Delete Member");
	}

	public boolean displayMembersList(ArrayList<Member> membersList) {
		printSeparation();
		displayTitle("List Members");
		
		String[] options = { "View compact list", "View verbose list", "Back" };
		
		displayMenuOptions(options);
		int chosenOption = getMenuInput();

		switch(chosenOption) {
			case 1:
				printSeparation();
				displayTitle("List Members - Verbose");
				displayMembersCompact(membersList);
				displayWait();
				break;
			case 2: 
				printSeparation();
				displayTitle("List Members - Verbose");
				displayMembersVerbose(membersList);
				displayWait();
				break;
			case 0:
				return false;
			default:
				displayInvalidMenuChoiceError();
		}
		return true;
	}
	
	public Boat displayRegisterBoat() {
		printSeparation();
		displayTitle("Register Boat");

		BoatType[] availableBoatTypes = BoatType.values();
		ArrayList<Object> menuOptions = new ArrayList<>(Arrays.asList(availableBoatTypes));
		menuOptions.add("Cancel");
		displayMenuOptions(menuOptions.toArray());
		
		int chosenOption = promptForValidMenuInput("Choose boat type", menuOptions.size() - 1);
		if(chosenOption == 0) {
			return null;
		}
		BoatType chosenBoatType = availableBoatTypes[chosenOption - 1];
		
		double enteredBoatSize = promptForValidBoatSize();
		if(enteredBoatSize == 0) {
			return null;
		}
		
		return new Boat(chosenBoatType, enteredBoatSize);
	}

	public Boat displayEditBoat(Boat currentBoat) {
		printSeparation();
		displayTitle("Edit \'" + currentBoat + "\'");

		String[] menuOptions = { "Edit Type", "Edit Size", "Back" };
		displayMenuOptions(menuOptions);

		int chosenOption = promptForValidMenuInput("", menuOptions.length - 1);
		Boat tempBoat = new Boat(currentBoat.getType(), currentBoat.getSize());

		if(chosenOption == 1) {
			printSeparation();
			displayTitle("Select new type");
			BoatType[] availableBoatTypes = BoatType.values();
			ArrayList<Object> menuOptions2 = new ArrayList<>(Arrays.asList(availableBoatTypes));
			menuOptions2.add("Cancel");
			displayMenuOptions(menuOptions2.toArray());

			int chosenOption2 = promptForValidMenuInput("", menuOptions2.size() - 1);
			if(chosenOption2 == 0) {
				return null;
			}
			BoatType chosenBoatType = availableBoatTypes[chosenOption2 - 1];
			tempBoat.editType(chosenBoatType);
		}
		else if(chosenOption == 2) {
			printSeparation();
			displayTitle("Enter new size");
			double enteredBoatSize = promptForValidBoatSize();
			if(enteredBoatSize == 0)
				return null;
			tempBoat.editSize(enteredBoatSize);
		}
		else {
			return null;
		}
		
		return tempBoat;
	}

	//------------------
	// Prompt loops. These loop until they get a valid input
	//----------------
	private int promptForValidMenuInput(String txt, int maxOption) {
		System.out.print(txt);
		int chosenOption = getMenuInput();
		while(!isValidMenuChoice(chosenOption, maxOption)) {
			displayInvalidMenuChoiceError();
			chosenOption = getMenuInput();
		}
		return chosenOption;
	}

	public String promptForValidMemberName() {
		String newName = displayNamePrompt();
		while(!Member.isValidName(newName)) {
			if(newName.equals("0"))
				return null;
				displayInvalidNameError();
				newName = displayNamePrompt();
			}
			return newName;
		}
		
	public String promptForValidMemberPNr() {
		String newPNr = displayPNrPrompt();
		while(!Member.isValidPNr(newPNr)) {
			if(newPNr.equals("0"))
				return null;
			displayInvalidPNrError();
			newPNr = displayPNrPrompt();
		}
		return newPNr;
	}

	private double promptForValidBoatSize() {
		double enteredBoatSize = displayBoatSizePrompt();
		while (enteredBoatSize < 0) {
			displayInvalidInputError();
			enteredBoatSize = displayBoatSizePrompt();
		}
		return enteredBoatSize;
	}

	public Member displayMemberSelection(MemberRegistry memReg) {
		System.out.print("Enter member ID: ");
		int input = getInputInt();
		while(!memReg.memberExists(input)) {
			if(input == 0)
				return null;
			displayMemberDoesNotExistError();
			System.out.print("Enter member ID: ");
			input = getInputInt();
		}
		return memReg.getMember(input);
	}

	public Boat displayBoatSelection(ArrayList<Boat> availableBoats) {
		printSeparation();
		displayTitle("Select boat to edit");

		ArrayList<Object> menuOptions = new ArrayList<>(availableBoats);
		menuOptions.add("Cancel");
		displayMenuOptions(menuOptions.toArray());
		
		int chosenOption = displayBoatChoicePrompt();
		while(!isValidMenuChoice(chosenOption, availableBoats.size())) {
			if(chosenOption == 0)
				return null;
			displayInvalidMenuChoiceError();
			displayMenuOptions(menuOptions.toArray());
			chosenOption = displayBoatChoicePrompt();
		}
		return availableBoats.get(chosenOption - 1);
	}

	//------------------
	// Input Prompts. Most of them are private methods made to avoid a small bit of code duplication
	//----------------
	private String displayNamePrompt() {
		System.out.print("Enter new name: ");
		return getInput();
	}
	
	private String displayPNrPrompt() {
		System.out.print("Enter new personal code number (YYMMDD-XXXX): ");
		return getInput();
	}

	private int displayBoatChoicePrompt() {
		System.out.print("Choose boat: ");
		return getInputInt();
	}
	
	// private int displayBoatTypePrompt() {
	// 	System.out.print("Choose new boat type: ");
	// 	return getInputInt();
	// }

	private double displayBoatSizePrompt() {
		System.out.print("Enter new boat size (in meters): ");
		return getInputDouble();
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
	
	private int getInputInt() {
		int input = -1;
		try {
			input = Integer.parseInt(getInput());
		}
		catch(NumberFormatException e) {
		}
		return input; 
	}

	private int getMenuInput() {
		System.out.print(": ");
		return getInputInt();
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
		printSeparation();

		displayTitle("View Member");
		System.out.format("%-12s %-26s %-16s \n","MEMBER ID","NAME","PERSONAL NUMBER");
		displayMemberFullInformation(member);

		displayWait();
	}	
	
	private void displayMembersVerbose(ArrayList<Member> list) {
		System.out.format("%-12s %-26s %-16s \n", "MEMBER ID", "NAME", "PERSONAL NUMBER");
		System.out.println("----------------------------------------------------------");
		for (Member member : list) {
			displayMemberFullInformation(member);
		}
	}
	
	private void displayMembersCompact(ArrayList<Member> list) {
		System.out.format("%-12s %-26s %-16s \n", "MemberID", "Name", "Number of boats");
		System.out.println("----------------------------------------------------------");
		for (Member member : list) {
			System.out.format("%-12s %-26s %-16s \n", member.getId(), member.getName(), member.getBoats().size());
		}
	}

	// ---------
	// Titles
	//--------
	public void displayAddMemberTitle() {
		printSeparation();
		displayTitle("Add Member");
	}

	public void displayEditMemberTitle() {
		printSeparation();
		displayTitle("Edit Member");
	}

	public void displayDeleteMemberTitle() {
		printSeparation();
		displayTitle("Delete Member");
	}

	public void displayViewMemberTitle() {
		printSeparation();
		displayTitle("View Member");
	}
	
	public void displayRegisterBoatTitle() {
		printSeparation();
		displayTitle("Register Boat");
	}
	
	public void displayEditBoatTitle() {
		printSeparation();
		displayTitle("Edit Boat");
	}

	public void displayRemoveBoatTitle() {
		printSeparation();
		displayTitle("Remove Boat");
	}
	

	//------------
	// Messages
	//----------
	
	public void displayWelcomeMsg() {
		printSeparation();
		
		System.out.println("###############################################################");
		System.out.println();
		System.out.println(" Welcome to \"The Jolly Pirates\" boat club's member registry! ");
		System.out.println();
		System.out.println("###############################################################");
		System.out.println("");
		displayWait();
	}

	public void displayExitMsg() {
		System.out.println("-- Ending session! --");
	}


	/**
	 * Dicates how messages should be displayed
	 * @param msg
	 */
	private void displayMsg(String msg) {
		printSeparation();
		System.out.println(msg);
		pause(MEDIUM_PAUSE);
	}

	public void displayMemberCreatedConfirmation() {
		displayMsg("Member created");
	}

	public void displayMemberEditedConfirmation() {
		displayMsg("Member details has been changed!");
	}

	public void displayNameChangedConfirmation() {
		displayMsg("Name has been changed!");
	}
	
	public void displayPNrChangedConfirmation() {
		displayMsg("Personal code number has been changed!");
	}
	
	public void displayMemberDeletedConfirmation() {
		displayMsg("Member has been deleted!");
	}

	public void displayMemberHasNoBoatsMsg() {
		displayMsg("Member has no boats!");
	}

	public void displayBoatRegisteredConfirmation() {
		displayMsg("Boat has been registered!");
	}
	
	public void displayBoatTypeEditedConfirmation() {
		displayMsg("Boat type has been changed!");
	}

	public void displayBoatSizeEditedConfirmation() {
		displayMsg("Boat size has been changed!");
	}

	public void displayBoatDeletedConfirmation() {
		displayMsg("Boat has been deleted!");
	}

	// public void displayNoChangesMadeMsg() {
	// 	displayMsg("No changes was made");
	// }
	 
	//----------
	// Errors
	//--------
	private void displayErrorMsg(String errorMsg) {
		System.out.println("-- " + errorMsg + " --");
		pause(SHORT_PAUSE);
	}


	public void displayMemberDoesNotExistError() {
		displayErrorMsg("Member does not exist!");
	}

	public void displayBoatDoesNotExistError() {
		displayErrorMsg("Boat does not exist");
	}

	public void displayInvalidMenuChoiceError() {
		displayErrorMsg("Invalid menu choice");
	}

	public void displayInvalidInputError() {
		displayErrorMsg("Invalid input");
	}

	public void displayInvalidNameError() {
		displayErrorMsg("Name contains forbidden characters or has less than two characters");
	}

	public void displayInvalidPNrError() {
		displayErrorMsg("Personal code number must be valid and on the form YYMMDD-XXXX");
	}

}