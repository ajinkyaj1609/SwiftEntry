public class TestEmail {
    public static void main(String[] args) {
        String email = System.getenv("SWIFTENTRY_TEST_EMAIL");
        String msg = "This is a test email from SwiftEntry. Please ignore this message.";
        if (email == null || email.isEmpty()) {
            System.err.println("Set SWIFTENTRY_TEST_EMAIL to a test recipient.");
            System.exit(1);
        }
        try {
            TLSEmail.sendEmail(email, msg);
            System.out.println("Email sent successfully.");
        } catch (Exception e) {
            System.err.println("Email failed: " + e.getMessage());
            System.exit(1);
        }
    }
}
