package edu.sdccd.cisc190.model;

import java.io.Serializable;

public class PlayerScore implements Serializable, Comparable<PlayerScore> {
    private final String playerName;
    private final float score;

    public PlayerScore(String playerName, float score) {
        this.playerName = playerName;
        this.score = score;
    }
    public String getPlayerName() {return playerName;}
    public float getScore() {return score;}

    /**
     * sort ascending by score, then ascending playerName
     * @param that the PlayerScore to be compared.
     * @return 0 if this==that, >0 if this>that, <0 if this<that
     */
    @Override
    public int compareTo(PlayerScore that) {
        if(that == null) return 1;
        int scoreCmp = Float.compare(this.score, that.score);
        if(scoreCmp == 0) {
            return this.playerName.compareTo(that.playerName);
        }
        return scoreCmp;
    }
}
