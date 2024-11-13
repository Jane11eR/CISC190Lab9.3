package edu.sdccd.cisc190.repositories;

import edu.sdccd.cisc190.Main;
import edu.sdccd.cisc190.model.PlayerScore;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
class LeaderboardFileRepositoryTest {
    public static final String FILE_NAME = "testPlayerScores.dat";

    @Test
    void loadLeaderboard() throws Exception {
        Properties config = Main.loadConfigFile();
        LeaderboardRepository leaderboardRepository = LeaderboardRepository.getInstance(config);
        leaderboardRepository.addPlayerScore(new PlayerScore("Alice", 10));
        leaderboardRepository.addPlayerScore(new PlayerScore("Charlie", 5));
        leaderboardRepository.addPlayerScore(new PlayerScore("Bob", 5));
        leaderboardRepository.addPlayerScore(new PlayerScore("Denise", 15));

        leaderboardRepository = LeaderboardRepository.getInstance(config);
        leaderboardRepository.loadLeaderboard();

        assertEquals(5, leaderboardRepository.getLeaderboard().getPlayerScores()[0].getScore());
        assertEquals("Bob", leaderboardRepository.getLeaderboard().getPlayerScores()[0].getPlayerName());
        assertEquals(5, leaderboardRepository.getLeaderboard().getPlayerScores()[1].getScore());
        assertEquals("Charlie", leaderboardRepository.getLeaderboard().getPlayerScores()[1].getPlayerName());
        assertEquals(10, leaderboardRepository.getLeaderboard().getPlayerScores()[2].getScore());
        assertEquals("Alice", leaderboardRepository.getLeaderboard().getPlayerScores()[2].getPlayerName());
        assertEquals(15, leaderboardRepository.getLeaderboard().getPlayerScores()[3].getScore());
        assertEquals("Denise", leaderboardRepository.getLeaderboard().getPlayerScores()[3].getPlayerName());

        File file = new File(FILE_NAME);
        if(file.delete()) System.out.printf("%s deleted successfully.", FILE_NAME);
    }
}