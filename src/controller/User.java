package controller;

import java.util.ArrayList;
import java.util.NoSuchElementException;

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
		
				try {
					numberInput = view.getInputInt();
				}
				
				catch (NumberFormatException e) {
					view.displayError(e.getMessage());
					numberInput = -9001;
				}
		
		//If exit
		if (numberInput == options.size()-1) {
			this.exit();
		}
		
	}
	
	//Not yet implemented
	private void addMember() {
		this.mainMenu();
	}
	
	private void exit() {
		
	}
	
	
}