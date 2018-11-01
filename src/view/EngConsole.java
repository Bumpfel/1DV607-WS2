package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.Boat;
import model.Member;
import model.MemberRegistry;
import model.search.SearchCriteriaComposite;

public class EngConsole implements ViewInterface {

	private final int SHORT_PAUSE = 750;
	private final int MEDIUM_PAUSE = 1250;

	private EngMemberConsole memberConsole = new EngMemberConsole();;
	private EngBoatConsole boatConsole = new EngBoatConsole();;
	private EngSearchConsole searchConsole = new EngSearchConsole();;

	public final String GO_BACK_INPUT = "";
	public final int GO_BACK_INPUT_INT = -1;

	// ---------------
	// Misc methods
	//--------------
	void displayTitle(String title) {
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
	
	void printSeparation() {
		StringBuffer str = new StringBuffer();
		for(int i = 0; i < 25; i ++) {
			str.append("\n");
		}
		System.out.print(str);
	}

	//----------------
	// Menu methods
	//--------------
	void displayMenuOptions(Object[] options) {
		for (int i = 0; i < options.length - 1; i++) {
			System.out.println(i + 1 + ". " + options[i]);
		}
		System.out.println("0. " + options[options.length - 1]);
	}

	int createMenu(String title, String extraPrint, String[] options) {
		printSeparation();
		displayTitle(title);
		System.out.print(extraPrint);

		displayMenuOptions(options);
		return getMenuInput();
	}
	public GuestAction displayGuestMainMenu() {
		String[] options = { 
			"Admin log in",
			"View member",
			"List members",
			"Simple member search",
			"Complex member search",
			"Exit",
		};
		int choice = createMenu("Main menu", "", options);

		GuestAction[] mainActions = GuestAction.values();
		if(!isValidMenuChoice(choice, options.length - 1)) {
			displayInvalidMenuChoiceError();
			return GuestAction.INVALID_CHOICE;
		}
		return mainActions[choice];
	}

	public AdminAction displayAdminMainMenu() {
		String[] options = { 
			"Admin log out", 
			"Add member",
			"Edit member",
			"View member",
			"Delete member",
			"List members",
			"Register boat",
			"Edit boat",
			"Remove boat",
			"Simple member search",
			"Complex member search",
			"Exit",
		};

		int choice = createMenu("Main menu (Admin)", "", options);

		AdminAction[] mainActions = AdminAction.values();
		if(!isValidMenuChoice(choice, options.length - 1)) {
			displayInvalidMenuChoiceError();
			return AdminAction.INVALID_CHOICE;
		}
		return mainActions[choice];
	}
	
	public Member displayAddMember() {
		return memberConsole.displayAddMember(this);
	}

	public Member displayEditMember(Member m) {
		return memberConsole.displayEditMember(this, m);
	}

	public void displayDeleteMember(ArrayList<Member> membersList) {
		printSeparation();
		displayTitle("Delete Member");
	}

	public boolean displayMembersList(ArrayList<Member> membersList) {
		return memberConsole.displayMembersList(this, membersList);
	}

	public void displayMemberInfo(Member member) {
		memberConsole.displayMemberInfo(this, member);
	}

	public Boat displayRegisterBoat() {
		return boatConsole.displayRegisterBoat(this);
	}

	public Boat displayEditBoat(Boat boat) {
		return boatConsole.displayEditBoat(this, boat);
	}

	public Member displayMemberSelection(MemberRegistry memReg) {
		return memberConsole.displayMemberSelection(this, memReg);
	}

	public Boat displayBoatSelection(ArrayList<Boat> availableBoats) {
		return boatConsole.displayBoatSelection(this, availableBoats);
	}

	public void displaySearchResults(ArrayList<Member> list, SearchCriteriaComposite composite) {
		searchConsole.displaySearchResults(list, composite, this, memberConsole);
	}

	public SearchAction displaySearchFilters() {
		return searchConsole.displaySearchFilters(this);
	}

	public ComplexSearchAction complexSearch(String activeFilters) {
		return searchConsole.complexSearch(activeFilters, this);
	}


	public String getSearchString() {
		System.out.print("Enter search string: ");
		return getInput();
	}

	int promptForValidMenuInput(String txt, int maxOption) {
		System.out.print(txt);
		int chosenOption = getMenuInput();
		while(!isValidMenuChoice(chosenOption, maxOption)) {
			displayInvalidMenuChoiceError();
			chosenOption = getMenuInput();
		}
		return chosenOption;
	}


	//---------------------
	// Get input methods
	//-------------------
	public String getInput() {
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
	
	int getInputInt() {
		int input = GO_BACK_INPUT_INT;
		try {
			input = Integer.parseInt(getInput());
		}
		catch(NumberFormatException e) {
		}
		return input; 
	}

	int getMenuInput() {
		System.out.print(": ");
		return getInputInt();
	}

	double getInputDouble() {
		double input = GO_BACK_INPUT_INT;
		try {
			input = Double.parseDouble(getInput());
		}
		catch(NumberFormatException e) {
		}
		return input; 
	}
	
	// public int displayMemberIdPrompt() {
	// 	System.out.print("Enter member ID: ");
	// 	return getInputInt();
	// }
	
	public String displayPasswordPrompt() {
		System.out.print("Enter password: ");
		String input = getInput();
		if(input.equals(GO_BACK_INPUT))
			return null;
		return input;
	}



	//---------------------------
	// Console validity checks
	//-------------------------
	boolean isValidMenuChoice(int input, int max) {
		return input >= 0 && input <= max;
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

	public void displayBoatRemovedConfirmation() {
		displayMsg("Boat has been removed!");
	}

	public void displayLogInMsg() {
		displayMsg("Logging in...");
	}

	public void displayLogOutMsg() {
		displayMsg("Logging out...");
	}
	
	public void displayDuplicateSearchFilterReplacedMsg() {
		displayMsg("A duplicate filter was found and replaced");
	}

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
	
	public void displayInvalidPasswordError() {
		displayErrorMsg("Password is incorrect");
	}

	public void displayInvalidSearchParameterError() {
		displayErrorMsg("Cannot add filter. Invalid search parameter");
	}
	
	public void displayContainsIdenticalCriteria() {
		displayErrorMsg("Identical criteria found");
	}
}	
