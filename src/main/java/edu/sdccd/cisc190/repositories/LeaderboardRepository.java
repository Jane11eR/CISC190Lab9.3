package edu.sdccd.cisc190.repositories;

import edu.sdccd.cisc190.model.Leaderboard;
import edu.sdccd.cisc190.model.PlayerScore;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

abstract public class LeaderboardRepository {
    protected final Properties config;
    protected final Leaderboard leaderboard;

    public LeaderboardRepository(Properties config) {
        this.config = config;
        leaderboard = new Leaderboard();
    }

    public static LeaderboardRepository getInstance(Properties config) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> loadedClass = Class.forName(config.getOrDefault("leaderboard.repository.class","edu.sdccd.cisc190.repositories.LeaderboardFileRepository").toString());
        Constructor<?> constructor = loadedClass.getConstructor(Properties.class);

        return (LeaderboardRepository) constructor.newInstance(config);
    }

    public Leaderboard getLeaderboard() {return leaderboard;}

    abstract public void loadLeaderboard() throws IOException;
    abstract public void addPlayerScore(PlayerScore newPlaerScore) throws IOException;
}
