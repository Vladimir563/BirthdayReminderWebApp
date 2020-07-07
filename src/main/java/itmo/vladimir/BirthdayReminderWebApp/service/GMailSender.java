package itmo.vladimir.BirthdayReminderWebApp.service;

import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

@Service
public class GMailSender
{
//    public static void main(String[] args) throws IOException, MessagingException
//    {
//        final Properties properties = new Properties();
//        properties.load(GMailSender.class.getClassLoader().getResourceAsStream("mail.properties"));
//
//        Session mailSession = Session.getDefaultInstance(properties);
//        MimeMessage message = new MimeMessage(mailSession);
//        message.setFrom(new InternetAddress("vladimir3591fom@gmail.com"));
//        message.addRecipient(Message.RecipientType.TO, new InternetAddress("vladimir3591f@mail.ru"));
//        message.setSubject("hello!");
//        message.setText("Hello from BB!");
//
//        Transport transport = mailSession.getTransport();
//        transport.connect(null,"563street");
//        transport.sendMessage(message,message.getAllRecipients());
//        transport.close();
//    }

    public void sendMessage(String sender,String receiver, String subject, String congratulationsText) throws IOException, MessagingException
    {
        final Properties properties = new Properties();
        properties.load(GMailSender.class.getClassLoader().getResourceAsStream("mail.properties"));

        Session mailSession = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(mailSession);
//        message.setFrom(new InternetAddress("vladimir3591fom@gmail.com"));
        message.setFrom(new InternetAddress(properties.getProperty("mail.smtps.user")));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
        message.setSubject(subject);
        message.setText(congratulationsText);

        Transport transport = mailSession.getTransport();
        transport.connect(null,"563street");
        transport.sendMessage(message,message.getAllRecipients());
        transport.close();
    }
}
