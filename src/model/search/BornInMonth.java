package model.search;

import java.util.ArrayList;

import model.Member;

public class BornInMonth implements ISearchStrategy {
    ArrayList<Member> filteredList = new ArrayList<>();

	@Override
	public ArrayList<Member> returnFiltered(ArrayList<Member> members, String searchParameter) {
        
        for (Member member : members) {            
            String month = member.getPNr().substring(2,4);            
            System.out.println(month);
            if (month.equalsIgnoreCase(searchParameter))
                filteredList.add(member);
        }
        
		return filteredList;
	}
}