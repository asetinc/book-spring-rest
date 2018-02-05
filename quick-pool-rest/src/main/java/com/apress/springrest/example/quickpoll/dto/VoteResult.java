package com.apress.springrest.example.quickpoll.dto;

import java.util.Collection;

public class VoteResult {
    private int totalVotes;
    private Collection<OptionsCount> results;

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }

    public Collection<OptionsCount> getResults() {
        return results;
    }

    public void setResults(Collection<OptionsCount> results) {
        this.results = results;
    }
}
