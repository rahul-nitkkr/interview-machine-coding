import model.Candidate;
import model.CandidateComparator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElectionCampaign {
    private final Map<String, Candidate> candidates;

    public ElectionCampaign() {
        this.candidates = new HashMap<>();
    }

    public String runCampaign(List<String> votes) {
        int voter = 0;
        for (String candidateName : votes) {
            Candidate candidate = candidates.getOrDefault(candidateName, new Candidate(candidateName));
            candidate.updateVoter(voter);
            candidate.incrementVote();
            voter += 1;
            candidates.put(candidateName, candidate);
        }

        return candidates.values().stream().sorted(new CandidateComparator().reversed()).limit(1).findAny().get().getName();
    }
}
