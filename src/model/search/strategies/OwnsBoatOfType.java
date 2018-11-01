package model.search.strategies;

import java.util.ArrayList;
import model.Boat;
import model.Member;
import model.Boat.BoatType;

public class OwnsBoatOfType implements ISearchStrategy {
    
    @Override
	public ArrayList<Member> returnFiltered(ArrayList<Member> members, String searchParameter) {
        ArrayList<Member> filteredList = new ArrayList<>();
		for (Member member : members) {
            if (!member.getBoats().isEmpty()) {

                for (Boat boat : member.getBoats()) {                    
                    if (boat.getType().toString().equalsIgnoreCase(searchParameter)) {
                        filteredList.add(member);
                        break;
                    }
                }

            }
        }

        return filteredList;
    }
    
    public boolean isValid(String param) {
        for(BoatType bType : BoatType.values()) {
            if(bType.toString().equalsIgnoreCase(param))
                return true;
        }
        return false;
    }

    public boolean allowsDuplicate() {
        return true;
    }

    public String toString() {
        return "Owns Boat of Type";
    }
}