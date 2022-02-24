package model;

import java.util.Map;
import java.util.TreeMap;

public class Candidate {
    private final String name;
    private final Map<Integer , Integer> positionFrequencies;
    private Integer score;

    public Candidate(String name) {
        this.name = name;
        this.positionFrequencies = new TreeMap<>();
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
    }

    public Map<Integer, Integer> getPositionFrequencies() {
        return positionFrequencies;
    }

    public void updateScore(Integer updateScoreBy) {
        this.score += updateScoreBy;
    }

    public void updatePositionFrequency(Integer position) {
        Integer currFrequency = positionFrequencies.getOrDefault(position , 0);
        positionFrequencies.put(position , currFrequency + 1);
    }

    @Override
    public String toString() {
        return name;
    }
}
