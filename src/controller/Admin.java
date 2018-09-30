package controller;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import model.Boat;
import model.Member;
import model.MemberRegistry;
import model.Boat.BoatType;
// import view.EngConsole;
import view.ViewInterface;

public class Admin extends User {

	private enum Action { ADD_MEMBER, EDIT_NAME, EDIT_PNR, VIEW_MEMBER, DELETE_MEMBER, REGISTER_BOAT, EDIT_BOAT_TYPE, EDIT_BOAT_SIZE, DELETE_BOAT }

	private ViewInterface view;
	// private EngConsole tempConsole = new EngConsole(); // Temp ----------------------------------------
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
		
		// int currentOption = view.getInputInt(1, 9);
		// int chosenOption = view.getMenuInput();
		// if(isValidMenuChoice(chosenOption, 1, view.getMainMenuOptions().length)) {
			switch(chosenOption) {
				case 1: addMember();
				case 2: editMember();
				case 3: viewMember(); // doAction(Action.VIEW_MEMBER);
				case 4: deleteMember();  // doAction(Action.DELETE_MEMBER);
				case 5: viewMemberList();
				case 6: registerBoat(); // doAction(Action.REGISTER_BOAT);
				case 7: editBoat();
				case 8: removeBoat(); // doAction(Action.DELETE_BOAT);
				case 9: exit();
				default: view.displayInvalidMenuChoiceError();
						 mainMenu();
			}
			
