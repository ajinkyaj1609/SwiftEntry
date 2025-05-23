import java.util.Properties;
import jakarta.mail.*;
import jakarta.activation.*;
import jakarta.mail.internet.*;
import org.eclipse.angus.*;

public class TLSEmail {
	private static final String username = "swiftentryhost@gmail.com";
	private static final String password = "tbct ttux fzta klms";
	private static Properties prop;
	/**
	   Outgoing Mail (SMTP) Server
	   requires TLS or SSL: smtp.gmail.com (use authentication)
	   Use Authentication: Yes
	   Port for TLS/STARTTLS: 587
	 */
	public static void main(String[] args) {
		
		
		prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.tls.trust", "smtp.gmail.com");
		prop.put("mail.stmp.tls.enable", "true");
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		
	}

		public static void sendEmail(String email, String msg) {

		Session session = Session.getInstance(prop, new Authenticator() {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
		});
	
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(
			Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("Lesson Plan");

	

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);

			message.setContent(multipart);
			
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
