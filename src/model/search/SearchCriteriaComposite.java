package model.search;

import java.util.ArrayList;

import model.search.strategies.ISearchStrategy;

public class SearchCriteriaComposite {
    private ArrayList<SearchCriteria> criterias = new ArrayList<>();

    public void add(SearchCriteria criteria) {
        criterias.add(criteria);
    }

    public ArrayList<SearchCriteria> get() {
        return new ArrayList<SearchCriteria>(criterias);
    }

    public boolean isEmpty() {
        return criterias.size() == 0;
    }

    public boolean contains(ISearchStrategy strategy) {
        for(SearchCriteria criteria : criterias) {
            if(criteria.strategy.getClass() == strategy.getClass()) {
                return true;
            }
        }
        return false;
    }

    public boolean containsIdenticalCriteria(SearchCriteria newCriteria) {
        for(SearchCriteria criteria : criterias) {
            if(criteria.strategy.getClass() == newCriteria.strategy.getClass() && criteria.parameter.equalsIgnoreCase(newCriteria.parameter)) {
                return true;
            }
        }
        return false;
    }

    public void removeDuplicate(ISearchStrategy strategy) {
        for(SearchCriteria criteria : criterias) {
            if(criteria.strategy.getClass() == strategy.getClass()) {
                criterias.remove(criteria);
                break;
            }
        }
    }
    
    public String toString() {
        StringBuffer str = new StringBuffer();
        for(SearchCriteria criteria : criterias) {
            str.append(criteria + "\n");
        }
        return str.toString();
    }
}