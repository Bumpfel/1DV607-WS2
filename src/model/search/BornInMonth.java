package model.search;

import java.util.ArrayList;

import model.Member;

class BornInMonth implements ISearchStrategy {
    ArrayList<Member> filteredList = new ArrayList<>();

	@Override
	public ArrayList<Member> returnFiltered(ArrayList<Member> members, String searchParameter) {
        for (Member member : members) {
            String month = member.getPNr().substring(2,4);
            if (month == searchParameter)
                filteredList.add(member);
        }
		return filteredList;
	}
}