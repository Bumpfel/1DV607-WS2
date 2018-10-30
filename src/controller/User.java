package controller;

import java.util.ArrayList;

import model.Member;
import model.MemberRegistry;
import view.ViewInterface;
import view.ViewInterface.Title;

public class User {
	protected ViewInterface view;
	protected MemberRegistry memberRegistry;
	
	public User(MemberRegistry memberReg, ViewInterface inView) {
		view = inView;
		memberRegistry = memberReg;
	}
	
    public void startApplication() {
		view.displayWelcomeMsg();
		mainMenu();
    }

    protected void mainMenu() {
		int chosenOption = view.displayGuestMainMenu();
		
		switch(chosenOption) {
			case 1: view.displayTitle(Title.LOG_IN);
					logIn();
					break;
			case 2: view.displayTitle(Title.VIEW_MEMBER);
					viewMember();
					break;
			case 3: view.displayTitle(Title.LIST_MEMBERS);
					listMembers();
					break;
			case 4: exit();
					break;
			default: {
				view.displayInvalidMenuChoiceError();
				mainMenu();
			}
		}
	}
	
    private void logIn() {
    	
		if (authentication.Authentication.adminAuthenticate(view.displayPasswordPrompt())) {
			view.displayCorrectPaswordPrompt();
			authentication.Authentication.runAdmin(memberRegistry,view);
			exit();
		}

		else {
			view.displayInvalidPasswordError();
			mainMenu();
		}
	}

    protected void viewMember() {
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

    protected void listMembers() {
		ArrayList<Member> membersList = memberRegistry.getAllMembers();
		view.displayMembersList(membersList);
		mainMenu();
	}
    
    protected void exit() {
		view.displayExitMsg();
	}

}