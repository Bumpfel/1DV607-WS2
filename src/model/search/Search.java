package model.search;

import java.util.ArrayList;
import model.Member;

public class Search {
    public ArrayList<Member> search(ArrayList<Member> members, ISearchStrategy strategy, String searchParameter) {

        return strategy.returnFiltered(members, searchParameter);

    }
}