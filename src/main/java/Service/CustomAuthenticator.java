package Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class CustomAuthenticator extends Authenticator {
    private final String username;
    private final String password;

    public CustomAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}

