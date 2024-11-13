package edu.sdccd.cisc190.model;

public class Leaderboard {
    // TODO: move to config.properties
    public static final int MAX_NUM_SCORES = 10;

    private PlayerScore[] playerScores;

    public Leaderboard() {
        playerScores = new PlayerScore[MAX_NUM_SCORES];
    }

    public PlayerScore[] getPlayerScores() {
        return playerScores;
    }

    public void setPlayerScores(PlayerScore[] playerScores) {
        this.playerScores = playerScores;
    }

    public void addScore(PlayerScore newPlayerScore) {
        int i = findInsertionIndex(newPlayerScore);
        rightShiftScoresFromIndex(i);
        playerScores[i] = newPlayerScore;
    }

    private void rightShiftScoresFromIndex(int index) {
        for(int i = playerScores.length - 2; i >= index; i--) {
            playerScores[i + 1] = playerScores[i];
        }
    }

    private int findInsertionIndex(PlayerScore newPlayerScore) {
        int low = 0;
        int high = getEndIndex() - 1;
        while(low <= high) {
            int mid = (low + high) / 2;
            if(newPlayerScore.compareTo(playerScores[mid]) < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    private int getEndIndex() {
        int i = 0;
        while(i < MAX_NUM_SCORES) {
            if(playerScores[i] == null) { return i;}
            i++;
        }
        return i;
    }
}
