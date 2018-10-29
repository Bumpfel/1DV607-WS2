package controller;

import java.util.ArrayList;

import model.Boat;
import model.Member;
import model.MemberRegistry;
import model.Boat.BoatType;
import view.ViewInterface;
import view.ViewInterface.MainAction;

public class Admin {

	private ViewInterface view;
	private MemberRegistry memberRegistry;
	private Member currentMember;
	private Boat currentBoat;
	private MainAction currentAction;
	
	public Admin(MemberRegistry memberReg, ViewInterface inView) {
		view = inView;
		memberRegistry = memberReg;
	}

	public void startApplication() {
		view.displayWelcomeMsg();
		decideAction();
	}

	private void decideAction() {
		if(currentAction == null)
			currentAction = view.displayMainMenu();
		
		switch(currentAction) {
			case ADD_MEMBER: 
				addMember();
				break;
			case EDIT_MEMBER: 
				editMember();
				break;
			case VIEW_MEMBER:
				viewMember();
				break;
			case DELETE_MEMBER: 
				deleteMember();
				break;
			case LIST_MEMBERS:
				listMembers();
				break;
			case REGISTER_BOAT: 
				registerBoat();
				break;
			case EDIT_BOAT: 
				editBoat();
				break;
			case REMOVE_BOAT: 
				removeBoat();
				break;
			case EXIT: 
				exit();
				break;
			case INVALID_CHOICE:
				currentAction = null;
				decideAction(); // recursive call
				break;
		}
	}

	
	private void addMember() {
		Member newMember = view.displayAddMember();
		if(newMember == null) {
			currentMember = null;
			currentAction = null;
			decideAction();
			return;
		}

		memberRegistry.addMember(newMember);
		view.displayMemberCreatedConfirmation();
		currentAction = null;
		decideAction();
	}

	private void editMember() {
		view.displayEditMemberTitle();
		
		if(setCurrentMember()) {
			Member newMember = view.displayEditMember(currentMember);
			if(newMember == null) {
				currentMember = null;
				currentAction = null;
				decideAction();
				return;
			}
			
			String newName = newMember.getName();
			String newPNr = newMember.getPNr();
			
			if(!newName.equals(currentMember.getName())) {
				currentMember.editName(newName);
				view.displayNameChangedConfirmation();
			}
			if(!newPNr.equals(currentMember.getPNr())) {
				currentMember.editPNr(newPNr);
				view.displayPNrChangedConfirmation();
			}
			memberRegistry.saveDB();
			decideAction();
		}
	}
	

	private void viewMember() {
		view.displayViewMemberTitle();

		if(setCurrentMember()) {
			view.displayMemberInfo(currentMember); 
			currentMember = null;
			currentAction = null;
			decideAction();
		}
	}
	

	private void deleteMember() {
		view.displayDeleteMember(memberRegistry.getAllMembers());

		if(setCurrentMember()) {
			memberRegistry.deleteMember(currentMember);
			view.displayMemberDeletedConfirmation();
			currentAction = null;
			currentMember = null;
			decideAction();
		}
	}


	private void listMembers() {
		ArrayList<Member> membersList = memberRegistry.getAllMembers();
		if(!view.displayMembersList(membersList))
			currentAction = null;
		decideAction();
	}

	
	private void registerBoat() {
		view.displayRegisterBoatTitle();

		if(setCurrentMember()) {
			Boat newBoat = view.displayRegisterBoat();
			if(newBoat == null) {
				currentAction = null;
				currentMember = null;
				decideAction();
				return;
			}
			currentMember.addBoat(newBoat);
			view.displayBoatRegisteredConfirmation();
			memberRegistry.saveDB();
			currentAction = null;
			currentMember = null;
			decideAction();
		}
	}


	private void editBoat() {
		if(currentMember == null)
			view.displayEditBoatTitle();

		if(setCurrentMember()) { // Member selection
			ArrayList<Boat> memberBoats = currentMember.getBoats();

			if (memberBoats.size() == 0) {
				view.displayMemberHasNoBoatsMsg();
				currentAction = null;
				currentMember = null;
				decideAction();
			}
			else if(setCurrentBoat(memberBoats)) { // Boat selection
				Boat newBoat = view.displayEditBoat(currentBoat);
				if(newBoat == null) { // Aborted inputs
					currentBoat = null;
					decideAction();
					return;
				}
				BoatType newType = newBoat.getType();
				double newSize = newBoat.getSize();
				
				if(!newType.equals(currentBoat.getType())) {
					currentBoat.editType(newType);
					view.displayBoatTypeEditedConfirmation();
				}
				if(newSize != currentBoat.getSize()) {
					currentBoat.editSize(newSize);
					view.displayBoatSizeEditedConfirmation();
				}
				memberRegistry.saveDB();
				decideAction();
			}
		}
	}


	private void removeBoat() {
		view.displayRemoveBoatTitle();
		
		if(setCurrentMember()) {
			ArrayList<Boat> memberBoats = currentMember.getBoats();

			if (memberBoats.size() == 0) {
				view.displayMemberHasNoBoatsMsg();
				currentAction = null;
				currentMember = null;
				decideAction();
			}
			else if(setCurrentBoat(memberBoats)) {
				currentMember.removeBoat(currentBoat);
				view.displayBoatDeletedConfirmation();
				memberRegistry.saveDB();
				currentBoat = null;
				currentAction = null;
				currentMember = null;
				decideAction();
			}
		}
	}
	

	private void exit() {
		view.displayExitMsg();
	}




	/**
	 * Used for navigation convenience. Controller can return to a submenu without having to type in the user id again. Sets currentMember if unset
	 */
	private boolean setCurrentMember() {
		if(currentMember == null) {
			Member selectedMember = view.displayMemberSelection(memberRegistry);
			if(selectedMember == null) {
				currentAction = null;
				decideAction();
				return false;
			}
			else 
				currentMember = selectedMember;
		}
		return true;
	}

	/**
	 * Used for navigation convenience. Controller can return to a submenu without having to type in the boat id again. Sets currentBoat if unset
	 */
	private boolean setCurrentBoat(ArrayList<Boat> memberBoats) {
		if(currentBoat == null) {
			Boat selectedboat = view.displayBoatSelection(memberBoats);
			if(selectedboat == null) {
				currentAction = null;
				currentMember = null;
				decideAction();
				return false;
			}
			else {
				currentBoat = selectedboat;
			}
		}
		return true;
	}
}