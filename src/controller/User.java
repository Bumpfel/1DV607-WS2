package controller;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import view.ViewInterface;
import model.MemberRegistry;

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
		options.add("List members (Verbose");
		options.add("List members (Compact)");
		options.add("Register boat");
		options.add("Edit boat");
		options.add("Remove boat");
		options.add("Exit");

		view.displayMenu(options);

		currentOption = view.getInputInt(1,options.size());
		
		//System.out.println(memberRegistry.getMember(2).getName());
		
		switch(currentOption) {
			case 1:
				this.addMember();
				break;
				
			case 2:
				this.editMember();
				break;
		
			case 10:
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
	
	//Not yet implemented
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
		
		if (memberExists(memberId)) {
			newName = view.displayEditName();
			memberRegistry.editMemberName(memberId, newName);
			view.displayMessage("Name has been changed!");
		} else {
			
		}
		
		this.mainMenu();
	}
	
	private void editPnr() {
		this.mainMenu();
	}

	private void exit() {

	}

	private boolean memberExists(int member) {
		//Find member, is persistent
		boolean validMember = false;		
		try {
			memberRegistry.getMember(member);
			validMember = true;
		} catch (NoSuchElementException e) {
			view.displayError("Member does not exist!");
		}
			
		return validMember;
	}

}