		// }
	}

	// while it would make more sense to call a method in the view that handles both inputs, it would make less sense to put the isValidX methods in the view
	// an alt. would be to call those methods from the controller, but then we would have one more dependency
	private void addMember() {
		String[] nameAndPNr = view.displayAddMember();
		String newName = nameAndPNr[0];
		String newPNr = nameAndPNr[1];

		memberRegistry.addMember(newName.trim(), newPNr.trim());
		view.displayMemberCreatedConfirm();
		mainMenu();
	}
	
	/**
	 * Asks for memberId, details
	 */
	private void editMember() {
		int memberId = view.displayEnterMemberIdInput();

		if(memberRegistry.memberExists(memberId)) {
			Member currentMember = memberRegistry.getMember(memberId);
			String[] nameAndPNr = view.displayEditMember();
			String name = nameAndPNr[0];
			String pNr = nameAndPNr[1];
			// currentMember.editName(name);
			// currentMember.editPNr(pNr);
			currentMember.editMember(name, pNr);
			view.displayMemberEditedConfirm();
			mainMenu();
		}
		else {
			view.displayMemberDoesNotExistError();
			editMember();
		}
	}
	
	private void viewMember() {
		int memberId = view.displayEnterMemberIdInput();
		
		if(memberRegistry.memberExists(memberId)) {
			Member currentMember = memberRegistry.getMember(memberId);
			view.displayViewMember(currentMember);
			mainMenu();
		}
		else {
			view.displayMemberDoesNotExistError();
			viewMember();
		}
	}
	
	private void deleteMember() {
		int memberId = view.displayEnterMemberIdInput();
		
		if(memberRegistry.memberExists(memberId)) {
			memberRegistry.deleteMember(memberId);
			view.displayDeleteMemberConfirmation();
			mainMenu();
		}
		else {
			view.displayMemberDoesNotExistError();
			deleteMember();
		}
	}

	private void viewMemberList() {
		ArrayList<Member> membersList = memberRegistry.getAllMembers();
		view.displayViewMembersList(membersList);
		mainMenu();
	}

	
	private void registerBoat() {
		int memberId = view.displayEnterMemberIdInput();
		
		if(memberRegistry.memberExists(memberId)) {
			Member currentMember = memberRegistry.getMember(memberId);

			// String[] availableBoatTypes = Boat.BoatType.value;
			Boat.BoatType.values().toString();

			Object[] boatDetails = view.displayRegisterBoat(Boat.BoatType.values());
			Boat.BoatType boatType = (Boat.BoatType) boatDetails[0];
			double size = (double) boatDetails[1];
			currentMember.registerBoat(boatType, size);
			
			view.displayBoatRegisteredConfirmation();
			mainMenu();
		}
		else {
			view.displayMemberDoesNotExistError();
			registerBoat();
		}
	}

	private void editBoat() {
		// int memberId = view.displayEnterMemberIdInput();
		
		// if(memberRegistry.memberExists(memberId)) {
		// 	Member currentMember = memberRegistry.getMember(memberId);
		// 	ArrayList<Boat> boats = currentMember.getBoats();

		// 	Boat currentBoat = view.displayEnterBoatInput(boats);
			
		// 	int chosenOption = view.getMenuInput();

			
		// 	// view.displayBoatList(currentMember);
			
		// }		
		// else {
		// 	view.displayMemberDoesNotExistError();
		// 	editBoat();
		// }
	}
	
	// private void editBoatType(Boat currentBoat) {
	// 	Boat.BoatType newType = view.displayEnterBoatType();
		
	// 	currentBoat.editType(newType);
		
	// 	view.displayEditBoatSizeConfirm();
		
	// 	this.mainMenu();
	// }
	
	// private void editBoatSize(Boat currentBoat) {
	// 	double newSize = view.displayBoatEnterSize();
		
	// 	currentBoat.editSize(newSize);
		
	// 	view.displayEditBoatTypeConfirm();
		
	// 	this.mainMenu();
	// }
	
	private void removeBoat() {
		int memberId = view.displayEnterMemberIdInput();
		
		// Boat currentBoat;
		if(memberRegistry.memberExists(memberId)) {
			Member currentMember = memberRegistry.getMember(memberId);
			ArrayList<Boat> boats = currentMember.getBoats();
			view.displayBoatList(boats);
		}
		else {
			view.displayMemberDoesNotExistError();
			this.mainMenu();
		}
		
		
		
		// currentBoat = view.displayEnterBoatInput(boats);
		// currentMember.removeBoat(currentBoat);
	
		view.displayDeleteBoatConfirm();

		memberRegistry.saveDB();
		this.mainMenu();
	}

	// private void subMenu(String[] options, Action...actions) { // new ----------------------------------------------
	// 	int chosenOption = view.displaySubMenu(options);

	// 	for(int i = 0; i < options.length; i ++) {
	// 		if(chosenOption == i) {
	// 			doAction(Action.values()[i]);
	// 		}	
	// 	}
	// }


	// private void doAction(Action action) { // new ----------------------------------------------
	// 	// most actions that doesnt't have a submenu follow a pattern like this
	
	// 	//Makes sure member actually exists, also prints error if it does not
	// 	String memberIdStr = view.displayEnterMemberIdInput();
	// 	try {
	// 		int memberId = Integer.parseInt(memberIdStr);
	// 		Member currentMember = memberRegistry.getMember(memberId);

	// 		if(action == Action.EDIT_NAME) {
	// 			String newName = new String(); //view.displayNewNameInput();
	// 			while(!isValidName(newName)) {
	// 				view.displayInvalidInputError();
	// 				newName = view.displayNewNameInput();
	// 				memberRegistry.saveDB();
	// 			}
	// 			currentMember.editName(newName);
	// 			view.displayNameChangedConfirm();
	// 		}
	// 		else if(action == Action.EDIT_PNR) {
	// 			String newPNr = new String(); //view.displayNewPNrInput();
	// 			while(!isValidPNr(newPNr)) {
	// 				view.displayInvalidPNrError();
	// 				newPNr = view.displayNewPNrInput();
	// 			}
	// 		}
	// 		else if(action == Action.VIEW_MEMBER) {
	// 			view.displayViewMember(currentMember);
	// 		}
	// 		else if(action == Action.DELETE_MEMBER) {
	// 			memberRegistry.deleteMember(memberId);

	// 			view.displayDeleteMemberConfirmation();
	// 			memberRegistry.saveDB();
	// 		}
	// 		else if(action == Action.REGISTER_BOAT) {
	// 			Boat.BoatType boatType = view.displayEnterBoatType(); // ------- check out
	// 			double size = view.displayBoatEnterSize(); // ------ check out
	// 			currentMember.registerBoat(boatType, size);

	// 			view.displayBoatConfirm();
	// 			memberRegistry.saveDB();
	// 		}
	// 		else if(action == Action.EDIT_BOAT_TYPE) {
	// 			Boat.BoatType newType = view.displayEnterBoatType();
				
	// 			// currentBoat.editType(newType);
				
	// 			view.displayEditBoatSizeConfirm();
	// 		}
	// 		else if(action == Action.EDIT_BOAT_SIZE) {
	// 		}
	// 		else if(action == Action.DELETE_BOAT) {
	// 			ArrayList<Boat> boats = currentMember.getBoats();
		
	// 			view.displayBoatListCompact(currentMember);

	// 			Boat currentBoat = null;
	// 			while(currentBoat == null) {
	// 				currentBoat = view.displayEnterBoatInput(boats);
	// 			}
	// 			currentMember.removeBoat(currentBoat);
				
	// 			view.displayDeleteBoatConfirm();
	// 			memberRegistry.saveDB();
	// 		}

	// 		this.mainMenu();
	// 	}
	// 	catch (NoSuchElementException e) {
	// 		view.displayMemberDoesNotExistError();
	// 	}
	// 	catch(NumberFormatException e){
	// 		view.displayInvalidInputError();
	// 	}
	// }
	
	
	private void exit() {
		view.displayExitMsg();
		System.exit(-1);
	}

}