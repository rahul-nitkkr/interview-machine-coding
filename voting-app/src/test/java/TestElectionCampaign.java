import model.Candidate;
import org.junit.jupiter.api.Test;
import strategy.FrequencyBasedRankingStrategy;
import strategy.WeightBasedRankingStrategy;

import java.util.List;

public class TestElectionCampaign {

    @Test
    public void testElectionCampaign_weightBased() {
        List<String> voter1 = List.of("A" , "B" , "C");
        List<String> voter2 = List.of("B" , "A" , "C");
        List<String> voter3 = List.of("A" , "C" , "B");
        List<String> voter4 = List.of("C" , "A" , "B");
        List<String> voter5 = List.of("C" , "A" , "B");

        List<List<String>> votes = List.of(voter1 , voter2 , voter3 , voter4 , voter5);

        ElectionCampaign campaign = new ElectionCampaign(new WeightBasedRankingStrategy(new CandidateWeightComparator()));
        List<Candidate> result = campaign.runElection(votes);

        System.out.println(result);

    }

    @Test
    public void testElectionCampaign_frequencyBased() {
        List<String> voter1 = List.of("A" , "B" , "C");
        List<String> voter2 = List.of("B" , "A" , "C");
        List<String> voter3 = List.of("A" , "C" , "B");
        List<String> voter4 = List.of("C" , "A" , "B");
        List<String> voter5 = List.of("C" , "A" , "B");
        List<String> voter6 = List.of("a" , "B" , "C");

        List<List<String>> votes = List.of(voter1 , voter2 , voter3 , voter4 , voter5 , voter6);

        ElectionCampaign campaign = new ElectionCampaign(new FrequencyBasedRankingStrategy(new CandidateFrequencyComparator()));
        List<Candidate> result = campaign.runElection(votes);

        System.out.println(result);

    }
}
