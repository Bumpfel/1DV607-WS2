package controller;

import java.util.ArrayList;

import authentication.Authentication;
import model.Boat;
import model.Boat.BoatType;
import model.Member;
import model.MemberRegistry;
import view.ViewInterface;
import view.ViewInterface.AdminAction;

public class Admin extends User {

	public Admin(MemberRegistry memberReg, ViewInterface inView) {
		super(memberReg, inView);
	}

	@Override
	protected void mainMenu() {
		AdminAction action = view.displayAdminMainMenu(); // view.displayAdminMainMenu()
		
		switch(action) {
			case ADMIN_LOGOUT:
				logOut();
				break;
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
			case SIMPLE_SEARCH:
				simpleMemberSearch();
				break;
			case COMPLEX_SEARCH:
				complexMemberSearch();
				break;
			case EXIT: 
				exit();
				break;
			case INVALID_CHOICE:
				resetMenu();
				break;
		}
	}

	private void logOut() {
		view.displayLogOutMsg();
		Authentication.adminLogOut(memberRegistry, view);
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
					memberRegistry.saveDB();
				}
				if(!newPNr.equals(currentMember.getPNr())) {
					currentMember.editPNr(newPNr);
					view.displayPNrChangedConfirmation();
					memberRegistry.saveDB();
				}
				editMember();
			}
		}
		else
			resetMenu();
	}
	

	private void deleteMember() {
		view.displayDeleteMember(memberRegistry.getAllMembers());

		if(setCurrentMember()) {
			memberRegistry.deleteMember(currentMember);
			view.displayMemberDeletedConfirmation();

			currentMember = null;
			deleteMember();
		}
		else
			resetMenu();
	}


	private void registerBoat() {
		view.displayRegisterBoatTitle();

		if(setCurrentMember()) {
			Boat newBoat = view.displayRegisterBoat();
			if(newBoat != null) {
				currentMember.addBoat(newBoat);
				view.displayBoatRegisteredConfirmation();
				memberRegistry.saveDB();
				registerBoat();
			}
			else {
				resetMenu();
			}
		}
		else
			resetMenu();
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
			else if(setCurrentBoat(memberBoats)) { // Boat selection
				Boat newBoat = view.displayEditBoat(currentBoat);
				if(newBoat == null) { // Aborted inputs
					currentBoat = null;
					editBoat();
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
					editBoat();
				}
			}
			else
				resetMenu();
		}
		else
			resetMenu();
	}


	private void removeBoat() {
		if(currentMember == null)
			view.displayRemoveBoatTitle();
		
		if(setCurrentMember()) {
			ArrayList<Boat> memberBoats = currentMember.getBoats();

			if (memberBoats.size() == 0) {
				view.displayMemberHasNoBoatsMsg();
				resetMenu();
			}
			if(setCurrentBoat(memberBoats)) {
				currentMember.removeBoat(currentBoat);
				currentBoat = null;
				view.displayBoatRemovedConfirmation();
				memberRegistry.saveDB();

				removeBoat();
			}
			else
				resetMenu();
		}
		else
			resetMenu();
	}

	@Override
	protected void resetMenu() {
		currentMember = null;
		currentBoat = null;
		mainMenu();
	}

}