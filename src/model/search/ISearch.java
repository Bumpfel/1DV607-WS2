package model.search;

import java.util.ArrayList;
import model.Member;

public interface ISearch {
    ArrayList<Member> returnFiltered(ArrayList<Member> members, String searchParameter);
}