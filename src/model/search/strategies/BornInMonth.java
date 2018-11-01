package model.search.strategies;

import java.util.ArrayList;

import model.Member;

public class BornInMonth implements ISearchStrategy {
    
    @Override
	public ArrayList<Member> returnFiltered(ArrayList<Member> members, String searchParameter) {
        ArrayList<Member> filteredList = new ArrayList<>();
        
        String parameter = searchParameter;
        if(searchParameter.length() == 1)
            parameter = "0" + parameter;

        for (Member member : members) {
            String month = member.getPNr().substring(2,4);
            if (month.equalsIgnoreCase(parameter)) {
                filteredList.add(member);
                break;
            }
        }
        
		return filteredList;
    }

    public String toString() {
        return "Born in Month";
    }
}