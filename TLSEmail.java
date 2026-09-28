import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class TLSEmail {
    public static void sendEmail(String email, String msg) throws MessagingException {
        String username = System.getenv("SWIFTENTRY_SMTP_USERNAME");
        String password = System.getenv("SWIFTENTRY_SMTP_PASSWORD");
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalStateException(
                    "Set SWIFTENTRY_SMTP_USERNAME and SWIFTENTRY_SMTP_PASSWORD before sending email");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email recipient is required");
        }

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("Lesson Plan");

        MimeBodyPart header = new MimeBodyPart();
        header.setContent("<h3>SwiftEntry Has detected that you were absent from class today. "
                + "Here is your teacher's lesson plan:</h3><br>", "text/html; charset=utf-8");
        MimeBodyPart body = new MimeBodyPart();
        body.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(header);
        multipart.addBodyPart(body);
        message.setContent(multipart);
        Transport.send(message);
    }
}
