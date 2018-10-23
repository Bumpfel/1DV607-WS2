package model.search;

import model.Member;
import java.util.ArrayList;
import java.util.Date;

class IsBelowAge implements ISearchStrategy {

    public ArrayList<Member> returnFiltered(ArrayList<Member> members, String searchParameter) {
        ArrayList<Member> membersFiltered = new ArrayList<Member>();

        for(Member member : members) {
            int memberBirthYear = Integer.parseInt(member.getPNr().substring(0, 4));
            int currentYear = new Date().getYear() + 1900;
            String memberAge = "" + (currentYear - memberBirthYear);
            if(memberAge.compareTo(searchParameter) == -1) {
                membersFiltered.add(member);
            }
        }
                
        return membersFiltered;
    }
}