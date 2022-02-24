package strategy;

import model.Candidate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FrequencyBasedRankingStrategy implements RankingStrategy {

    private final Comparator<Candidate> candidateComparator;

    public FrequencyBasedRankingStrategy(Comparator<Candidate> candidateComparator) {
        this.candidateComparator = candidateComparator;
    }

    @Override
    public List<Candidate> rankCandidates(List<Candidate> candidates) {
        return candidates.stream().sorted(candidateComparator.reversed()).collect(Collectors.toList());
    }
}
