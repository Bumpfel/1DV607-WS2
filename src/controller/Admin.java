package controller;

import java.util.ArrayList;

import model.Boat;
import model.Boat.BoatType;
import model.Member;
import model.MemberRegistry;
import model.search.Search;
import model.search.SearchCriteria;
import model.search.SearchCriteriaComposite;
import model.search.strategies.BornInMonth;
import model.search.strategies.ISearchStrategy;
import model.search.strategies.IsBelowAge;
import model.search.strategies.IsOverAge;
import model.search.strategies.NameStartsWith;
import model.search.strategies.OwnsBoatOfType;
import view.ViewInterface;
import view.ViewInterface.MainAction;
import view.ViewInterface.SearchAction;

public class Admin {

	private ViewInterface view;
	private MemberRegistry memberRegistry;
	private Member currentMember;
	private Boat currentBoat;

	private MainAction currentAction;
	
	public Admin(MemberRegistry memberReg, ViewInterface inView) {
		view = inView;
		memberRegistry = memberReg;
	}

	public void startApplication() {
		view.displayWelcomeMsg();
		decideAction();
	}

	private void decideAction() {
		if(currentAction == null)
			currentAction = view.displayMainMenu();
		
		switch(currentAction) {
			case ADD_MEMBER: 
				addMember();
				break;
			case EDIT_MEMBER: 
				editMember();
				break;
			case VIEW_MEMBER:
				viewMember();
				break;
			case DELETE_MEMBER: 
				deleteMember();
				break;
			case LIST_MEMBERS:
				listMembers();
				break;
			case REGISTER_BOAT: 
				registerBoat();
				break;
			case EDIT_BOAT: 
				editBoat();
				break;
			case REMOVE_BOAT: 
				removeBoat();
				break;
			case EXIT: 
				exit();
				break;
			case SIMPLE_SEARCH:
				simpleMemberSearch();
				break;
			case COMPLEX_SEARCH:
				complexMemberSearch();
				break;
			case INVALID_CHOICE:
				resetMenu();
				break;
		}
	}

	private void simpleMemberSearch() {
		SearchAction choice = view.displaySimpleSearch();
		
		ISearchStrategy strategy;
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
				decideAction();
				return;
			default:
				resetMenu();
				return;
		}

		//TODO Data validation for search strings. Would differ depending on strategy 

		String searchString = view.getSearchString();
		SearchCriteria criteria = new SearchCriteria(strategy, searchString);
		
		ArrayList<Member> members = memberRegistry.getAllMembers();
		
		SearchCriteriaComposite composite = new SearchCriteriaComposite();
		composite.add(criteria);
		ArrayList<Member> searchResult = new Search().complexSearch(members, composite);
		
