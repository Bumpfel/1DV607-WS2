package controller;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import model.Boat;
import model.Member;
import model.MemberRegistry;
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
		view.displayMainMenu();
		
		// int currentOption = view.getInputInt(1, 9);
		String chosenOption = view.getInput();
		// if(isValidMenuChoice(chosenOption, 1, view.getMainMenuOptions().length)) {
			switch(chosenOption) {
				case "1": addMember();
				case "2": editMemberMenu();
				case "3": doAction(Action.VIEW_MEMBER);//viewMember();
				case "4": doAction(Action.DELETE_MEMBER); //deleteMember();
				case "5": viewMemberList();
				case "6": doAction(Action.REGISTER_BOAT); //registerBoat();
				case "7": editBoat();
				case "8": doAction(Action.DELETE_BOAT); //removeBoat();
				case "9": exit();
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

	// private void addMember_() {
	// 	String newName = view.displayNewNameInput();
	// 	String newPNr = view.displayNewPNrInput();
	// 	while(!isValidName(newName)) {
	// 		view.displayInvalidNameError();
	// 		newName = view.displayNewNameInput();
	// 	}
	// 	while(!isValidPNr(newPNr)) {
	// 		view.displayInvalidPNrError();
	// 		newPNr = view.displayNewPNrInput();
	// 	}
	// 	memberRegistry.addMember(newName.trim(), newPNr.trim());
	// 	// memberRegistry.saveDB();  // saving in addMember() instead

	// 	view.displayMemberCreatedConfirm();
	// 	mainMenu();	
	// }
	
	private void editMemberMenu() {
		String[] options = view.getEditMemberOptions();
		int chosenOption = view.displaySubMenu(options);
		
		switch (chosenOption) {
			case 1: doAction(Action.EDIT_NAME); //editName();
			case 2: doAction(Action.EDIT_PNR); //editPnr();
			default: view.displayInvalidMenuChoiceError();
					 editMemberMenu();
		}
	}

	private void viewMemberList() {
		String[] options = view.getListMemberOptions();
		// int chosenOption = view.displayViewMemberListMenu();
		int chosenOption = view.displaySubMenu(options);
		
		switch (chosenOption) {
			case 1: viewListCompact();
			case 2: viewListVerbose();
		}
	}

	private void subMenu(String[] options, Action...actions) { // new ----------------------------------------------
		int chosenOption = view.displaySubMenu(options);

		for(int i = 0; i < options.length; i ++) {
			if(chosenOption == i) {
				doAction(Action.values()[i]);
			}	
		}
	}


	private void doAction(Action action) { // new ----------------------------------------------
		// most actions that doesnt't have a submenu follow a pattern like this
	
		//Makes sure member actually exists, also prints error if it does not
		String memberIdStr = view.displayEnterMemberIdInput();
		try {
			int memberId = Integer.parseInt(memberIdStr);
			Member currentMember = memberRegistry.getMember(memberId);

			if(action == Action.EDIT_NAME) {
				String newName = new String(); //view.displayNewNameInput();
				while(!isValidName(newName)) {
					view.displayInvalidInputError();
					newName = view.displayNewNameInput();
					memberRegistry.saveDB();
				}
				currentMember.editName(newName);
				view.displayNameChangedConfirm();
			}
			else if(action == Action.EDIT_PNR) {
				String newPNr = new String(); //view.displayNewPNrInput();
				while(!isValidPNr(newPNr)) {
					view.displayInvalidPNrError();
					newPNr = view.displayNewPNrInput();
				}
			}
			else if(action == Action.VIEW_MEMBER) {
				view.displayViewMember(currentMember);
			}
			else if(action == Action.DELETE_MEMBER) {
				memberRegistry.deleteMember(memberId);

				view.displayDeleteMemberConfirmation();
				memberRegistry.saveDB();
			}
			else if(action == Action.REGISTER_BOAT) {
				Boat.BoatType boatType = view.displayEnterBoatType(); // ------- check out
				double size = view.displayBoatEnterSize(); // ------ check out
				currentMember.registerBoat(boatType, size);

				view.displayBoatConfirm();
				memberRegistry.saveDB();
			}
			else if(action == Action.EDIT_BOAT_TYPE) {
				Boat.BoatType newType = view.displayEnterBoatType();
				
				// currentBoat.editType(newType);
				
				view.displayEditBoatSizeConfirm();
			}
			else if(action == Action.EDIT_BOAT_SIZE) {
			}
			else if(action == Action.DELETE_BOAT) {
				ArrayList<Boat> boats = currentMember.getBoats();
		
				view.displayBoatListCompact(currentMember);

				Boat currentBoat = null;
				while(currentBoat == null) {
					currentBoat = view.displayEnterBoatInput(boats);
				}
				currentMember.removeBoat(currentBoat);
				
				view.displayDeleteBoatConfirm();
				memberRegistry.saveDB();
			}

			this.mainMenu();
		}
		catch (NoSuchElementException e) {
			view.displayMemberDoesNotExistError();
		}
		catch(NumberFormatException e){
			view.displayInvalidInputError();
		}
	}
	
	// private void editName() {
	// 	//Makes sure member actually exists, also prints error if it does not
	// 	String memberIdStr = view.displayEnterMemberId();
	// 	try {
	// 		int memberId = Integer.parseInt(memberIdStr);
	// 		Member currentMember = memberRegistry.getMember(memberId);
	// 		String newName = view.displayNewName();
			
	// 		currentMember.editName(newName);
	// 		view.displayNameChangedConfirm();
	// 	}
	// 	catch (NoSuchElementException e) {
	// 		view.displayMemberDoesNotExistError();
	// 		// editName();
	// 	}
	// 	catch(NumberFormatException e){
	// 		view.displayInvalidInputError();
	// 	}
		
	// 	memberRegistry.saveDB();
	// 	this.mainMenu();
	// }
	
	// private void editPnr() {
	// 	String memberIdStr = view.displayEnterMemberId();
		
	// 	//Makes sure member actually exists, also prints error if it does not
	// 	try {
	// 		int memberId = Integer.parseInt(memberIdStr);
	// 		Member currentMember = memberRegistry.getMember(memberId);
	// 		String newPnr = view.displayNewPNr();
			
	// 		currentMember.editPNr(newPnr);
	// 		view.displayPNrChangedConfirm();
	// 		// view.displayMessage("Social security number has been changed!");
	// 	}
		
	// 	catch (NoSuchElementException e) {
	// 		view.displayMemberDoesNotExistError();
	// 	}
		
	// 	memberRegistry.saveDB();
	// 	this.mainMenu();
	// }
	
	// private void viewMember() {
	// 	int memberId = view.displayEnterMemberId();
		
	// 	//Makes sure member actually exists, also prints error if it does not
	// 	try {
	// 		Member currentMember = memberRegistry.getMember(memberId);
	// 		view.displayViewMember(currentMember);
	// 	} 
		
	// 	catch (NoSuchElementException e) {
	// 		view.displayMemberDoesNotExistError();
	// 	}
		
	// 	this.mainMenu();
	// }
	
	// private void deleteMember() {
	// 	int memberId = view.displayEnterMemberId();
		
	// 	//Makes sure member actually exists, also prints error if it does not
	// 	try {
	// 		memberRegistry.deleteMember(memberId);
	// 		view.displayDeleteMember();
	// 	} 
		
	// 	catch (NoSuchElementException e) {
	// 		view.displayMemberDoesNotExistError();
	// 	}
		
	// 	memberRegistry.saveDB();
	// 	this.mainMenu();
	// }
	
	
	
	private void viewListVerbose() {
		ArrayList<Member> membersList = memberRegistry.getAllMembers();
		view.displayMembersVerbose(membersList);
		
		mainMenu();
	}
	
	private void viewListCompact() {
		ArrayList<Member> membersList = memberRegistry.getAllMembers();
		view.displayMembersCompact(membersList);
		
		mainMenu();
	}
	
	// private void registerBoat() {
	// 	Boat.BoatType boatType = Boat.BoatType.Other;
	// 	Member currentMember;
		
	// 	int memberId = view.displayEnterMemberIdInput();
		
	// 	//Makes sure member actually exists, also prints error if it does not
	// 	try {
	// 		currentMember = memberRegistry.getMember(memberId);
	// 		boatType = view.displayEnterBoatType();
			
	// 		double size = view.displayBoatEnterSize();
			
	// 		currentMember.registerBoat(boatType, size);
			
	// 		view.displayBoatConfirm();
	// 	}
		
	// 	catch (NoSuchElementException e) {
	// 		view.displayMemberDoesNotExistError();
	// 	}
		
	// 	memberRegistry.saveDB();
	// 	this.mainMenu();
	// }
	
	private void editBoat() {
		String memberIdStr = view.displayEnterMemberIdInput();
		int memberId = Integer.parseInt(memberIdStr);
		Member currentMember = null;
		Boat currentBoat;
		ArrayList<Boat> boats;
		
		try {
			currentMember = memberRegistry.getMember(memberId);
		}
		
		catch (NoSuchElementException e) {
			view.displayMemberDoesNotExistError();
			this.mainMenu();
		}

		boats = currentMember.getBoats();
		
		view.displayBoatListCompact(currentMember);
		currentBoat = view.displayEnterBoatInput(boats);
		
		int editSizeOrType = view.displayEditBoatMenu();
		
		switch (editSizeOrType) {
			case 1: 
				editBoatType(currentBoat);
				break;
			case 2:
				editBoatSize(currentBoat);
				break;
		}
	}
	
	private void editBoatType(Boat currentBoat) {
		Boat.BoatType newType = view.displayEnterBoatType();
		
		currentBoat.editType(newType);
		
		view.displayEditBoatSizeConfirm();
		
		this.mainMenu();
	}
	
	private void editBoatSize(Boat currentBoat) {
		double newSize = view.displayBoatEnterSize();
		
		currentBoat.editSize(newSize);
		
		view.displayEditBoatTypeConfirm();
		
		this.mainMenu();
	}
	
	// private void removeBoat() {
	// 	Member currentMember = null;
	// 	int memberId = view.displayEnterMemberIdInput();
		
	// 	Boat currentBoat;
	// 	ArrayList<Boat> boats;
		
	// 	try {
	// 		currentMember = memberRegistry.getMember(memberId);
	// 	}
		
	// 	catch (NoSuchElementException e) {
	// 		view.displayMemberDoesNotExistError();
	// 		this.mainMenu();
	// 	}
		
	// 	boats = currentMember.getBoats();
		
	// 	view.displayBoatListCompact(currentMember);
		
	// 	currentBoat = view.displayEnterBoatInput(boats);
	// 	currentMember.removeBoat(currentBoat);
	
	// 	view.displayDeleteBoatConfirm();

	// 	memberRegistry.saveDB();
	// 	this.mainMenu();
	// }



	//-----------------
	// Input validation
	//-----------------
	private boolean isValidMenuChoice(String input, int min, int max) {
		try {
			int inputInt = Integer.parseInt(input);
			if(inputInt >= min && inputInt <= max) {
				return true;
			}
			view.displayInvalidMenuChoiceError();
			return false;
		}
		catch(Exception e) {
			view.displayInvalidMenuChoiceError();
			return false;
		}
	}

	

	private boolean isValidStringInput(String input) {
		return !input.trim().isEmpty();
	}



	//This method does nothing
	private void exit() {

	}

}