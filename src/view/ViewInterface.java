package view;

import java.util.ArrayList;

import model.Boat;
import model.Member;
import model.MemberRegistry;
import model.search.SearchCriteriaComposite;

public interface ViewInterface {

	public Member displayMemberSelection(MemberRegistry memberRegistry);
	public Boat displayBoatSelection(ArrayList<Boat> availableBoats);
	public String getInput();

	public String displayPasswordPrompt();
	public GuestAction displayGuestMainMenu();
	public AdminAction displayAdminMainMenu();

	public void displayWelcomeMsg();
	public void displayExitMsg();

	public void displayEditMemberTitle();
	public void displayViewMemberTitle();
	public void displayRegisterBoatTitle();
	public void displayEditBoatTitle();
	public void displayRemoveBoatTitle();
	
	public Member displayAddMember();
	public Member displayEditMember(Member member);
	public void displayDeleteMember(ArrayList<Member> membersList);
	
	public void displayMemberInfo(Member member);
	public boolean displayMembersList(ArrayList<Member> memberList);
	
	public Boat displayRegisterBoat();
	public Boat displayEditBoat(Boat boat);

	public ComplexSearchAction complexSearch(String activeFilters);
	public SearchAction displaySearchFilters();
	public void displaySearchResults(ArrayList<Member> memberList, SearchCriteriaComposite composite);
	public String getSearchString();
	
	public void displayMemberCreatedConfirmation();
	public void displayNameChangedConfirmation();
	public void displayPNrChangedConfirmation();
	public void displayMemberDeletedConfirmation();

	public void displayMemberHasNoBoatsMsg();
	public void displayBoatRegisteredConfirmation();
	public void displayBoatTypeEditedConfirmation();
	public void displayBoatSizeEditedConfirmation();
	public void displayBoatRemovedConfirmation();
	public void displayLogInMsg();
	public void displayLogOutMsg();

	public void displayMemberDoesNotExistError();
	public void displayInvalidMenuChoiceError();
	public void displayInvalidPasswordError();
	public void displayNoPasswordFileError();
	
	public void displayInvalidSearchParameterError();
	public void displayDuplicateSearchFilterReplacedMsg();
	public void displayContainsIdenticalCriteria();

	public enum GuestAction { 
		EXIT,
		ADMIN_LOGIN,
		VIEW_MEMBER, 
		LIST_MEMBERS, 
		SIMPLE_SEARCH,
		COMPLEX_SEARCH,
		INVALID_CHOICE,
	}

	public enum AdminAction { 
		EXIT,
		ADMIN_LOGOUT,
		ADD_MEMBER, 
		EDIT_MEMBER, 
		VIEW_MEMBER, 
		DELETE_MEMBER, 
		LIST_MEMBERS, 
		REGISTER_BOAT, 
		EDIT_BOAT, 
		REMOVE_BOAT,
		SIMPLE_SEARCH,
		COMPLEX_SEARCH,
		INVALID_CHOICE,
	}

	public enum SearchAction {
		BACK,
		BORN_IN_MONTH,
		IS_BELOW_AGE,
		IS_OVER_AGE,
		NAME_STARTS_WITH,
		OWNS_BOAT_TYPE,
		INVALID_CHOICE,
	}

	public enum ComplexSearchAction {
		BACK,
		ADD,
		SEARCH,
		RESET,
		INVALID_CHOICE,
	}
}
