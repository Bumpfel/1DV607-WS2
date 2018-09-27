package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.Boat;
import model.Member;

public class Console implements ViewInterface {

	public Console() {

	}

	public void displayWelcomeMsg() {
		System.out.println("Welcome to \"The Jolly Pirate\" boat club's member registry!");
	}

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

	public void displayMainMenu(ArrayList<String> options) {
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

	public int getInputInt(int minimum) {
		int input = -1;

		boolean validInput = false;
		while (!validInput) {
			input = this.getInputInt();

			if (input >= minimum) {
				validInput = true;
			} else {
				displayError("Input out of bound");
			}
		}

		return input;
	}

	public int getInputInt(int minimum, int maximum) {
		int input = -1;

		boolean validInput = false;
		while (!validInput) {
			input = this.getInputInt();

			if (input >= minimum && input <= maximum) {
				validInput = true;
			} else {
				displayError("Input out of bound");
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

		System.out.println("Member created");
		displayWait();

		return nameAndPnr;
	}

	
	
	
	
	
	// New experimental methods

	public String getInput() {
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(reader);
		String input = "";

		try {
			input = in.readLine();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}
	private boolean isValidMenuChoice(String input, int min, int max) {
		try {
			int inputInt = Integer.parseInt(input);
			if(inputInt >= min && inputInt <= max) {
				return true;
			}
			displayError("Invalid menu choice");
			return false;
		}
		catch(Exception e) {
			displayError("Invalid menu choice");
			return false;
		}
	}
	private boolean isValidStringInput(String input) {
		return !input.trim().isEmpty();
	}
	public int displayEditMemberMenu() {
		ArrayList<String> options = new ArrayList<String>();
		options.add("Edit name");
		options.add("Edit social security number");

		displayMenuOptions(options);

		String chosenOption = getInput();
		
		if(!isValidMenuChoice(chosenOption, 1, options.size())) {
			displayEditMemberMenu();
		}
		return Integer.parseInt(chosenOption);
	}
	
	public int displaySubMenu(String[] options) {
		System.out.println("using sub menu");
//		String[] editMemberOptions = { "Edit name", "Edit social security number" };
				
		displayMenuOptions_NEW(options);
		
		String chosenOption = getInput();
		
		if(!isValidMenuChoice(chosenOption, 1, options.length)) {
			displaySubMenu(options);
		}
		return Integer.parseInt(chosenOption);
	}	//
	
	
	public String[] getEditMemberArray() {
		return new String[] { "Edit name", "Edit social security number" };
//		return arr;
	}
	

	public int displayEditMemberMenu_original() {
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

	public int displayViewMemberListMenu() {
		ArrayList<String> options = new ArrayList<String>();
		int currentOption = -1;
		int numOfOptions = -1;

		options.add("View compact list");
		options.add("View verbose list");
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
		System.out.println("Enter new social security number:");
		return this.getInputString();
	}

	public int displayEnterMemberId() {
		System.out.println("Enter member ID:");
		return this.getInputInt();
	}

	public void displayDeleteMember() {
		System.out.println("Member was deleted!");
		displayWait();
	}

	private void displayMemberFullInformation(Member member) {
		System.out.format("%-12s %-26s %-16s \n", member.getId(), member.getName(), member.getPNr());
		ArrayList<Boat> memberBoats = member.getBoats();
		if(memberBoats.size() > 0) {
			System.out.format("\n%-11s %-26s %-16s \n"," > Boats", "Type", "Size (m)");
			for(Boat boat : memberBoats) {
				System.out.format("%-12s %-26s %.16s \n", "", boat.getType(), boat.getSize());
			}
			System.out.println(" Has " + memberBoats.size() + " boat(s) registered in total");
		}
	}

	public void displayViewMember(Member member) {
		System.out.format("%-12s %-26s %-16s \n","MemberID","Name","Number of boats");
		this.displayMemberFullInformation(member);

		displayWait();
	}	

	public void displayMembersVerbose(ArrayList<Member> list) {
		System.out.format("%-12s %-26s %-16s \n","MEMBER ID","NAME","PERSONAL NUMBER");

		for (Member member : list) {
			System.out.println("----------------------------------------------------------");
			displayMemberFullInformation(member);
		}
		displayWait();
	}

	public void displayMembersCompact(ArrayList<Member> list) {
		System.out.format("%-12s %-26s %-16s \n","MemberID","Name","Number of boats");

		for (Member member : list) {
			System.out.format("%-12s %-26s %-16s \n",member.getId(),member.getName(),member.getBoats().size());
		}
		displayWait();
	}

	public double displayBoatEnterSize() {
		double doubleInput = -1;
		System.out.println("Enter boat size (in meters):");

		boolean validInput = false;
		while (!validInput) {
			String input = this.getInputString();

			try {
				doubleInput = Double.valueOf(input);
				if (doubleInput <= 0)
					throw new IllegalArgumentException();
				validInput = true;
			}
			catch (IllegalArgumentException e) {
				System.out.println("Invalid input");
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
			String stringType = this.getInputString();

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

	public void displayBoatConfirm() {
		System.out.println("Boat was added!");
		displayWait();
	}

	public void displayBoatListCompact(Member currentMember) {
		ArrayList<Boat> boats = currentMember.getBoats();

		if (boats.size() == 0) {
			System.out.println("Member has no boats!");
		}
		else {
			System.out.println();
			for (int i = 0; i < boats.size(); i++) {
				Boat boat = boats.get(i);
				System.out.println(i + 1 + ". " + boat.getType());
			}
		}
	}

	public int displayEditBoatMenu() {
		System.out.println("1: Edit boat type\n"
				+ "2: Edit boat size");
		int menuChoice = this.getInputInt(1,2);
		return menuChoice;
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

	public Boat displayEnterBoat(ArrayList<Boat> boats) {
		System.out.println("Enter boat ID:");
		Boat currentBoat;

		int choiceAsInt = this.getInputInt(1, boats.size());

		currentBoat = boats.get(choiceAsInt - 1);

		return currentBoat;
	}

	public void displayMessage(String m) {
		System.out.println(m);
		displayWait();
	}

	public void displayError(String e) {
		System.out.println(e + "\n");
	}

	private void displayMenuOptions_NEW(String[] options) {
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

}