package Service;

import bean.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendingEmail {
    private final String userEmail;
    private String myHash;

    public SendingEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public SendingEmail(String userEmail, String myHash) {
        this.userEmail = userEmail;
        this.myHash = myHash;
    }

    public String sendVerifyOrder(){

        return null;
    }

    // đây là phương thức gửi email chứa liên kết xác nhận khi người dùng yêu cầu muốn đặt lại mật khẩu
    public String sendFPassByEmail(){
        // tài khoản mk của email của mình
        String email = "linhson208@gmail.com";
        String pword = "towk gnyo yraf ohhh";
        // sử dụng properties để cấu hình các thuộc tính của máy chủ email
        Properties properties = new Properties();
        // cấu hình smtp, port, quyền tác giả, giao thức bảo mật mã hóa dữ liệu gửi đi
        properties.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        properties.put("mail.smtp.port", "587"); //TLS Port
        properties.put("mail.smtp.auth", "true"); //enable authentication
        properties.put("mail.smtp.starttls.enable", "true"); //enable
        properties.put("mail.smtp.timeout", "5000");

        //tạo phiên gửi email, truyền vào properties đã cấu hình và  tạo mới một authenticator để xác thực tài khoản email của mình

        Session session = Session.getDefaultInstance(properties, new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(email, pword);
            }
        });

        // tạo và cấu hình tin nhắn email
        // sử dụng MimeMessage để tạo tin nhắn email và cái này nó sẽ nhận một session
        MimeMessage message = new MimeMessage(session);
        try {
            // setfrom là người gửi
            message.setFrom(new InternetAddress(email));
            //addrecipient là người nhận email
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            //Đặt chủ đề cho email
            message.setSubject("Password verification email");
            //settext là đặt nội dung cho email
            String verificationLink = "Your Verification Link :: http://localhost:8081/ForgotPassword?action=createPass&key1=" + userEmail + "&key2=" + myHash;
            message.setText(verificationLink);
            // sử dụng transport để gửi tin nhắn đã được tạo ra
            Transport.send(message);
            return "success";
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    // đây là phương thức email dạng văn bản bình thường
    public String sendTextEmail(String messageContentContact, String subject){
        //bắt đầu tạo ra cấu hình email
        String email = "linhson208@gmail.com";
        String pword = "towk gnyo yraf ohhh";
        String host = "smtp.gmail.com";

        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        properties.put("mail.smtp.port", "587"); //TLS Port
        properties.put("mail.smtp.auth", "true"); //enable authentication
        properties.put("mail.smtp.starttls.enable", "true"); //enable


        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, pword);
            }
        });

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(email));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject(subject);
            message.setText(messageContentContact);
            Transport.send(message);
            return "success";
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    //Phương thức gửi mail cho người dùng để họ xác nhận khi đăng nhập

    public void sendMail(){
        String email = "linhson208@gmail.com";
        String pword = "towk gnyo yraf ohhh";
        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        properties.put("mail.smtp.port", "587"); //TLS Port
        properties.put("mail.smtp.auth", "true"); //enable authentication
        properties.put("mail.smtp.starttls.enable", "true"); //enable

        Session session = Session.getDefaultInstance(properties, new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(email, pword);
            }
        });

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(email));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject("Account registration verification email");
            String verificationLink = "Your Verification Link : http://localhost:8081/ActiveAccount?key1=" + userEmail + "&key2=" + myHash;

            message.setText(verificationLink);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    // gửi cảnh báo cho admin.
    public void sendWarning(String warning){
        String email = "linhson208@gmail.com";
        String pword = "towk gnyo yraf ohhh";
        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        properties.put("mail.smtp.port", "587"); //TLS Port
        properties.put("mail.smtp.auth", "true"); //enable authentication
        properties.put("mail.smtp.starttls.enable", "true"); //enable

        Session session = Session.getDefaultInstance(properties, new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(email, pword);
            }
        });

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(email));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject("Account registration verification email");

            message.setText(warning);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SendingEmail a= new SendingEmail("uyent612@gmail.com");
        a.sendTextEmail("Your public key has been reported and is now marked as expired. Please login to generate a new key: http://localhost:8081/login","reportedKey");
    }

}
