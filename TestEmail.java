public class TestEmail {
    public static void main(String[] args) {
        String email = ""; //Must be set or code will crash
        String msg = "This is a test email from SwiftEntry. Please ignore this message.";
        TLSEmail.sendEmail(email, msg);

        System.out.println("Email sent successfully.");
    }
}