package edu.sdccd.cisc190.repositories;

import edu.sdccd.cisc190.model.PlayerScore;

import java.io.*;
import java.util.Properties;

public class LeaderboardFileRepository extends LeaderboardRepository {
    private final File file;
    public LeaderboardFileRepository(Properties config) {
        super(config);
        this.file = new File(config.getOrDefault("leaderboard.repository.path", "leaderboard.dat").toString());
    }

    private boolean createFile() throws IOException {
        if(!file.exists()) {
            return file.createNewFile();
        }
        return false;
    }

    @Override
    public void loadLeaderboard() throws IOException {
        if(createFile() || file.length() == 0) return;

        FileInputStream fis = new FileInputStream(file);

        try(fis; ObjectInputStream ois = new ObjectInputStream(fis)) {
            leaderboard.setPlayerScores((PlayerScore[]) ois.readObject());
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }

    }

    @Override
    public void addPlayerScore(PlayerScore newPlaerScore) throws IOException {
        leaderboard.addScore(newPlaerScore);

        FileOutputStream fos = new FileOutputStream(file, false);

        try(fos; ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(leaderboard.getPlayerScores());
        }
    }
}
