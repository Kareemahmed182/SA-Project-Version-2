package data_handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import domain.Session;

import java.util.List;

public class SessionDataHandler {
    private static final String FILE_PATH = "data_handlers/sessions.json";
    private static final DataHandler<Session> dataHandler = new DataHandler<>(FILE_PATH);

    public static List<Session> loadSessions() {
        List<Session> sessions = dataHandler.readData(new TypeReference<>() {});
        System.out.println("Debug: Loaded sessions - " + sessions.size());
        return sessions;
    }


    public static void saveSessions(List<Session> sessions) {
        dataHandler.writeData(sessions);
    }
}
