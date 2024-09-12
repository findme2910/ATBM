package controller;

import bean.User;
import bean.Util;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.AccountDAO;
import db.DBProperties;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.util.DigestUtils;

import javax.json.Json;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Random;

@WebServlet("/loginByTwitter")
public class LoginTwitterHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        String accessToken =getToken(code);
        User user, userCheck;
        Random random = new Random();
        random.nextInt(999999);

        String myHash = DigestUtils.md5DigestAsHex((""+random).getBytes());

        Timestamp currentTimestamp= Util.getCurrentTimestamp();
        HttpSession session = request.getSession();
        try {
            user= getUserInfo(accessToken);
            userCheck= AccountDAO.getInstance().checkAccountExist(user.getEmail());
            if(userCheck == null){
                String str = AccountDAO.getInstance().signUp2(user.getEmail(), null, user.getUsername(), user.getSurName() ,user.getLastName() ,user.getPhone(), myHash, user.getLoginBy());

                if(str.equals("success")){
                    session.setAttribute("user", user);
                    response.sendRedirect("./HomePageController");
                }
            }else{
                session.setAttribute("user", user);
                response.sendRedirect("./HomePageController");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getToken(final String code) throws ClientProtocolException, IOException {
        String clientCredentials = DBProperties.TWITTER_CLIENT_ID + ":" + DBProperties.TWITTER_CLIENT_SECRET;
        String base64Credentials = new String(Base64.encodeBase64(clientCredentials.getBytes()));
        String response = Request.Post(DBProperties.TWITTER_LINK_GET_TOKEN)
                .addHeader("Authorization", "Basic " + base64Credentials)
                .bodyForm(Form.form()
                        .add("grant_type", "authorization_code")
                        .add("code", code)
                        .add("redirect_uri", DBProperties.TWITTER_REDIRECT_URI.replace("?state=state", ""))
                        .add("code_verifier", "challenge")
                        .build())
                .execute().returnContent().asString();

        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        return json.get("access_token").getAsString();
    }

    public User getUserInfo(final String accessToken) throws ClientProtocolException, IOException, SQLException {
        String response = Request.Get(DBProperties.TWITTER_LINK_GET_USER_INFO)
                .addHeader("Authorization", "Bearer " + accessToken)
                .execute().returnContent().asString();
        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        User user = new User();
        json = json.getAsJsonObject("data");
        String username = json.has("username") ? json.get("username").getAsString() : null;
        user.setUsername(username);
        user.setEmail(username + "@users.noreply.twitter.com");
        user.setLastname(json.has("name") ? json.get("name").getAsString() : null);
        user.setPicture(json.has("profile_image_url") ? json.get("profile_image_url").getAsString() : null);
        user.setLoginBy(4);
        return user;
    }
}
