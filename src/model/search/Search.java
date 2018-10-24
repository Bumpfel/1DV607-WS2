package model.search;

import java.util.ArrayList;
import model.Member;

public class Search {
    public ArrayList<Member> search(ArrayList<Member> members, ArrayList<ISearchStrategy> strategies, String searchParameter) {

        for (ISearchStrategy strategy : strategies) {
            members = strategy.returnFiltered(members, searchParameter);
        }
        
        return members;

    }
}