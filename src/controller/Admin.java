package controller;

import java.util.ArrayList;

import model.Boat;
import model.Member;
import model.MemberRegistry;
import model.Boat.BoatType;
import view.ViewInterface;

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
			case 1: addMember();
					break;
			case 2: editMember();
					break;
			case 3: viewMember();
					break;
			case 4: deleteMember();
					break;
			case 5: listMembers();
					break;
			case 6: registerBoat();
					break;
			case 7: editBoat();
					break;
			case 8: removeBoat();
					break;
			case 0: exit();
					break;
			default: view.displayInvalidMenuChoiceError();
					 mainMenu();
		}
	}

	
	private void addMember() {
		String[] nameAndPNr = view.displayAddMember();

		checkInput(nameAndPNr[0]);
		checkInput(nameAndPNr[1]);

		String newName = nameAndPNr[0];
		String newPNr = nameAndPNr[1];
		
		memberRegistry.addMember(newName.trim(), newPNr);
		view.displayMemberCreatedConfirmation();
		mainMenu();
	}


	private void editMember() {
		view.displayEditMemberTitle();
		int memberId = view.displayMemberIdPrompt();

		checkInput(memberId);

		if(memberRegistry.memberExists(memberId)) {
			Member currentMember = memberRegistry.getMember(memberId);
			String[] nameAndPNr = view.displayEditMember(currentMember);
			String newName = nameAndPNr[0];
			checkInput(newName);
			String newPNr = nameAndPNr[1];
			checkInput(newPNr);
			
			if(!newName.trim().equals(currentMember.getName())) {
				currentMember.editName(newName);
				view.displayNameChangedConfirmation();
			}
			else if(!newPNr.equals(currentMember.getPNr())) {
				currentMember.editPNr(newPNr);
				view.displayPNrChangedConfirmation();
			}
			mainMenu(); // would be nice if it went back to the submenu *************************************************************************************
		}
		else {
			view.displayMemberDoesNotExistError();
			editMember();
		}
	}
	

	private void viewMember() {
		view.displayViewMemberTitle();

		int memberId = view.displayMemberIdPrompt();
		checkInput(memberId);

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
		view.displayDeleteMemberTitle();

		int memberId = view.displayMemberIdPrompt();
		checkInput(memberId);

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
		view.displayRegisterBoatTitle();

		int memberId = view.displayMemberIdPrompt();
		checkInput(memberId);

		if(memberRegistry.memberExists(memberId)) {
			Member currentMember = memberRegistry.getMember(memberId);

			Object[] boatDetails = view.displayRegisterBoat(BoatType.values());
			checkInput(boatDetails[0]);
			checkInput(boatDetails[1]);
			
			BoatType boatType = (BoatType) boatDetails[0];
			double size = (double) boatDetails[1];
			currentMember.registerBoat(boatType, size);
			
			memberRegistry.saveDB();
			view.displayBoatRegisteredConfirmation();
		}
		else {
			view.displayMemberDoesNotExistError();
			registerBoat();
		}
	}


	private void editBoat() {
		view.displayEditBoatTitle();

		int memberId = view.displayMemberIdPrompt();
		checkInput(memberId);

		if(memberRegistry.memberExists(memberId)) {
			Member currentMember = memberRegistry.getMember(memberId);
			ArrayList<Boat> memberBoats = currentMember.getBoats();

			if (memberBoats.size() == 0) {
				view.displayMemberHasNoBoatsMsg();
				editBoat();
			}
			else { // should be separated into submenu > edit boattype or edit size ------------------------------------------------------------
				int boatIndex = view.displayBoatSelection(memberBoats);
				checkInput(boatIndex);
				Boat selectedBoat = memberBoats.get(boatIndex - 1);
				
				Object[] boatDetails = view.displayEditBoat(BoatType.values());
				checkInput(boatDetails[0]);
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
		view.displayRemoveBoatTitle();
		
		int memberId = view.displayMemberIdPrompt();
		checkInput(memberId);

		if(memberRegistry.memberExists(memberId)) {
			Member currentMember = memberRegistry.getMember(memberId);
			ArrayList<Boat> memberBoats = currentMember.getBoats();

			if (memberBoats.size() == 0) {
				view.displayMemberHasNoBoatsMsg();
				removeBoat();
			}
			else {
				int boatIndex = view.displayBoatSelection(memberBoats);
				checkInput(boatIndex);
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

	private void checkInput(Object arg) {
		if(arg.equals(0) || arg.equals("0")) {
			mainMenu();
		}
	}
}