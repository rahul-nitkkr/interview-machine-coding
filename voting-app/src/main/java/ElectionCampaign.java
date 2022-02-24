import model.Candidate;
import strategy.RankingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElectionCampaign {

    private final RankingStrategy rankingStrategy;

    public ElectionCampaign(RankingStrategy rankingStrategy) {
        this.rankingStrategy = rankingStrategy;
    }

    public List<Candidate> runElection(List<List<String>> votes) {
        List<String> candidates = votes.get(0);
        Map<String, Candidate> candidateMap = new HashMap<String, Candidate>();

        for (int voter = 0; voter < votes.size(); voter++) {
            for (int rank = 0; rank < candidates.size(); rank++) {
                String candidateName = votes.get(voter).get(rank).toUpperCase();
                Candidate thisCandidate = candidateMap.getOrDefault(candidateName, new Candidate(candidateName));
                thisCandidate.updateScore(candidates.size() - rank);
                thisCandidate.updatePositionFrequency(rank);
                candidateMap.put(candidateName, thisCandidate);
            }
        }

        return rankingStrategy.rankCandidates(new ArrayList<>(candidateMap.values()));
    }
}
