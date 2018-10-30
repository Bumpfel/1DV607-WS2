package controller;

import java.util.ArrayList;

import model.Boat;
import model.Boat.BoatType;
import model.Member;
import model.MemberRegistry;
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
		if(newMember != null) {
			memberRegistry.addMember(newMember);
			view.displayMemberCreatedConfirmation();
		}
		resetMenu();
	}

	private void editMember() {
		view.displayEditMemberTitle();
		
		if(setCurrentMember()) {
			Member tempMember = view.displayEditMember(currentMember);
			if(tempMember == null) {
				resetMenu();
			}
			else {
				String newName = tempMember.getName();
				String newPNr = tempMember.getPNr();
				
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
	}
	

	private void viewMember() {
		view.displayViewMemberTitle();

		if(setCurrentMember()) {
			view.displayMemberInfo(currentMember); 
			resetMenu();
		}
	}
	

	private void deleteMember() {
		view.displayDeleteMember(memberRegistry.getAllMembers());

		if(setCurrentMember()) {
			memberRegistry.deleteMember(currentMember);
			view.displayMemberDeletedConfirmation();

			resetMenu();
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
			if(newBoat != null) {
				currentMember.addBoat(newBoat);
				view.displayBoatRegisteredConfirmation();
				memberRegistry.saveDB();
			}
			resetMenu();
		}
	}


	private void editBoat() {
		if(currentMember == null)
			view.displayEditBoatTitle();

		if(setCurrentMember()) { // Member selection
			ArrayList<Boat> memberBoats = currentMember.getBoats();

			if (memberBoats.size() == 0) {
				view.displayMemberHasNoBoatsMsg();
				resetMenu();
			}
			/*else {
				Boat selectedBoat = view.displayBoatSelection(memberBoats);
				if(selectedBoat != null) {
					Boat tempBoat = view.displayEditBoat(selectedBoat);


				}
			}*/
			else if(setCurrentBoat(memberBoats)) { // Boat selection
				Boat newBoat = view.displayEditBoat(currentBoat);
				if(newBoat == null) { // Aborted inputs
					currentBoat = null;
					decideAction();
				}
				else {
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

				resetMenu();
			}
		}
	}
	

	private void exit() {
		view.displayExitMsg();
	}


	private void resetMenu() {
		currentMember = null;
		currentBoat = null;
		currentAction = null;
		decideAction();
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