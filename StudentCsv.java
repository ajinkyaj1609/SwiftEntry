import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class StudentCsv {
    private static final String HEADER = "ID,Name,Email,TimesLate,TimesAbsent,TimesTooLongOutClass,TimeSpentOutOfClass";

    private StudentCsv() {}

    public static List<Student> read(Path path) throws IOException {
        if (!Files.exists(path)) {
            throw new IOException("Student file does not exist: " + path);
        }
        String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        List<List<String>> rows = parse(content);
        if (rows.isEmpty() || !rows.get(0).equals(parse(HEADER).get(0))) {
            throw new IOException("Invalid student CSV header in " + path);
        }
        Map<String, Student> byId = new LinkedHashMap<>();
        for (int i = 1; i < rows.size(); i++) {
            List<String> row = rows.get(i);
            if (row.size() != 7) {
                throw new IOException("Expected 7 fields in student record " + (i + 1));
            }
            try {
                String id = row.get(0).trim();
                String name = row.get(1).trim();
                if (id.isEmpty() || name.isEmpty()) {
                    throw new IOException("Blank ID or name in student record " + (i + 1));
                }
                Student incoming = new Student(id, name, row.get(2).trim(),
                        Integer.parseInt(row.get(3).trim()), Integer.parseInt(row.get(4).trim()),
                        Integer.parseInt(row.get(5).trim()), Integer.parseInt(row.get(6).trim()));
                Student previous = byId.get(id);
                if (previous == null) {
                    byId.put(id, incoming);
                } else if (!previous.getName().equals(name)) {
                    throw new IOException("ID " + id + " has conflicting names; resolve the CSV before running");
                } else {
                    // Repeated historical rows are snapshots, so keep the highest count in each column.
                    String email = incoming.getEmail().isEmpty() ? previous.getEmail() : incoming.getEmail();
                    byId.put(id, new Student(id, name, email,
                            Math.max(previous.getTimesLate(), incoming.getTimesLate()),
                            Math.max(previous.getTimesAbsent(), incoming.getTimesAbsent()),
                            Math.max(previous.getTimesTooLongOutClass(), incoming.getTimesTooLongOutClass()),
                            Math.max(previous.getTimeSpentOutOfClass(), incoming.getTimeSpentOutOfClass())));
                    System.err.println("Merged duplicate student ID " + id + " from CSV");
                }
            } catch (NumberFormatException e) {
                throw new IOException("Invalid number in student record " + (i + 1), e);
            }
        }
        return new ArrayList<>(byId.values());
    }

    public static void write(Path path, List<Student> students) throws IOException {
        Path absolute = path.toAbsolutePath();
        Path temporary = Files.createTempFile(absolute.getParent(), "students-", ".csv.tmp");
        try {
            try (BufferedWriter writer = Files.newBufferedWriter(temporary, StandardCharsets.UTF_8)) {
                writer.write(HEADER);
                writer.newLine();
                for (Student student : students) {
                    writer.write(escape(student.getId()) + "," + escape(student.getName()) + ","
                            + escape(student.getEmail()) + "," + student.getTimesLate() + ","
                            + student.getTimesAbsent() + "," + student.getTimesTooLongOutClass() + ","
                            + student.getTimeSpentOutOfClass());
                    writer.newLine();
                }
            }
            // If the filesystem cannot replace atomically, preserve the original file and fail.
            Files.move(temporary, absolute, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (AtomicMoveNotSupportedException e) {
            throw new IOException("Filesystem does not support atomic student CSV replacement", e);
        } finally {
            Files.deleteIfExists(temporary);
        }
    }

    private static String escape(String value) {
        String text = value == null ? "" : value;
        if (text.contains(",") || text.contains("\"") || text.contains("\n") || text.contains("\r")) {
            return "\"" + text.replace("\"", "\"\"") + "\"";
        }
        return text;
    }

    private static List<List<String>> parse(String content) throws IOException {
        List<List<String>> rows = new ArrayList<>();
        List<String> row = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean quoted = false;
        boolean closedQuote = false;
        for (int i = 0; i < content.length(); i++) {
            char ch = content.charAt(i);
            if (quoted) {
                if (ch == '"' && i + 1 < content.length() && content.charAt(i + 1) == '"') {
                    field.append('"');
                    i++;
                } else if (ch == '"') {
                    quoted = false;
                    closedQuote = true;
                } else {
                    field.append(ch);
                }
            } else if (ch == '"' && field.length() == 0 && !closedQuote) {
                quoted = true;
            } else if (ch == ',') {
                row.add(field.toString());
                field.setLength(0);
                closedQuote = false;
            } else if (ch == '\n' || ch == '\r') {
                row.add(field.toString());
                if (!(row.size() == 1 && row.get(0).isEmpty())) {
                    rows.add(row);
                }
                row = new ArrayList<>();
                field.setLength(0);
                closedQuote = false;
                if (ch == '\r' && i + 1 < content.length() && content.charAt(i + 1) == '\n') {
                    i++;
                }
            } else if (closedQuote || ch == '"') {
                throw new IOException("Malformed CSV quoting");
            } else {
                field.append(ch);
            }
        }
        if (quoted) {
            throw new IOException("Unclosed CSV quote");
        }
        if (!row.isEmpty() || field.length() > 0 || closedQuote) {
            row.add(field.toString());
            rows.add(row);
        }
        return rows;
    }
}
