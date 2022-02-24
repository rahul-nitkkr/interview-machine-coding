import model.Candidate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CandidateFrequencyComparator implements Comparator<Candidate> {

    @Override
    public int compare(Candidate c1, Candidate c2) {
        return compare(new ArrayList<>(c1.getPositionFrequencies().values()), new ArrayList<>(c2.getPositionFrequencies().values()));
    }

    private int compare(List<Integer> l1, List<Integer> l2) {
        for (int i = 0; i < l1.size(); i++) {
            if (!l1.get(i).equals(l2.get(i))) {
                return l1.get(i) - l2.get(i);
            }
        }
        return 0;
    }
}
