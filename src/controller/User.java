package controller;

import java.util.ArrayList;
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

	//Not yet implemented
	private void addMember() {
		String[] nameAndPnr = new String[2];
		
		nameAndPnr = view.displayAddMember();
		memberRegistry.addMember(nameAndPnr[0],nameAndPnr[1]);
		this.mainMenu();
	}
	
	//Not yet implemented
	private void editMember() {
		this.mainMenu();
	}

	private void exit() {

	}


}