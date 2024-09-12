package controller;

import bean.User;
import bean.Util;
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

@WebServlet("/loginByGithub")
public class LoginGithubHandler extends HttpServlet {

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
        String response = Request.Post(DBProperties.GITHUB_LINK_GET_TOKEN)
                .addHeader("Accept", "application/json")
                .bodyForm(Form.form()
                        .add("client_id", DBProperties.GITHUB_CLIENT_ID)
                        .add("client_secret", DBProperties.GITHUB_CLIENT_SECRET)
                        .add("code", code)
                        .build())
                .execute().returnContent().asString();

        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        return json.get("access_token").getAsString();
    }

    public User getUserInfo(final String accessToken) throws ClientProtocolException, IOException, SQLException {
        String response = Request.Get(DBProperties.GITHUB_LINK_GET_USER_INFO)
                .addHeader("Authorization", "Bearer " + accessToken)
                .execute().returnContent().asString();
        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        User user = new User();
        String userName = json.has("login") ? json.get("login").getAsString() : null;
        user.setUsername(userName);
        user.setEmail(userName + "@users.noreply.github.com");
        user.setLastname(json.has("name") ? json.get("name").getAsString() : null);
        user.setPicture(json.has("avatar_url") ? json.get("avatar_url").getAsString() : null);
        user.setLoginBy(5);
        return user;
    }
}
