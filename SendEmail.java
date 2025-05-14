// Java program to send email

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.*;

public class SendEmail 
{

   public static void main(String [] args) 
   {    
	    System.out.println("SimpleEmail Start");
		
	    String smtpHostServer = "smtp.example.com";
	    String emailID = "em98723122@gmail.com";
	    
	    Properties props = System.getProperties();

	    props.put("mail.smtp.host", smtpHostServer);

	    Session session = Session.getInstance(props, null);
	    
	    EmailUtil.sendEmail(session, emailID,"SimpleEmail Testing Subject", "SimpleEmail Testing Body");
   }
}

