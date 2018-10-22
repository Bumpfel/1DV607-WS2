package view;

import java.util.ArrayList;

import model.Member;

public interface ViewInterface {
	public int displayMemberIdPrompt();
	public String displayPasswordPrompt();

	public int displayGuestMainMenu();
	public int displayAdminMainMenu();

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
	public void displayInvalidPasswordError();

	// public void displayError(String e);
	
	public void displayTitle(Title action);

	public enum Title { ADD_MEMBER("Add Member"),
						EDIT_MEMBER("Edit Member"),
						VIEW_MEMBER("View Member"), 
						DELETE_MEMBER("Delete Member"), 
						LIST_MEMBERS("List Members"), 
						REGISTER_BOAT("Register Boat"),
						EDIT_BOAT("Edit Boat"),
						REMOVE_BOAT("Remove Boat"),
						LOG_IN("Log in")
						;
		String titleMsg;

		private Title(String action) {
			titleMsg = action;
		}

		public String getMsg() {
			int cols = titleMsg.length() + 8;
			StringBuffer lines = new StringBuffer();
			for(int i = 0; i < cols; i ++) {
				lines.append("-");
			}
			return lines + "\n" 
				   + " -- " + titleMsg + " -- \n"
				   + lines;
		}
	}

}
