package view;

import java.util.ArrayList;

import model.Boat;
import model.Member;

public interface ViewInterface {
	
//	public enum Msg {};
//	public enum MenuOptions {
//		EditName(""),
//		EditPNr(""),
//		CompactList(""),
//		VerboseList(""),
//		;
//		
//		private String option;
//		
//		private MenuOptions(String str) {
//		}
//		
//		public String getOption() {
//			return option;
//		}
//		
//	};

public void displayMainMenu();
	public int displaySubMenu(String...options);
	public int displayEditMemberMenu();
	
	public String[] getEditMemberOptions();
	public String[] getMainMenuOptions();
	public String[] getListMemberOptions();
	
	public String getInput(); // new
	public int getInputInt();
	// public String getInputString();
	// public int getInputInt(int minimum);
	// public int getInputInt(int minimum, int maximum);

	public String[] displayAddMember();
	// public String displayEditName();
	// public String displayEditPnr();
	public String displayNewNameInput();
	public String displayNewPNrInput();
	public String displayEnterMemberIdInput();
	public Boat displayEnterBoatInput(ArrayList<Boat> boats);
	
	public int displayViewMemberListMenu();
	public void displayMembersVerbose(ArrayList<Member> list);
	public void displayMembersCompact(ArrayList<Member> list);
	
	public Boat.BoatType displayEnterBoatType();
	public double displayBoatEnterSize();
	
	public void displayWelcomeMsg();
	public void displayMemberCreatedConfirm();
	public void displayNameChangedConfirm();
	public void displayPNrChangedConfirm();
	public void displayDeleteMemberConfirmation();
	public void displayEditBoatSizeConfirm();
	
	public void displayEditBoatTypeConfirm();
	public void displayBoatConfirm();
	public void displayDeleteBoatConfirm();

	public void displayInvalidMenuChoiceError();
	public void displayInvalidInputError();
	public void displayMemberDoesNotExistError();
	public void displayInvalidNameError();
	public void displayInvalidPNrError();
	
	public void displayMessage(String m);
	public void displayError(String e);
	
	public void displayViewMember(Member member);
	public void displayBoatListCompact(Member currentMember);
	public int displayEditBoatMenu();
	
}
