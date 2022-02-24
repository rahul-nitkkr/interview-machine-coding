package strategy;

import model.Candidate;

import java.util.List;

public interface RankingStrategy {

    public List<Candidate> rankCandidates(List<Candidate> candidates);
}
