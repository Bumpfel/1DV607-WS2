package view;

import java.util.ArrayList;

import model.Boat;
import model.Member;
import model.Boat.BoatType;

public interface ViewInterface {
	public int displayMemberIdPrompt();

	public int displayMainMenu();

	public void displayWelcomeMsg();
	public void displayExitMsg();

	public void displayEditMemberTitle();
	public void displayViewMemberTitle();
	public void displayDeleteMemberTitle();
	public void displayRegisterBoatTitle();
	public void displayEditBoatTitle();
	public void displayRemoveBoatTitle();

	public String[] displayAddMember();
	public String[] displayEditMember(Member member);
	
	public void displayMemberInfo(Member member);
	public void displayMembersList(ArrayList<Member> list);
	
	public int displayBoatSelection(ArrayList<Boat> availableBoats);
	public Object[] displayRegisterBoat(BoatType[] availableBoatTypes);
	public Object[] displayEditBoat(BoatType[] availableBoatTypes);
	// public Boat.BoatType displayEnterBoatType();
	// public double displayEnterBoatSize();
	
	public void displayMemberCreatedConfirmation();
	public void displayMemberEditedConfirmation();
	public void displayNameChangedConfirmation();
	public void displayPNrChangedConfirmation();
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
}
