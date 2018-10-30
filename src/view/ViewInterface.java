package view;

import java.util.ArrayList;

import model.Boat;
import model.Member;
import model.MemberRegistry;

public interface ViewInterface {

	public Member displayMemberSelection(MemberRegistry memReg);
	public Boat displayBoatSelection(ArrayList<Boat> availableBoats);

	public MainAction displayMainMenu();

	public void displayWelcomeMsg();
	public void displayExitMsg();

	public void displayEditMemberTitle();
	public void displayViewMemberTitle();
	public void displayRegisterBoatTitle();
	public void displayEditBoatTitle();
	public void displayRemoveBoatTitle();
	
	public Member displayAddMember();
	public Member displayEditMember(Member member);
	public void displayDeleteMember(ArrayList<Member> membersList);
	
	public void displayMemberInfo(Member member);
	public boolean displayMembersList(ArrayList<Member> memberList);
	
	public Boat displayRegisterBoat();
	public Boat displayEditBoat(Boat boat);
	
	public void displayMemberCreatedConfirmation();
	public void displayNameChangedConfirmation();
	public void displayPNrChangedConfirmation();
	public void displayMemberDeletedConfirmation();

	public void displayMemberHasNoBoatsMsg();
	public void displayBoatRegisteredConfirmation();
	public void displayBoatTypeEditedConfirmation();
	public void displayBoatSizeEditedConfirmation();
	public void displayBoatDeletedConfirmation();

	public void displayMemberDoesNotExistError();

	public enum MainAction { EXIT,
							ADD_MEMBER, 
							EDIT_MEMBER, 
							VIEW_MEMBER, 
							DELETE_MEMBER, 
							LIST_MEMBERS, 
							REGISTER_BOAT, 
							EDIT_BOAT, 
							REMOVE_BOAT,
							INVALID_CHOICE, 
	};
}
