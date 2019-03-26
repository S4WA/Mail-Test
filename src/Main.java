import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        send(args[0], args[1], "s4wa@outlook.com", "subject", "owo");
    }

    public static void send(String account, String password, String to, String subject, String content) {
        final String charset = "UTF-8", encoding = "base64";

        String host = "smtp-mail.outlook.com", port = "587", starttls = "true";
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", starttls);

        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");

        props.put("mail.debug", "true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(account, password);
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);

            // Set From:
            message.setFrom(new InternetAddress(account, "Mail Test"));
            // Set ReplyTo:
            message.setReplyTo(new Address[]{new InternetAddress(account)});
            // Set To:
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject(subject, charset);
            message.setText(content, charset);

            message.setHeader("Content-Transfer-Encoding", encoding);

            Transport.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
