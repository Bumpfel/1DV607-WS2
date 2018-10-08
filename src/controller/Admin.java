package controller;

import java.util.ArrayList;

import model.Boat;
import model.Member;
import model.MemberRegistry;
import model.Boat.BoatType;
import view.ViewInterface;
import view.ViewInterface.Title;

public class Admin {

	private ViewInterface view;
	private MemberRegistry memberRegistry;

	public Admin(MemberRegistry memberReg, ViewInterface inView) {
		view = inView;
		memberRegistry = memberReg;
	}

	public void startApplication() {
		view.displayWelcomeMsg();
		mainMenu();
	}

	private void mainMenu() {
		int chosenOption = view.displayMainMenu();
		
		switch(chosenOption) {
			case 1: view.displayTitle(Title.ADD_MEMBER);
					addMember();
					break;
			case 2: view.displayTitle(Title.EDIT_MEMBER);
					editMember();
					break;
			case 3: view.displayTitle(Title.VIEW_MEMBER);
					viewMember();
					break;
			case 4: view.displayTitle(Title.DELETE_MEMBER);
					deleteMember();
					break;
			case 5: view.displayTitle(Title.LIST_MEMBERS);
					listMembers();
					break;
			case 6: view.displayTitle(Title.REGISTER_BOAT);
					registerBoat();
					break;
			case 7: view.displayTitle(Title.EDIT_BOAT);
					editBoat();
					break;
			case 8: view.displayTitle(Title.REMOVE_BOAT);
					removeBoat();
					break;
			case 9: exit();
					break;
			default: {
				view.displayInvalidMenuChoiceError();
				mainMenu();
			}
		}
	}

	
	private void addMember() {
		String[] nameAndPNr = view.displayAddMember();
		String newName = nameAndPNr[0];
		String newPNr = nameAndPNr[1];

		memberRegistry.addMember(newName.trim(), newPNr);
		view.displayMemberCreatedConfirmation();
		mainMenu();
	}


	private void editMember() {
		int memberId = view.displayMemberIdPrompt();

		if(memberRegistry.memberExists(memberId)) {
			Member currentMember = memberRegistry.getMember(memberId);
			String[] nameAndPNr = view.displayEditMember();
			String newName = nameAndPNr[0];
			String newPNr = nameAndPNr[1];
			// currentMember.editName(name);
			// currentMember.editPNr(pNr);
			currentMember.editMember(newName.trim(), newPNr);
			view.displayMemberEditedConfirmation();
			mainMenu();
		}
		else {
			view.displayMemberDoesNotExistError();
			editMember();
		}
	}
	

	private void viewMember() {
		int memberId = view.displayMemberIdPrompt();
		
		if(memberRegistry.memberExists(memberId)) {
			Member currentMember = memberRegistry.getMember(memberId);
			view.displayMemberInfo(currentMember);
			mainMenu();
		}
		else {
			view.displayMemberDoesNotExistError();
			viewMember();
		}
	}
	

	private void deleteMember() {
		int memberId = view.displayMemberIdPrompt();
		
		if(memberRegistry.memberExists(memberId)) {
			memberRegistry.deleteMember(memberId);
			view.displayMemberDeletedConfirmation();
			mainMenu();
		}
		else {
			view.displayMemberDoesNotExistError();
			deleteMember();
		}
	}


	private void listMembers() {
		ArrayList<Member> membersList = memberRegistry.getAllMembers();
		view.displayMembersList(membersList);
		mainMenu();
	}

	
	private void registerBoat() {
		int memberId = view.displayMemberIdPrompt();
		
		if(memberRegistry.memberExists(memberId)) {
			Member currentMember = memberRegistry.getMember(memberId);

			Object[] boatDetails = view.displayRegisterBoat(BoatType.values());
			BoatType boatType = (BoatType) boatDetails[0];
			double size = (double) boatDetails[1];
			currentMember.registerBoat(boatType, size);
			
			memberRegistry.saveDB();
			view.displayBoatRegisteredConfirmation();
			mainMenu();
		}
		else {
			view.displayMemberDoesNotExistError();
			registerBoat();
		}
	}


	private void editBoat() {
		int memberId = view.displayMemberIdPrompt();
		
		if(memberRegistry.memberExists(memberId)) {
			Member currentMember = memberRegistry.getMember(memberId);
			ArrayList<Boat> memberBoats = currentMember.getBoats();

			if (memberBoats.size() == 0) {
				view.displayMemberHasNoBoatsMsg();
				editBoat();
			}
			else {
				int boatIndex = view.displayBoatSelection(memberBoats.toArray());
				Boat selectedBoat = memberBoats.get(boatIndex - 1);
				
				Object[] boatDetails = view.displayEditBoat(BoatType.values());
				BoatType newBoatType = (BoatType) boatDetails[0];
				double newSize = (double) boatDetails[1];
			
				selectedBoat.editType(newBoatType);
				selectedBoat.editSize(newSize);
				memberRegistry.saveDB();

				view.displayBoatEditedConfirmation();
				mainMenu();
			}
		}
		else {
			view.displayMemberDoesNotExistError();
			editBoat();
		}
	}


	private void removeBoat() {
		int memberId = view.displayMemberIdPrompt();
		
		if(memberRegistry.memberExists(memberId)) {
			Member currentMember = memberRegistry.getMember(memberId);
			ArrayList<Boat> memberBoats = currentMember.getBoats();

			if (memberBoats.size() == 0) {
				view.displayMemberHasNoBoatsMsg();
				removeBoat();
			}
			else {
				int boatIndex = view.displayBoatSelection(memberBoats.toArray());
				Boat selectedBoat = memberBoats.get(boatIndex - 1);

				currentMember.removeBoat(selectedBoat);
				memberRegistry.saveDB();

				view.displayBoatDeletedConfirmation();
				mainMenu();
			}
		}
		else {
			view.displayMemberDoesNotExistError();
			removeBoat();
		}
	}
	

	private void exit() {
		view.displayExitMsg();
	}

}