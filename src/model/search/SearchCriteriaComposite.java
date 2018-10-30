package model.search;

import java.util.ArrayList;

public class SearchCriteriaComposite {
    private ArrayList<SearchCriteria> criterias = new ArrayList<>();

    public void add(SearchCriteria criteria) {
        criterias.add(criteria);
    }

    public ArrayList<SearchCriteria> get() {
        return new ArrayList<SearchCriteria>(criterias);
    }
}