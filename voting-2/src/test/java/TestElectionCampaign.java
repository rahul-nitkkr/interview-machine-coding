import org.junit.jupiter.api.Test;

import java.util.List;

public class TestElectionCampaign {

    @Test
    public void testCampaign_SingleVoter() {
        ElectionCampaign electionCampaign = new ElectionCampaign();
        System.out.println(electionCampaign.runCampaign(List.of("A", "B", "A", "C", "E", "C", "D")));

    }
}
