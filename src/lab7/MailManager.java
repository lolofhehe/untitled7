package lab7;

import lab6.Server;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

class MailManager {
    private final static String PROPERTIES_PATH = "/home/s265092/lab7/mail.properties";
    private Session mailSession;
    private final String sender;

    MailManager() {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get(PROPERTIES_PATH))) {
            props.load(in);
        } catch (IOException exc) {
            System.out.println("Cannot read mail properties.");
        }
        this.sender = Server.mailLogin;

        if (!Server.debug){
            return;}
        mailSession = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, Server.mailPassword);
            }
        });

    }

    /**
     * Метод, отправляющий сообщение по почте от указанного в свойствах отправителя.
     * @param to Получатель
     * @param subject Тема
     * @param text Текст сообщения
     */
    void sendMessage(String to, String subject, String text) {
        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
        } catch (MessagingException exc) {
            exc.printStackTrace();
            System.out.println(
                    "Отправитель: "+ sender + "\nПолучатель: " + to + "\nТема: " + subject + "\n" + text);
        }
    }
}
