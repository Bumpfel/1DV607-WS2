package view;

import java.util.ArrayList;

import model.Boat;
import model.Member;

public interface ViewInterface {

	public int displayMainMenu();
	public int displaySubMenu(String...options);
	// public int displayEditMemberMenu();
	
	// public String[] getEditMemberOptions();
	// public String[] getMainMenuOptions();
	// public String[] getListMemberOptions();
	
	public String getInput();
	public int getMenuInput();
	public int getInputInt();
	// public String getInputString();
	// public int getInputInt(int minimum);
	// public int getInputInt(int minimum, int maximum);

	public String[] displayAddMember();
	public String[] displayEditMember();
	// public String[] displayEditMemberMenu(String name, String pNr);
	// public String displayEditName();
	// public String displayEditPnr();
	// public String displayNewNameInput();
	// public String displayNewPNrInput();
	public int displayEnterMemberIdInput();
	public Boat displayEnterBoatInput(ArrayList<Boat> boats);
	
	// public int displayViewMemberListMenu();
	public void displayViewMembersList(ArrayList<Member> list);
	public void displayMembersVerbose(ArrayList<Member> list);
	public void displayMembersCompact(ArrayList<Member> list);
	
	public Object[] displayRegisterBoat(Object[] availableBoatTypes);
	// public Boat.BoatType displayEnterBoatType();
	// public double displayEnterBoatSize();
	
	public void displayWelcomeMsg();
	public void displayExitMsg();
	public void displayMemberCreatedConfirm();
	public void displayNameChangedConfirm();
	public void displayMemberEditedConfirm();
	public void displayPNrChangedConfirm();
	public void displayDeleteMemberConfirmation();
	public void displayEditBoatSizeConfirm();
	
	public void displayEditBoatTypeConfirm();
	public void displayBoatRegisteredConfirmation();
	public void displayDeleteBoatConfirm();

	public void displayInvalidMenuChoiceError();
	public void displayInvalidInputError();
	public void displayMemberDoesNotExistError();
	public void displayInvalidNameError();
	public void displayInvalidPNrError();
	
	public void displayMessage(String m);
	public void displayError(String e);
	
	public void displayViewMember(Member member);
	// public void displayBoatList(Member currentMember);
	public void displayBoatList(ArrayList<Boat> boats);
	public Object[] displayEditBoat(ArrayList<Boat> boats);
	// public int displayEditBoatMenu();
	
}
