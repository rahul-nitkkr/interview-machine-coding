package model;

import java.util.Objects;

public class Candidate {
    private final String name;
    private Integer votes;
    private Integer lastVoter;

    public Candidate(String name) {
        this.name = name;
        this.votes = 0;
    }

    public String getName() {
        return name;
    }

    public Integer getVotes() {
        return votes;
    }

    public Integer getLastVoter() {
        return lastVoter;
    }

    public void incrementVote() {
        this.votes += 1;
    }

    public void updateVoter(Integer voter) {
        this.lastVoter = voter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return name.equals(candidate.name) &&
                Objects.equals(votes, candidate.votes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, votes);
    }
}
