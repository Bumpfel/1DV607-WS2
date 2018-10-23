package model.search;

import java.util.ArrayList;
import model.Boat;
import model.Member;

public class OwnsBoatOfType implements ISearchStrategy {
    ArrayList<Member> filteredList = new ArrayList<>();

	@Override
	public ArrayList<Member> returnFiltered(ArrayList<Member> members, String searchParameter) {
		for (Member member : members) {
            if (!member.getBoats().isEmpty()) {
                for (Boat boat : member.getBoats()) {
                    if (boat.getType().toString() == searchParameter)
                        filteredList.add(member);
                }
            }
        }

        return filteredList;
	}

}