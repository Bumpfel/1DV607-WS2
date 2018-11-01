package model.search;

import model.search.strategies.ISearchStrategy;

public class SearchCriteria {
    ISearchStrategy strategy;
    String parameter;

    public SearchCriteria(ISearchStrategy strategy, String parameter) {
        this.strategy = strategy;
        this.parameter = parameter;
    }

    public String toString() {
        return strategy.toString() + " \"" + parameter + "\"";
    }
}