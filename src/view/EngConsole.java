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

	public String[] getMainMenuOptions() {
		return new String[] { "Add member", "Edit member", "View member", "Delete member", "List members", "Register Boat", "Edit boat", "Remove boat", "Exit" };	
	}

	public String[] getEditMemberOptions() {
		return new String[] { "Edit name", "Edit social security number" };
	}
	
	public String[] getListMemberOptions() {
		return new String[] { "View compact list", "View verbose list" };
	}
	
	
		
	
	//-----------------
	// Menu methods
	//-----------------
	public void displayMainMenu() {
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
		
		this.displayMenuOptions(options);

		// System.out.println("What would you like to do?");
	}
	
	public int displaySubMenu(String...options) {
		displayMenuOptions(options);
		
		return getInputInt();
	}
	
	
	private void displayMenuOptions(String[] options) {
		int numOfOptions = options.length;

		for (int i = 0; i < numOfOptions; i++) {
			System.out.print(i + 1 + ": "); //Displays the number of the option
			System.out.println(options[i]); //Displays the option
		}
	}
	
	private void displayMenuOptions(ArrayList<String> options) {
		int numOfOptions = options.size();
		
		for (int i = 0; i < numOfOptions; i++) {
			System.out.print(i + 1 + ": "); //Displays the number of the option
			System.out.println(options.get(i)); //Displays the option
		}
	}
	
	// Old menu methods
	public int displayEditMemberMenu() {
		ArrayList<String> options = new ArrayList<String>();
		options.add("Edit name");
		options.add("Edit social security number");

		displayMenuOptions(options);

		String chosenOption = getInput();
		
		// if(!isValidMenuChoice(chosenOption, 1, options.size())) {
		// 	displayEditMemberMenu();
		// }
		return Integer.parseInt(chosenOption);
	}
	
	// public int displayEditMemberMenu_original() {
	// 	ArrayList<String> options = new ArrayList<String>();
	// 	int currentOption = -1;
	// 	int numOfOptions = -1;
		
	// 	options.add("Edit name");
	// 	options.add("Edit social security number");
	// 	numOfOptions = options.size();

	// 	this.displayMenuOptions(options);

	// 	currentOption = this.getInputInt(1, numOfOptions);		

	// 	return currentOption;
	// }

	public int displayViewMemberListMenu() {
		ArrayList<String> options = new ArrayList<String>();
		int currentOption = -1;
		int numOfOptions = -1;

		options.add("View compact list");
		options.add("View verbose list");
		numOfOptions = options.size();

		this.displayMenuOptions(options);

		currentOption = this.getInputInt();

		return currentOption;
	}	
	
	public int displayEditBoatMenu() {
		System.out.println("1: Edit boat type\n"
						 + "2: Edit boat size");
		int menuChoice = this.getInputInt();
		return menuChoice;
	}
	

	
	//-------------------
	// Get input from kb methods
	//-------------------
	public String getInput() {
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(reader);
		String input = new String();

		System.out.print("> ");
		try {
			input = in.readLine();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}
	
	public int getInputInt() {
		int input = -1;
		try {
			input = Integer.parseInt(getInput());
		}
		catch(NumberFormatException e) {
			displayInvalidInputError();
		}
		return input; 
	}

		
	
	//-------
	// Inputs
	//-------

	public String[] displayAddMember() {
		String newName = new String();
		String newPNr = new String();

		while(!Member.isValidName(newName)) {
			System.out.println("Enter new name: ");
			newName = getInput();
		}
		while(!Member.isValidPNr(newPNr)) {
			System.out.println("Enter personal code number: ");
			newPNr = getInput();
		}

		return new String[] { newName, newPNr };
	}

	public String displayNewNameInput() {
		System.out.println("Enter new name:");
		return getInput();
	}

	public String displayNewPNrInput() {
		System.out.println("Enter new personal code number (YYMMDD-XXXX):");
		return getInput();
	}

	public String displayEnterMemberIdInput() {
		System.out.println("Enter member ID:");
		return getInput();
	}
	
	public double displayBoatEnterSize() {
		double doubleInput = -1;
		System.out.println("Enter boat size (in meters):");

		boolean validInput = false;
		while (!validInput) {
			String input = this.getInput();

			try {
				doubleInput = Double.valueOf(input);
				if (doubleInput <= 0)
					throw new IllegalArgumentException();
				validInput = true;
			}
			catch (IllegalArgumentException e) {
				displayInvalidInputError();
			}
		}
		return doubleInput;
	}

	public Boat.BoatType displayEnterBoatType() {
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

	
	
	//--------
	// Results
	//--------
	private void displayMemberFullInformation(Member member) {
		System.out.println("----------------------------------------------------------");
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
		System.out.format("%-12s %-26s %-16s \n","MEMBER ID","NAME","PERSONAL NUMBER");
		this.displayMemberFullInformation(member);

		displayWait();
	}	

	public void displayMembersVerbose(ArrayList<Member> list) {
		System.out.format("%-12s %-26s %-16s \n", "MEMBER ID", "NAME", "PERSONAL NUMBER");

		for (Member member : list) {
			displayMemberFullInformation(member);
		}
		displayWait();
	}

	public void displayMembersCompact(ArrayList<Member> list) {
		System.out.format("%-12s %-26s %-16s \n", "MemberID", "Name", "Number of boats");

		for (Member member : list) {
			System.out.format("%-12s %-26s %-16s \n", member.getId(), member.getName(), member.getBoats().size());
		}
		displayWait();
	}

	public void displayBoatListCompact(Member currentMember) {
		ArrayList<Boat> boats = currentMember.getBoats();

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
		System.out.println("Welcome to \"The Jolly Pirate\" boat club's member registry!");
	}

	public void displayMemberCreatedConfirm() {
		System.out.println("Member created");
		displayWait();
	}

	public void displayMemberDoesNotExistError() {
		System.out.println("Member does not exist!");
		displayWait();
	}

	public void displayNameChangedConfirm() {
		System.out.println("Name has been changed!");
		displayWait();
	}
	
	public void displayPNrChangedConfirm() {
		System.out.println("Social security number has been changed!");
	}
	
	public void displayDeleteMemberConfirmation() {
		System.out.println("Member was deleted!");
		displayWait();
	}
	
	public void displayBoatDoesNotExistError() {
		System.out.println("Boat does not exist");
		// displayWait();
	}
	public void displayBoatConfirm() {
		System.out.println("Boat was added!");
		displayWait();
	}

	public void displayDeleteBoatConfirm() {
		System.out.println("Boat has been deleted!");
		displayWait();
	}

	public void displayEditBoatTypeConfirm() {
		System.out.println("Boat type was changed!");
		displayWait();
	}

	public void displayEditBoatSizeConfirm() {
		System.out.println("Boat size was changed!");
		displayWait();
	}
		
	public void displayInvalidMenuChoiceError() {
		System.out.println("Invalid menu choice\n");
		pause();
		// displayWait();
	}

	public void displayInvalidNameError() {
		System.out.println("Name contains forbidden characters or has less than two characters");
	}

	public void displayInvalidPNrError() {
		System.out.println("Personal code number must be on the form YYMMDD-XXXX");
	}

	public void displayInvalidInputError() {
		System.out.println("Invalid input");
		// displayWait();
	}
	
	public void displayMessage(String m) {
		System.out.println(m);
		displayWait();
	}

	public void displayError(String e) {
		System.out.println(e + "\n");
	}

}