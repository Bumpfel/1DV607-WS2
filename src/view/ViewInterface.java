package view;

import java.util.ArrayList;

import model.Member;

public interface ViewInterface {
	public int displayMemberIdPrompt();

	public int displayMainMenu();

	public void displayWelcomeMsg();
	public void displayExitMsg();
	
	public String[] displayAddMember();
	public String[] displayEditMember();
	
	public void displayMemberInfo(Member member);
	public void displayMembersList(ArrayList<Member> list);
	
	public int displayBoatSelection(Object[] availableBoats);
	public Object[] displayRegisterBoat(Object[] availableBoatTypes);
	public Object[] displayEditBoat(Object[] availableBoatTypes);
	// public Boat.BoatType displayEnterBoatType();
	// public double displayEnterBoatSize();
	
	public void displayMemberCreatedConfirmation();
	public void displayMemberEditedConfirmation();
	// public void displayNameChangedConfirm();
	// public void displayPNrChangedConfirm();
	public void displayMemberDeletedConfirmation();

	public void displayMemberHasNoBoatsMsg();
	public void displayBoatRegisteredConfirmation();
	public void displayBoatEditedConfirmation();
	// public void displayEditBoatSizeConfirm();
	// public void displayEditBoatTypeConfirm();
	public void displayBoatDeletedConfirmation();

	public void displayInvalidMenuChoiceError();
	public void displayInvalidInputError();
	public void displayMemberDoesNotExistError();
	public void displayInvalidNameError();
	public void displayInvalidPNrError();

	// public void displayError(String e);
	
	public void displayBoatListCompact(ArrayList<Boat> boats);
	public Boat displayEnterBoat(ArrayList<Boat> boats);
	public int displayEditBoatMenu();
	
}
