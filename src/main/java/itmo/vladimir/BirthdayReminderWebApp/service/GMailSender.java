package itmo.vladimir.BirthdayReminderWebApp.service;

import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

@Service
public class GMailSender
{
    public void sendMessage(String sender,String receiver, String subject, String congratulationsText) throws IOException, MessagingException
    {
        final Properties properties = new Properties();
        properties.load(GMailSender.class.getClassLoader().getResourceAsStream("mail.properties"));

        Session mailSession = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(properties.getProperty("mail.smtps.user")));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
        message.setSubject(subject);
        message.setText(congratulationsText);

        Transport transport = mailSession.getTransport();
        transport.connect(null,properties.getProperty("mail.smtps.password"));
        transport.sendMessage(message,message.getAllRecipients());
        transport.close();
    }
}
