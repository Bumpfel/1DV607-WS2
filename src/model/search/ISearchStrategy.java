package model.search;

import java.util.ArrayList;
import model.Member;

interface ISearchStrategy {
    ArrayList<Member> returnFiltered(ArrayList<Member> members, String searchParameter);
}