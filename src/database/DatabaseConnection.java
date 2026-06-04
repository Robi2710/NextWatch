package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;

    private final String url;
    private final String user;
    private final String password;

    private DatabaseConnection() {
        this.url = readSetting("nextwatch.db.url", "NEXTWATCH_DB_URL",
                "jdbc:postgresql://localhost:5432/nextwatch");
        this.user = readSetting("nextwatch.db.user", "NEXTWATCH_DB_USER",
                System.getProperty("user.name", "postgres"));
        this.password = readSetting("nextwatch.db.password", "NEXTWATCH_DB_PASSWORD", "");
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC driver is missing from the classpath.", e);
        }
        return DriverManager.getConnection(url, user, password);
    }

    private String readSetting(String propertyName, String envName, String defaultValue) {
        String propertyValue = System.getProperty(propertyName);
        if (propertyValue != null && !propertyValue.isBlank()) {
            return propertyValue;
        }

        String envValue = System.getenv(envName);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        return defaultValue;
    }
}
