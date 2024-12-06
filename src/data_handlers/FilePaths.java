package data_handlers;

public class FilePaths {
    private static final String BASE_DIR = System.getProperty("user.dir") + "/data_handlers";

    public static final String USERS_FILE = "data/users.json";
    public static final String ATTENDEES_FILE = "data/attendees.json";
    public static final String SESSIONS_FILE = "data/sessions.json";
    public static final String FEEDBACK_FILE = "data/feedback.json";
    public static final String CERTIFICATES_FILE = "data/certificates.json";

    static {
        // Debugging: Print resolved paths
        System.out.println("Resolved Paths:");
        System.out.println("Users File: " + USERS_FILE);
        System.out.println("Attendees File: " + ATTENDEES_FILE);
        System.out.println("Sessions File: " + SESSIONS_FILE);
        System.out.println("Feedback File: " + FEEDBACK_FILE);
        System.out.println("Certificates File: " + CERTIFICATES_FILE);
    }
}
