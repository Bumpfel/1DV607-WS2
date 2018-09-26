package controller;

/*
 * Implement edit boat
 * Implement remove boat
 * Implement/fix ListMemberVerbose
 */

import java.util.ArrayList;
import java.util.NoSuchElementException;

import view.ViewInterface;
import model.MemberRegistry;
import model.Boat;
import model.Member;

public class User {
	private ViewInterface view;
	private MemberRegistry memberRegistry;

	public User(MemberRegistry memberReg,ViewInterface inView) {
		view = inView;
		memberRegistry = memberReg;
	}

	public void startApplication() {
		view.displayWelcomeMsg();
		this.mainMenu();
	}

	private void mainMenu() {
		ArrayList<String> options = new ArrayList<String>();
		int currentOption = -1;

		options.add("Add member");
		options.add("Edit member");
		options.add("View member");
		options.add("Delete member");
		options.add("List members");
		options.add("Register boat");
		options.add("Edit boat");
		options.add("Remove boat");
		options.add("Exit");

		view.displayMainMenu(options);

		currentOption = view.getInputInt(1,options.size());
		
		switch(currentOption) {
			case 1:
				this.addMember();
				break;
				
			case 2:
				this.editMember();
				break;
				
			case 3:
				this.viewMember();
				break;
				
			case 4:
				this.deleteMember();
				break;
				
			case 5:
				this.viewMemberList();
				break;
				
			case 6:
				this.registerBoat();
				break;
				
			case 7:
				this.editBoat();
				break;
		
			case 9:
				this.exit();
				break;
		}
	}

	private void addMember() {
		String[] nameAndPnr = new String[2];
		
		nameAndPnr = view.displayAddMember();
		memberRegistry.addMember(nameAndPnr[0],nameAndPnr[1]);
		
		this.mainMenu();
	}
	
	private void editMember() {
		int option = view.displayEditMemberMenu();
		
		switch (option) {
			case 1:
				this.editName();
				break;
			case 2:
				this.editPnr();
				break;
		}
	}
	
	private void editName() {
		String newName = "";
		int memberId = view.displayEnterMemberId();
		
		//Makes sure member actually exists, also prints error if it does not
		try {
			Member currentMember = memberRegistry.getMember(memberId);
			newName = view.displayEditName();
			
			//Throws error if newName.length < 2
			try {
				currentMember.editName(newName);
				view.displayMessage("Name has been changed!");
			} catch (IllegalArgumentException e) {
				view.displayError(e.getMessage());
			}
		} 
		
		catch (NoSuchElementException e) {
			view.displayError("Member does not exist!");
		}
		
		this.mainMenu();
	}
	
	private void editPnr() {
		String newPnr = "";
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
		
		this.mainMenu();
	}
	
	//Not yet implemented
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
		int size;
		Boat.BoatType boatType = Boat.BoatType.Other;
		Member currentMember;
		
		int memberId = view.displayEnterMemberId();
		
		//Makes sure member actually exists, also prints error if it does not
		try {
			currentMember = memberRegistry.getMember(memberId);
			boatType = view.displayRegisterBoat();
			
			view.displayBoatEnterSize();
			size = view.getInputInt(1);
			
			currentMember.registerBoat(boatType, size);
			
			view.displayBoatConfirm();
		}
		
		catch (NoSuchElementException e) {
			view.displayError("Member does not exist!");
		}
		
		this.mainMenu();
	}
	
	private void editBoat() {
		
	}

	private void exit() {

	}

}