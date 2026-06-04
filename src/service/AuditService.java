package service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class AuditService {
    private static AuditService instance;

    private final Path auditFile;

    private AuditService() {
        String configuredPath = System.getProperty("nextwatch.audit.file");
        if (configuredPath == null || configuredPath.isBlank()) {
            configuredPath = "audit.csv";
        }
        this.auditFile = Path.of(configuredPath);
        ensureHeader();
    }

    public static synchronized AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public synchronized void logAction(String actionName) {
        try (BufferedWriter writer = Files.newBufferedWriter(
                auditFile,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        )) {
            writer.write(escape(actionName) + "," + LocalDateTime.now());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("[AuditService] Could not write audit entry: " + e.getMessage());
        }
    }

    private void ensureHeader() {
        try {
            if (Files.notExists(auditFile) || Files.size(auditFile) == 0) {
                try (BufferedWriter writer = Files.newBufferedWriter(
                        auditFile,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND
                )) {
                    writer.write("nume_actiune,timestamp");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("[AuditService] Could not initialize audit file: " + e.getMessage());
        }
    }

    private String escape(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
