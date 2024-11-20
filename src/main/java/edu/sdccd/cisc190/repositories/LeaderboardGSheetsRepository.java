package edu.sdccd.cisc190.repositories;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import edu.sdccd.cisc190.model.PlayerScore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.*;

public class LeaderboardGSheetsRepository extends LeaderboardRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(LeaderboardGSheetsRepository.class);
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private final Sheets sheets;

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES =
            Collections.singletonList(SheetsScopes.SPREADSHEETS);

    public LeaderboardGSheetsRepository(Properties config) throws IOException {
        super(config);
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT;
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException e) {
            throw new IOException(e);
        }
        final String spreadsheetId = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms";
        final String range = "Class Data!A2:E";
        sheets =
                new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(config.getProperty("leaderboard.gsheets.application.name"))
                        .build();
    }

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        String credentialsFilePath = (String) config.getOrDefault("leaderboard.gsheets.credentials.path", "/credentials.json");
        InputStream in = LeaderboardGSheetsRepository.class.getResourceAsStream(credentialsFilePath);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + credentialsFilePath);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File((String) config.getOrDefault("leaderboard.gsheets.tokens.path", "tokens"))))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    @Override
    public void loadLeaderboard() throws IOException {
        ValueRange response = sheets.spreadsheets().values()
                .get(config.getProperty("leaderboard.gsheets.spreadsheet.id"), (String) config.getOrDefault("leaderboard.gsheets.spreadsheet.range", "Leaderboard!A2:B"))
                .execute();
        List<List<Object>> values = response.getValues();

        if(values == null || values.isEmpty()) return;

        PlayerScore[] importedPlayerScores = values.stream().map(row -> new PlayerScore(row.getFirst().toString(), Float.parseFloat(row.get(1).toString()))).toArray(PlayerScore[]::new);

        for(PlayerScore playerScore: importedPlayerScores) {
            leaderboard.addScore(playerScore);
        }
    }

    @Override
    public void addPlayerScore(PlayerScore newPlaerScore) throws IOException {
        leaderboard.addScore(newPlaerScore);

        UpdateValuesResponse result = null;

        ValueRange body = new ValueRange()
                .setValues((List) Arrays.stream(leaderboard.getPlayerScores()).filter(Objects::nonNull)
                        .map((ps) -> Arrays.asList(ps.getPlayerName(), String.format("%.1f", ps.getScore())))
                        .toList()
                );

        result = sheets.spreadsheets().values().update(config.getProperty("leaderboard.gsheets.spreadsheet.id"), (String) config.getOrDefault("leaderboard.gsheets.spreadsheet.range", "Leaderboard!A2:B"), body)
                .setValueInputOption("RAW")
                .execute();

        LOGGER.info("{} cells updated.", result.getUpdatedCells());
    }
}