package controller;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {

    public static void sendEmail(String toEmail, String subject, String body) {
        // Cấu hình thuộc tính kết nối SMTP (ví dụ với Gmail)
        String host = "smtp.gmail.com";
        String fromEmail = "your-email@gmail.com";
        String password = "your-email-password";

        // Cấu hình các thuộc tính gửi email
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");

        // Tạo session
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            String newSubject = "Xác thực cấp lại key";
            String newBody = "zzz";

            message.setSubject(subject);
            message.setText(body);


            Transport.send(message);
            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
