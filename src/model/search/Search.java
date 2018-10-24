package model.search;

import java.util.ArrayList;
import java.util.List;

import model.Member;

public class Search {
    public ArrayList<Member> search(ArrayList<Member> members, ArrayList<ISearchStrategy> strategies, List<String> searchParameter) {

        for (int i = 0; i < strategies.size(); i++) {
            members = strategies.get(i).returnFiltered(members, searchParameter.get(i));
        }

        return members;

    }
}