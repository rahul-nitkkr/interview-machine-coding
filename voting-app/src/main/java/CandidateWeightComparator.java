import model.Candidate;

import java.util.Comparator;

public class CandidateWeightComparator implements Comparator<Candidate> {

    public int compare(Candidate c1, Candidate c2) {
        return (int) (c1.getScore() - c2.getScore());
    }
}
