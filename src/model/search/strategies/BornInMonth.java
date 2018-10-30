package model.search.strategies;

import java.util.ArrayList;

import model.Member;

public class BornInMonth implements ISearchStrategy {
    ArrayList<Member> filteredList = new ArrayList<>();

	@Override
	public ArrayList<Member> returnFiltered(ArrayList<Member> members, String searchParameter) {
        
        for (Member member : members) {            
            String month = member.getPNr().substring(2,4);                        
            if (month.equalsIgnoreCase(searchParameter))
                filteredList.add(member);
        }
        
		return filteredList;
    }

    public String toString() {
        return "Born in Month";
    }
}