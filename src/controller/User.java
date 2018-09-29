package controller;

public abstract class User {

    public User() {
    }

public class User {
	private ViewInterface view;
	private EngConsole tempConsole = new EngConsole(); // Temp ----------------------------------------
	private MemberRegistry memberRegistry;

	public User(MemberRegistry memberReg, ViewInterface inView) {
		view = inView;
		memberRegistry = memberReg;
	}

	public void startApplication() {
		view.displayWelcomeMsg();
		mainMenu();
	}

	private void mainMenu() {
		view.displayMainMenu();
		
		int currentOption = view.getInputInt(1, 9);
		
		switch(currentOption) {
			case 1: addMember();
			case 2: editMember();
			case 3:	viewMember();
			case 4:	deleteMember();
			case 5: viewMemberList();
			case 6: registerBoat();
			case 7: editBoat();
			case 8: removeBoat();
			case 9: exit();
		}
	}

	private void addMember() {
		String[] nameAndPnr = new String[2];
		
		nameAndPnr = view.displayAddMember();
		memberRegistry.addMember(nameAndPnr[0], nameAndPnr[1]);
		
		memberRegistry.saveDB();
		mainMenu();
	}
	
	private void editMember() {
//		String option1 = EngConsole.MenuOptions.EditName.getOption(); // Should not call EngConsole ---------------------------------------
//		String option2 = EngConsole.MenuOptions.EditPNr.getOption(); // Should not call from EngConsole ---------------------------------------

//		int chosenOption = tempConsole.displaySubMenu(option1, option2); // Temp ----------------------------------------

		String[] options = view.getEditMemberOptions();
		
		int chosenOption = view.displaySubMenu(options);
		
		switch (chosenOption) {
			case 1: editName();
			case 2: editPnr();
		}
	}
	
	private void editName() {
		String newName = new String();
		int memberId = view.displayEnterMemberId();
		
		//Makes sure member actually exists, also prints error if it does not
		try {
			Member currentMember = memberRegistry.getMember(memberId);
			newName = view.displayEditName();
			
			//Throws error if newName.length < 2
			try {
				currentMember.editName(newName);
				view.displayNameChangedConfirm();
//				view.displayMessage(EngConsole.Msg.NameChanged.getMsg()); // Should not call EngConsole ---------------------------------------
			} catch (IllegalArgumentException e) {
				view.displayError(e.getMessage());
			}
		} 
		
		catch (NoSuchElementException e) {
			view.displayMemberDoesNotExistMsg();
		}
		
		memberRegistry.saveDB();
		this.mainMenu();
	}
	
	private void editPnr() {
		String newPnr = new String();
		int memberId = view.displayEnterMemberId();
		
		//Makes sure member actually exists, also prints error if it does not
		try {
			Member currentMember = memberRegistry.getMember(memberId);
			newPnr = view.displayEditPnr();
			
			//Throws error if newPnr.length < 10
			try {
				currentMember.editPNr(newPnr);
				view.displayMessage("Social security number has been changed!");
			} catch (IllegalArgumentException e) {
				view.displayError(e.getMessage());
			}
		}
		
		catch (NoSuchElementException e) {
			view.displayError("Member does not exist!");
		}
		
		memberRegistry.saveDB();
		this.mainMenu();
	}
	
	private void viewMember() {
		int memberId = view.displayEnterMemberId();
		
		//Makes sure member actually exists, also prints error if it does not
		try {
			Member currentMember = memberRegistry.getMember(memberId);
			view.displayViewMember(currentMember);
		} 
		
		catch (NoSuchElementException e) {
			view.displayError("Member does not exist!");
		}
		
		this.mainMenu();
	}
	
	private void deleteMember() {
		int memberId = view.displayEnterMemberId();
		
		//Makes sure member actually exists, also prints error if it does not
		try {
			memberRegistry.deleteMember(memberId);
			view.displayDeleteMember();
		} 
		
		catch (NoSuchElementException e) {
			view.displayError("Member does not exist!");
		}
		
		memberRegistry.saveDB();
		this.mainMenu();
	}
	
	private void viewMemberList() {
		int option = view.displayViewMemberListMenu();
		
		switch (option) {
		case 1:
			this.viewListCompact();
			break;
		case 2:
			this.viewListVerbose();
			break;
		}
	}
	
	private void viewListVerbose() {
		ArrayList<Member> membersList = memberRegistry.getAllMembers();
		
		view.displayMembersVerbose(membersList);
		
		this.mainMenu();
	}
	
	private void viewListCompact() {
		ArrayList<Member> membersList = memberRegistry.getAllMembers();
		
		view.displayMembersCompact(membersList);
		
		this.mainMenu();
	}
	
	private void registerBoat() {
		double size;
		Boat.BoatType boatType = Boat.BoatType.Other;
		Member currentMember;
		
		int memberId = view.displayEnterMemberId();
		
		//Makes sure member actually exists, also prints error if it does not
		try {
			currentMember = memberRegistry.getMember(memberId);
			boatType = view.displayEnterBoatType();
			
			size = view.displayBoatEnterSize();
			
			currentMember.registerBoat(boatType, size);
			
			view.displayBoatConfirm();
		}
		
		catch (NoSuchElementException e) {
			view.displayError("Member does not exist!");
		}
		
		memberRegistry.saveDB();
		this.mainMenu();
	}
	
	private void editBoat() {
		int memberId = view.displayEnterMemberId();
		Member currentMember = null;
		Boat currentBoat;
		ArrayList<Boat> boats;
		
		try {
			currentMember = memberRegistry.getMember(memberId);
		}
		
		catch (NoSuchElementException e) {
			view.displayError("Member does not exist!");
			this.mainMenu();
		}

		boats = currentMember.getBoats();
		if (boats.size() == 0) {
			view.displayError("Member has no boats!");
			this.mainMenu();
		}
		
		view.displayBoatListCompact(boats);
		
		currentBoat = view.displayEnterBoat(boats);
		
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
	
	private void removeBoat() {
		Member currentMember = null;
		int memberId = view.displayEnterMemberId();
		
		Boat currentBoat;
		ArrayList<Boat> boats;
		
		try {
			currentMember = memberRegistry.getMember(memberId);
		}
		
		catch (NoSuchElementException e) {
			view.displayError("Member does not exist!");			
			this.mainMenu();
		}
		
		boats = currentMember.getBoats();
		if (boats.size() == 0) {
			view.displayError("Member has no boats!");			
			this.mainMenu();
		}
		view.displayBoatListCompact(boats);
		
		currentBoat = view.displayEnterBoat(boats);
		currentMember.removeBoat(currentBoat);
	
		view.displayDeleteBoatConfirm();

		memberRegistry.saveDB();
		this.mainMenu();
	}

	//This method does nothing
	private void exit() {

	}

}