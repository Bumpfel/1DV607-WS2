package controller;

import java.util.ArrayList;

public class User {
	public view.ViewInterface view;
	
	public User(view.ViewInterface inView) {
		view = inView;
	}
	
	public void startApplication() {
		view.displayWelcomeMsg();
		this.mainMenu();
	}
	
	private void mainMenu() {
		ArrayList<String> options = new ArrayList<String>();
		String StringInput = "";
		int numberInput = -1;
		
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
		
		while (numberInput <= 0) {
				numberInput = view.getInputInt();
				if (numberInput <= 0) {
					view.displayError("Input cannot be 0 or below!");
				}
		}
		
		//If exit
		if (numberInput == options.size()-1) {
			this.exit();
		}
		
	}
	
	private void addMember() {
		
	}
	
	private void exit() {
		
	}
	
	
}