import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class SwiftEntryTests {
    public static void main(String[] args) throws Exception {
        Path testDir = Files.createTempDirectory(Paths.get(args[0]), "swiftentry-test-");
        try {
            Path csv = testDir.resolve("students.csv");
            Student special = new Student("42", "Doe, \"Jane\"\nJr.", "jane@example.com", 1, 2, 3, 4);
            StudentCsv.write(csv, Arrays.asList(special));
            List<Student> roundTrip = StudentCsv.read(csv);
            check(roundTrip.size() == 1, "CSV round trip record count");
            check(roundTrip.get(0).getName().equals(special.getName()), "CSV quoted name round trip");
            check(roundTrip.get(0).getEmail().equals(special.getEmail()), "CSV email round trip");

            byte[] original = Files.readAllBytes(csv);
            Student broken = new Student("99", "Broken") {
                @Override public String getName() { throw new IllegalStateException("simulated write failure"); }
            };
            try {
                StudentCsv.write(csv, Arrays.asList(special, broken));
                throw new AssertionError("Expected simulated write failure");
            } catch (IllegalStateException expected) {
                check(Arrays.equals(original, Files.readAllBytes(csv)), "old CSV survives failed write");
            }

            Path sample = Paths.get(args[1]);
            List<Student> merged = StudentCsv.read(sample);
            check(merged.size() == 2, "duplicate IDs merge into two students");
            check(merged.get(0).getTimesAbsent() == 4, "highest absence count retained");
            check(!merged.get(0).getEmail().isEmpty(), "nonempty email retained");

            Path teacherFile = testDir.resolve("teacher.csv");
            StudentCsv.write(teacherFile, merged);
            Teacher teacher = new Teacher("test-password", teacherFile);
            teacher.addStudent("new-id", "Doe, Jane", "new@example.com");
            check(Teacher.GetData(teacherFile).get(2).getEmail().equals("new@example.com"),
                    "new email persists immediately");
            try {
                teacher.addStudent("new-id", "Duplicate", "");
                throw new AssertionError("Expected duplicate ID rejection");
            } catch (IllegalArgumentException expected) {
                check(Teacher.GetData(teacherFile).size() == 3, "duplicate ID did not alter CSV");
            }
            try {
                teacher.removeStudent(-1);
                throw new AssertionError("Expected unknown student rejection");
            } catch (IllegalArgumentException expected) {
                check(Teacher.GetData(teacherFile).size() == 3, "unknown removal did not alter CSV");
            }
            teacher.removeStudent(teacher.findStudentIndex("new-id"));
            check(Teacher.GetData(teacherFile).size() == 2, "student removal persists");

            InputStream oldInput = System.in;
            try {
                System.setIn(new ByteArrayInputStream(
                        "9am\n09:00:00\n08:00:00\n09:00:00\n10:00:00\n".getBytes(StandardCharsets.UTF_8)));
                teacher.setStartendTime();
            } finally {
                System.setIn(oldInput);
            }

            Scans onTime = new Scans("on-time");
            onTime.setStartTime(LocalTime.MAX);
            onTime.Scan();
            check(!onTime.returnLate(), "scan before class start is on time");
            Scans late = new Scans("late");
            late.setStartTime(LocalTime.MIN);
            late.Scan();
            check(late.returnLate(), "scan after class start is late");
            System.out.println("SwiftEntry regression tests passed");
        } finally {
            Files.deleteIfExists(testDir.resolve("students.csv"));
            Files.deleteIfExists(testDir.resolve("teacher.csv"));
            Files.deleteIfExists(testDir);
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
