package controller;

import bean.User;
import bean.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.AccountDAO;
import db.DBProperties;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.util.DigestUtils;

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

@WebServlet("/loginWithFacebook")
public class LoginFacebookHandler extends HttpServlet {

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

    String getToken(final String code) throws ClientProtocolException, IOException {
        String response = Request.Post(DBProperties.FACEBOOK_LINK_GET_TOKEN).bodyForm(Form.form()
                        .add("client_id", DBProperties.FACEBOOK_CLIENT_ID)
                        .add("client_secret", DBProperties.FACEBOOK_CLIENT_SECRET)
                        .add("redirect_uri", DBProperties.FACEBOOK_REDIRECT_URI)
                        .add("code", code)
                        .add("grant_type", DBProperties.GOOGLE_GRANT_TYPE)
                        .build())
                .execute().returnContent().asString();

        JsonObject json = new Gson().fromJson(response, JsonObject.class);
        return json.get("access_token").toString().replaceAll("\"", "");
    }

    public User getUserInfo(final String accessToken) throws ClientProtocolException, IOException, SQLException {
        String response = Request.Get(DBProperties.FACEBOOK_LINK_GET_USER_INFO)
                .addHeader("Authorization", "Bearer " + accessToken)
                .execute().returnContent().asString();
        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        System.out.println(json.toString());
        User user = new User();
            user.setUsername(json.has("name") ? json.get("name").getAsString() : null);
            user.setEmail(json.has("email") ? json.get("email").getAsString() : user.getUsername() + "@users.noreply.facebook.com");
            user.setLastname(json.has("last_name") ? json.get("last_name").getAsString() : null);
            user.setSurname(json.has("first_name") ? json.get("first_name").getAsString() : null);
            if (json.has("picture") && json.getAsJsonObject("picture").has("data")) {
                JsonObject obj = json.getAsJsonObject("picture").getAsJsonObject("data");
                user.setPicture(obj.has("url") ? obj.get("url").getAsString() : null);
            }
            user.setLoginBy(3);
        return user;
    }
}
