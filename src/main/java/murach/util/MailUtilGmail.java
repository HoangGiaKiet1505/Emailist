package murach.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class MailUtilGmail {

    private static final String USERNAME =
            System.getenv("MAIL_USERNAME");

    private static final String PASSWORD =
            System.getenv("MAIL_PASSWORD");

    public static void sendMail(String to, String from,
                                String subject, String body,
                                boolean bodyIsHTML)
            throws MessagingException {

        Properties props = new Properties();

        // Gmail SMTP - Port 587
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");

        Session session = Session.getInstance(props);

        Message message = new MimeMessage(session);

        message.setSubject(subject);

        if (bodyIsHTML) {
            message.setContent(body, "text/html; charset=UTF-8");
        } else {
            message.setText(body);
        }

        Address fromAddress = new InternetAddress(USERNAME);
        Address toAddress = new InternetAddress(to);

        message.setFrom(fromAddress);
        message.setRecipient(
                Message.RecipientType.TO,
                toAddress
        );

        Transport transport = session.getTransport("smtp");

        try {
            transport.connect(USERNAME, PASSWORD);

            transport.sendMessage(
                    message,
                    message.getAllRecipients()
            );

        } finally {
            transport.close();
        }
    }

    public static String getSenderAddress() {
        return USERNAME;
    }
}