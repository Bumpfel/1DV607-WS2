package model.search.strategies;

import java.util.ArrayList;
import model.Member;

public class NameStartsWith implements ISearchStrategy {
    
    @Override
	public ArrayList<Member> returnFiltered(ArrayList<Member> members, String searchParameter) {
        ArrayList<Member> filteredList = new ArrayList<>();
        int len = searchParameter.length();
        for (Member member : members) {
            if(member.getName().length() < len)
                continue;
            if (searchParameter.equalsIgnoreCase(member.getName().substring(0, len))) {
                filteredList.add(member);
            }
        }
		return filteredList;
    }
    
    public String toString() {
        return "Name starts with";
    }
}