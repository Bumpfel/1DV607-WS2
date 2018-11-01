package model.search.strategies;

import java.util.ArrayList;
import model.Member;

public interface ISearchStrategy {

    ArrayList<Member> returnFiltered(ArrayList<Member> members, String searchParameter);
    public boolean isValid(String param);
    public boolean allowsDuplicate();
}