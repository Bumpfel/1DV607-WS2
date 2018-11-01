package view;

import java.util.ArrayList;

import model.Member;
import model.search.SearchCriteriaComposite;
import view.ViewInterface.ComplexSearchAction;
import view.ViewInterface.SearchAction;

public class EngSearchConsole {

	public ComplexSearchAction complexSearch(String activeFilters, EngConsole console) {
		String extraPrint;
		if(activeFilters.equals(""))
			extraPrint = "No active filters\n\n";
		else
			extraPrint = "Active filters:\n" + activeFilters + "\n";

		String[] options = {
			"Add search filter",
			"Search",
			"Reset",
			"Back",
		};
		int choice = console.createMenu("Complex member search", extraPrint, options);
		
		ComplexSearchAction[] actions = ComplexSearchAction.values();

		if(!console.isValidMenuChoice(choice, options.length - 1)) {
			return ComplexSearchAction.INVALID_CHOICE;
		}
		return actions[choice];
	}

	
	void displaySearchResults(ArrayList<Member> list, SearchCriteriaComposite composite, EngConsole console, EngMemberConsole memberConsole) {
		console.printSeparation();
		console.displayTitle("Search results");
		System.out.println("Search criterias");
		System.out.println(composite.toString());

		if(list.isEmpty())
			System.out.println("-- No members matching your search criterias --");
		else
		memberConsole.displayMembersCompact(list);
		console.displayWait();
	}


	public SearchAction displaySearchFilters(EngConsole console) {
		String[] options = {
			"Born in month",
			"Is below age",
			"Is over age", 
			"Name starts with",
			"Owns boat of type",
			"Back",
		};
		
		int choice = console.createMenu("Search filters", "", options);
		SearchAction[] searchActions = SearchAction.values();
		
		if(!console.isValidMenuChoice(choice, options.length - 1)) {
			return SearchAction.INVALID_CHOICE;
		}
		
		return searchActions[choice];
	}
}