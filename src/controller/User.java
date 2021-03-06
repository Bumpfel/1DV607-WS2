package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.Boat;
import model.Member;
import model.MemberRegistry;
import model.search.*;
import model.search.strategies.*;
import view.ViewInterface;
import view.ViewInterface.ComplexSearchAction;
import view.ViewInterface.GuestAction;
import view.ViewInterface.SearchAction;

public class User {
	protected ViewInterface view;
	protected MemberRegistry memberRegistry;

	protected Member currentMember;
	protected Boat currentBoat;

	protected SearchCriteriaComposite complexComposite = new SearchCriteriaComposite();

	public User(MemberRegistry memberReg, ViewInterface inView) {
		view = inView;
		memberRegistry = memberReg;
	}
	
    public void startApplication(boolean displayWelcomeMsg) {
		if(displayWelcomeMsg)
			view.displayWelcomeMsg();
		mainMenu();
    }

    protected void mainMenu() {
		GuestAction action = view.displayGuestMainMenu();
		
		switch(action) {
			case ADMIN_LOGIN:
				logIn();
				break;
			case VIEW_MEMBER:
				viewMember();
				break;
			case LIST_MEMBERS:
				listMembers();
				break;
			case SIMPLE_SEARCH:
				simpleMemberSearch();
				break;
			case COMPLEX_SEARCH:
				complexMemberSearch();
				break;
			case EXIT: 
				exit();
				break;
			case INVALID_CHOICE:
				resetMenu();
				break;
		}
	}
	
    private void logIn() {
		String input = view.displayPasswordPrompt();
		Boolean adminAuth = false;
		
		try {
			adminAuth = authentication.Authentication.adminAuthenticate(input);
		}
		catch (IOException e) {
			view.displayNoPasswordFileError();
			resetMenu();
			return;
		}
		
		if(input == null)
			resetMenu();
		else if (adminAuth) {
			view.displayLogInMsg();
			authentication.Authentication.runAdmin(memberRegistry,view);
			return;
		}
		else {
			view.displayInvalidPasswordError();
			logIn();
		}
	}

	protected void viewMember() {
		view.displayViewMemberTitle();

		if(setCurrentMember()) {
			view.displayMemberInfo(currentMember); 
		}
		resetMenu();
	}

	protected void listMembers() {
		ArrayList<Member> membersList = memberRegistry.getAllMembers();
		if(view.displayMembersList(membersList))
			listMembers();
		else
			resetMenu();
	}

	protected void simpleMemberSearch() {
		SearchCriteriaComposite tempComposite = new SearchCriteriaComposite();
		tempComposite = selectSearchFilter(tempComposite);
		if(tempComposite == null) {
			resetMenu();
		}
		else if(tempComposite.isEmpty()) {
			simpleMemberSearch();
		}
		else {
			ArrayList<Member> members = memberRegistry.getAllMembers();
			
			ArrayList<Member> searchResult = new Search().doSearch(members, tempComposite);
			view.displaySearchResults(searchResult, tempComposite);
			simpleMemberSearch();
		}
	}

	protected void complexMemberSearch() {
		ArrayList<Member> members = memberRegistry.getAllMembers();

		ComplexSearchAction choice = view.complexSearch(complexComposite.toString());
		switch(choice) {
			case ADD:
				selectSearchFilter(complexComposite);
				complexMemberSearch();
				break;
			case SEARCH:
				ArrayList<Member> searchResult = new Search().doSearch(members, complexComposite);
				view.displaySearchResults(searchResult, complexComposite);
				complexMemberSearch();
				break;
			case RESET:
				complexComposite = new SearchCriteriaComposite();
				complexMemberSearch();
				break;
			case INVALID_CHOICE:
				view.displayInvalidMenuChoiceError();
				complexMemberSearch();
				break;
			default: //case BACK:
				resetMenu();
				break;
		}
	}

	protected SearchCriteriaComposite selectSearchFilter(SearchCriteriaComposite comp) {
		ISearchStrategy strategy;
		SearchAction choice = view.displaySearchFilters();
		if(choice == null)
			return comp;

		switch (choice) {
			case BORN_IN_MONTH:
				strategy = new BornInMonth();
				break;
			case IS_BELOW_AGE:
				strategy = new IsBelowAge();
				break;
			case IS_OVER_AGE:
				strategy = new IsOverAge();
				break;
			case NAME_STARTS_WITH:
				strategy = new NameStartsWith();
				break;
			case OWNS_BOAT_TYPE:
				strategy = new OwnsBoatOfType();
				break;
			case INVALID_CHOICE:
				view.displayInvalidMenuChoiceError();
				return comp;
			default: //case BACK:
				return null;
			
		}
		String searchString = view.getSearchString();
		if(searchString.equals("")) {
			return comp;
		}
		else if(!strategy.isValid(searchString)) {
			view.displayInvalidSearchParameterError();
			return comp;
		}
		else if(comp.contains(strategy) && !strategy.allowsDuplicate()) {
			view.displayDuplicateSearchFilterReplacedMsg();
			comp.removeDuplicate(strategy);
		}

		SearchCriteria criteria = new SearchCriteria(strategy, searchString);
		if(comp.containsIdenticalCriteria(criteria)) {
			view.displayContainsIdenticalCriteria();
			return comp;
		}

		comp.add(criteria);
		return comp;
	}
	
    protected void exit() {
		view.displayExitMsg();
	}

	/**
	 * Used for navigation convenience. Controller can return to a submenu without having to type in the user id again. Sets currentMember if unset
	 */
	protected boolean setCurrentMember() {
		if(currentMember == null) {
			Member selectedMember = view.displayMemberSelection(memberRegistry);
			if(selectedMember == null) {
				// resetMenu();
				return false;
			}
			else 
				currentMember = selectedMember;
		}
		return true;
	}
	

	/**
	 * Used for navigation convenience. Controller can return to a submenu without having to type in the boat id again. Sets currentBoat if unset
	 */
	protected boolean setCurrentBoat(ArrayList<Boat> memberBoats) {
		if(currentBoat == null) {
			Boat selectedboat = view.displayBoatSelection(memberBoats);
			if(selectedboat == null) {
				// resetMenu();
				return false;
			}
			else {
				currentBoat = selectedboat;
			}
		}
		return true;
	}


	protected void resetMenu() {
		currentMember = null;
		currentBoat = null;
		mainMenu();
	}

}