		view.displaySearchResults(searchResult, composite);
		decideAction();
	}

	private void complexMemberSearch() {
		Search search = new Search();
		ArrayList<Member> members = memberRegistry.getAllMembers();

		// Below age 50 and Born in January
		SearchCriteriaComposite composite = new SearchCriteriaComposite();
		composite.add(new SearchCriteria(new IsBelowAge(), "50"));
		composite.add(new SearchCriteria(new BornInMonth(), "01"));
		view.displaySearchResults(new ArrayList<Member>(search.complexSearch(members, composite)), composite);

		// Age => 60 && owns boat of type Sailboat && name starts with 'mo'
		SearchCriteriaComposite composite2 = new SearchCriteriaComposite();
		composite2.add(new SearchCriteria(new IsOverAge(), "60"));
		composite2.add(new SearchCriteria(new OwnsBoatOfType(), "Sailboat"));
		composite2.add(new SearchCriteria(new NameStartsWith(), "mo"));
		view.displaySearchResults(new ArrayList<Member>(search.complexSearch(members, composite2)), composite2);
		
		resetMenu();
	}
	
	private void addMember() {
		Member newMember = view.displayAddMember();
		if(newMember != null) {
			memberRegistry.addMember(newMember);
			view.displayMemberCreatedConfirmation();
		}
		resetMenu();
	}

	private void editMember() {
		view.displayEditMemberTitle();
		
		if(setCurrentMember()) {
			Member tempMember = view.displayEditMember(currentMember);
			if(tempMember == null) {
				resetMenu();
			}
			else {
				String newName = tempMember.getName();
				String newPNr = tempMember.getPNr();
				
				if(!newName.equals(currentMember.getName()) || !newPNr.equals(currentMember.getPNr())) {
					if(!newName.equals(currentMember.getName())) {
						currentMember.editName(newName);
						view.displayNameChangedConfirmation();
					}
					// if(!newPNr.equals(currentMember.getPNr())) {
					else {
						currentMember.editPNr(newPNr);
						view.displayPNrChangedConfirmation();
					}
					memberRegistry.saveDB();
				}
				else {
					//TODO temp
					view.displayNoChangesMadeMsg();

				}
				decideAction();
			}
		}
	}
	

	private void viewMember() {
		view.displayViewMemberTitle();

		if(setCurrentMember()) {
			view.displayMemberInfo(currentMember); 
			resetMenu();
		}
	}
	

	private void deleteMember() {
		view.displayDeleteMember(memberRegistry.getAllMembers());

		if(setCurrentMember()) {
			memberRegistry.deleteMember(currentMember);
			view.displayMemberDeletedConfirmation();

			resetMenu();
		}
	}


	private void listMembers() {
		ArrayList<Member> membersList = memberRegistry.getAllMembers();
		if(!view.displayMembersList(membersList))
			currentAction = null;
		decideAction();
	}

	
	private void registerBoat() {
		view.displayRegisterBoatTitle();

		if(setCurrentMember()) {
			Boat newBoat = view.displayRegisterBoat();
			if(newBoat != null) {
				currentMember.addBoat(newBoat);
				view.displayBoatRegisteredConfirmation();
				memberRegistry.saveDB();
			}
			resetMenu();
		}
	}


	private void editBoat() {
		if(currentMember == null)
			view.displayEditBoatTitle();

		if(setCurrentMember()) { // Member selection
			ArrayList<Boat> memberBoats = currentMember.getBoats();

			if (memberBoats.size() == 0) {
				view.displayMemberHasNoBoatsMsg();
				resetMenu();
			}
			else if(setCurrentBoat(memberBoats)) { // Boat selection
				Boat newBoat = view.displayEditBoat(currentBoat);
				if(newBoat == null) { // Aborted inputs
					currentBoat = null;
					decideAction();
				}
				else {
					BoatType newType = newBoat.getType();
					double newSize = newBoat.getSize();
					
					if(!newType.equals(currentBoat.getType())) {
						currentBoat.editType(newType);
						view.displayBoatTypeEditedConfirmation();
					}
					if(newSize != currentBoat.getSize()) {
						currentBoat.editSize(newSize);
						view.displayBoatSizeEditedConfirmation();
					}
					memberRegistry.saveDB();
					decideAction();
				}
			}
		}
	}


	private void removeBoat() {
		view.displayRemoveBoatTitle();
		
		if(setCurrentMember()) {
			ArrayList<Boat> memberBoats = currentMember.getBoats();

			if (memberBoats.size() == 0) {
				view.displayMemberHasNoBoatsMsg();
				currentAction = null;
				currentMember = null;
				decideAction();
			}
			else if(setCurrentBoat(memberBoats)) {
				currentMember.removeBoat(currentBoat);
				view.displayBoatDeletedConfirmation();
				memberRegistry.saveDB();

				resetMenu();
			}
		}
	}
	

	private void exit() {
		view.displayExitMsg();
	}


	private void resetMenu() {
		currentMember = null;
		currentBoat = null;
		currentAction = null;
		decideAction();
	}

	/**
	 * Used for navigation convenience. Controller can return to a submenu without having to type in the user id again. Sets currentMember if unset
	 */
	private boolean setCurrentMember() {
		if(currentMember == null) {
			Member selectedMember = view.displayMemberSelection(memberRegistry);
			if(selectedMember == null) {
				// currentAction = null;
				// decideAction();
				resetMenu();
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
	private boolean setCurrentBoat(ArrayList<Boat> memberBoats) {
		if(currentBoat == null) {
			Boat selectedboat = view.displayBoatSelection(memberBoats);
			if(selectedboat == null) {
				resetMenu();
				return false;
			}
			else {
				currentBoat = selectedboat;
			}
		}
		return true;
	}
}