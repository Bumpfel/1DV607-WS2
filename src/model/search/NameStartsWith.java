package model.search;

import java.util.ArrayList;
import model.Member;

public class NameStartsWith implements ISearchStrategy {
    ArrayList<Member> filteredList = new ArrayList<>();

	@Override
	public ArrayList<Member> returnFiltered(ArrayList<Member> members, String searchParameter) {
        int len = searchParameter.length();
        for (Member member : members) {
            if (searchParameter.equalsIgnoreCase(member.getName().substring(0, len))) {                
                filteredList.add(member);
            }
        }
		return filteredList;
	}

}