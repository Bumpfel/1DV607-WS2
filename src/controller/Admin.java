package controller;

import java.util.ArrayList;

import model.search.*;
import model.Boat;
import model.Member;
import model.MemberRegistry;
import model.Boat.BoatType;
import view.ViewInterface;
import view.ViewInterface.Title;

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
			case 1: view.displayTitle(Title.ADD_MEMBER);
					addMember();
					break;
			case 2: view.displayTitle(Title.EDIT_MEMBER);
					editMember();
					break;
			case 3: view.displayTitle(Title.VIEW_MEMBER);
					viewMember();
					break;
			case 4: view.displayTitle(Title.DELETE_MEMBER);
					deleteMember();
					break;
			case 5: view.displayTitle(Title.LIST_MEMBERS);
					listMembers();
					break;
			case 6: view.displayTitle(Title.REGISTER_BOAT);
					registerBoat();
					break;
			case 7: view.displayTitle(Title.EDIT_BOAT);
					editBoat();
					break;
			case 8: view.displayTitle(Title.REMOVE_BOAT);
					removeBoat();
					break;
			case 9: view.displayTitle(Title.SEARCH_MEMBER);
					searchMember();
					break;
			case 10: exit();
					break;
			default: {
				view.displayInvalidMenuChoiceError();
				mainMenu();
			}
		}
	}

	private void searchMember() {
		Search search = new Search();

		String[] options = {
			"Born in month",
			"Is below age",
			"Is over age", 
			"Name starts with",
			 "Owns boat of type"};
			 
		String[] searchArguments = view.displaySearch(options);		
		
		ArrayList<ISearchStrategy> searchStrategy = new ArrayList<>();
		switch (Integer.parseInt(searchArguments[0])) {
			case 1: searchStrategy.add(new BornInMonth());
					break;
			case 2: searchStrategy.add(new IsBelowAge());
					break;
			case 3: searchStrategy.add(new IsOverAge());
					break;
			case 4: searchStrategy.add(new NameStartsWith());
					break;
			case 5: searchStrategy.add(new OwnsBoatOfType());
					break;
		}

		String searchParameter = searchArguments[1];
		ArrayList<Member> members = memberRegistry.getAllMembers();

		ArrayList<Member> searchResult = search.search(members, searchStrategy, searchParameter);
		
		view.displayMembersCompact(searchResult);
		mainMenu();
	}
	
	private void addMember() {
		String[] nameAndPNr = view.displayAddMember();
		String newName = nameAndPNr[0];
		String newPNr = nameAndPNr[1];

		memberRegistry.addMember(newName.trim(), newPNr);
		view.displayMemberCreatedConfirmation();
		memberRegistry.saveDB();
		mainMenu();
	}


	private void editMember() {
		int memberId = view.displayMemberIdPrompt();

		if(memberRegistry.memberExists(memberId)) {
			Member currentMember = memberRegistry.getMember(memberId);
			String[] nameAndPNr = view.displayEditMember();
			String newName = nameAndPNr[0];
			String newPNr = nameAndPNr[1];
			// currentMember.editName(name);
			// currentMember.editPNr(pNr);
			currentMember.editMember(newName.trim(), newPNr);
			view.displayMemberEditedConfirmation();
			memberRegistry.saveDB();
			mainMenu();
		}
		else {
			view.displayMemberDoesNotExistError();
			editMember();
		}
	}
	

	private void viewMember() {
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
	

	private void deleteMember() {
		int memberId = view.displayMemberIdPrompt();
		
		if(memberRegistry.memberExists(memberId)) {
			memberRegistry.deleteMember(memberId);
			view.displayMemberDeletedConfirmation();
			memberRegistry.saveDB();
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
		int memberId = view.displayMemberIdPrompt();
		
		if(memberRegistry.memberExists(memberId)) {
			Member currentMember = memberRegistry.getMember(memberId);

			Object[] boatDetails = view.displayRegisterBoat(BoatType.values());
			BoatType boatType = (BoatType) boatDetails[0];
			double size = (double) boatDetails[1];
			currentMember.registerBoat(boatType, size);
			
			memberRegistry.saveDB();
			view.displayBoatRegisteredConfirmation();
			mainMenu();
		}
		else {
			view.displayMemberDoesNotExistError();
			registerBoat();
		}
	}


	private void editBoat() {
		int memberId = view.displayMemberIdPrompt();
		
		if(memberRegistry.memberExists(memberId)) {
			Member currentMember = memberRegistry.getMember(memberId);
			ArrayList<Boat> memberBoats = currentMember.getBoats();

			if (memberBoats.size() == 0) {
				view.displayMemberHasNoBoatsMsg();
				editBoat();
			}
			else {
				int boatIndex = view.displayBoatSelection(memberBoats.toArray());
				Boat selectedBoat = memberBoats.get(boatIndex - 1);
				
				Object[] boatDetails = view.displayEditBoat(BoatType.values());
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
		int memberId = view.displayMemberIdPrompt();
		
		if(memberRegistry.memberExists(memberId)) {
			Member currentMember = memberRegistry.getMember(memberId);
			ArrayList<Boat> memberBoats = currentMember.getBoats();

			if (memberBoats.size() == 0) {
				view.displayMemberHasNoBoatsMsg();
				removeBoat();
			}
			else {
				int boatIndex = view.displayBoatSelection(memberBoats.toArray());
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

}