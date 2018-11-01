package model.search.strategies;

import model.Member;
import java.util.ArrayList;
import java.util.Calendar;

public class IsBelowAge implements ISearchStrategy {

    //TODO duplicated code in IsOverAge
    public ArrayList<Member> returnFiltered(ArrayList<Member> members, String searchParameter) {
        ArrayList<Member> membersFiltered = new ArrayList<Member>();
        Calendar cal = Calendar.getInstance();

        for (Member member : members) {
            try {
                int memberBirthYear = Integer.parseInt(member.getPNr().substring(0, 2)) + 2000;
                int currentYear = cal.get(Calendar.YEAR);
                if (memberBirthYear > currentYear) {
                    memberBirthYear -= 100;
                }
                int memberAge = currentYear - memberBirthYear;

                if (memberAge < Integer.parseInt(searchParameter)) {
                    membersFiltered.add(member);
                }
            } catch (NumberFormatException exception) {
            }
        }

        return membersFiltered;
    }

    public String toString() {
        return "Is Below Age";
    }
}