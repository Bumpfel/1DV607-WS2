package model.search;

import model.Member;
import java.util.ArrayList;
import java.util.Calendar;

public class IsBelowAge implements ISearchStrategy {

    public ArrayList<Member> returnFiltered(ArrayList<Member> members, String searchParameter) {
        ArrayList<Member> membersFiltered = new ArrayList<Member>();
        Calendar cal = Calendar.getInstance();
       
        for(Member member : members) {
            int memberBirthYear = Integer.parseInt(member.getPNr().substring(0, 2));
            int currentYear = cal.get(Calendar.YEAR) - 2000;
            System.out.println(currentYear); //TODO ta bort
            String memberAge = "" + (currentYear - memberBirthYear);
            if(memberAge.compareTo(searchParameter) == -1) {
                membersFiltered.add(member);
            }
        }
                
        return membersFiltered;
    }
}