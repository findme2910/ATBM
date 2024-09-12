//package controller.GoogleService;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import org.apache.commons.codec.binary.Base64;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.fluent.Form;
//import org.apache.http.client.fluent.Request;
//import vn.edu.hcmuaf.fit.model.User;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//public class OAuth2Callback {
//    private static String request;
//    private static String id;
//    private static String secret;
//    private static String uri;
//    private static String link;
//    private static Enum<OAuth2Service> apis;
//
//    public static String getToken(final String code, Enum<OAuth2Service> type) throws ClientProtocolException, IOException {
//        boolean valid = setProperties(type);
//        String response;
//        JsonObject json;
//        if (valid && !apis.equals(TWITTER) && !apis.equals(GITHUB)) {
//            response = Request.Post(request).bodyForm(Form.form()
//                            .add("client_id", id)
//                            .add("client_secret", secret)
//                            .add("redirect_uri", uri)
//                            .add("code", code)
//                            .add("grant_type", GOOGLE_GRANT_TYPE)
//                            .build())
//                    .execute().returnContent().asString();
//
//            json = new Gson().fromJson(response, JsonObject.class);
//            return json.get("access_token").toString().replaceAll("\"", "");
//        } else if (apis.equals(TWITTER)) {
//            String clientCredentials = id + ":" + secret;
//            String base64Credentials = new String(Base64.encodeBase64(clientCredentials.getBytes()));
//            response = Request.Post(request)
//                    .addHeader("Authorization", "Basic " + base64Credentials)
//                    .bodyForm(Form.form()
//                            .add("grant_type", "authorization_code")
//                            .add("code", code)
//                            .add("redirect_uri", uri.replace("&state=state", ""))
//                            .add("code_verifier", "challenge")
//                            .build())
//                    .execute().returnContent().asString();
//
//            json = JsonParser.parseString(response).getAsJsonObject();
//            return json.get("access_token").getAsString();
//        } else {
//            response = Request.Post(request)
//                    .addHeader("Accept", "application/json")
//                    .bodyForm(Form.form()
//                            .add("client_id", id)
//                            .add("client_secret", secret)
//                            .add("code", code)
//                            .build())
//                    .execute().returnContent().asString();
//
//            json = JsonParser.parseString(response).getAsJsonObject();
//            return json.get("access_token").getAsString();
//        }
//    }
//
//    public static User getUserInfo(final String accessToken, boolean bearer) throws ClientProtocolException, IOException, SQLException {
//        String response;
//        if (bearer) {
//            response = Request.Get(link)
//                                .addHeader("Authorization", "Bearer " + accessToken)
//                                .execute().returnContent().asString();
//        } else {
//            response = Request.Get(link + accessToken).execute().returnContent().asString();
//        }
//        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
//        User user = new User();
//        if (apis.equals(TWITTER)) {
//            json = json.getAsJsonObject("data");
//            String username = json.has("username") ? json.get("username").getAsString() : null;
//            user.setUsername(username);
//            user.setEmail(username + "@users.noreply.twitter.com");
//            user.setFullName(json.has("name") ? json.get("name").getAsString() : null);
//            user.setAvatar(json.has("profile_image_url") ? json.get("profile_image_url").getAsString() : null);
//            user.setLoginBy(3);
//        } else if (apis.equals(GOOGLE)) {
//            user.setUsername(json.has("given_name") ? json.get("given_name").getAsString() : null);
//            user.setEmail(json.has("email") ? json.get("email").getAsString() : null);
//            user.setFullName(json.has("name") ? json.get("name").getAsString() : null);
//            user.setAvatar(json.has("picture") ? json.get("picture").getAsString() : null);
//            user.setLoginBy(1);
//        } else if (apis.equals(FACEBOOK)) {
//            user.setUsername(json.has("last_name") ? json.get("last_name").getAsString() : null);
//            user.setEmail(json.has("email") ? json.get("email").getAsString() : null);
//            user.setFullName(json.has("name") ? json.get("name").getAsString() : null);
//            if (json.has("picture") && json.getAsJsonObject("picture").has("data")) {
//                JsonObject obj = json.getAsJsonObject("picture").getAsJsonObject("data");
//                user.setAvatar(obj.has("url") ? obj.get("url").getAsString() : null);
//            }
//            user.setLoginBy(2);
//        } else if (apis.equals(DISCORD)) {
//            user.setUsername(json.has("username") ? json.get("username").getAsString() : null);
//            user.setEmail(json.has("email") ? json.get("email").getAsString() : null);
//            user.setFullName(json.has("global_name") ? json.get("global_name").getAsString() : null);
//            user.setAvatar(json.has("avatar") ? json.get("avatar").getAsString() : null);
//            user.setLoginBy(4);
//        } else if (apis.equals(GITHUB)) {
//            String userName = json.has("login") ? json.get("login").getAsString() : null;
//            user.setUsername(userName);
//            user.setEmail(userName + "@users.noreply.github.com");
//            user.setFullName(json.has("name") ? json.get("name").getAsString() : null);
//            user.setAvatar(json.has("avatar_url") ? json.get("avatar_url").getAsString() : null);
//            user.setLoginBy(5);
//        }
//        return user;
//    }
//
//    public static boolean setProperties(Enum<OAuth2Service> type) {
//        if (type.equals(GOOGLE)) {
//            request = GOOGLE_LINK_GET_TOKEN;
//            id = GOOGLE_CLIENT_ID;
//            secret = GOOGLE_CLIENT_SECRET;
//            uri = GOOGLE_REDIRECT_URI;
//            link = GOOGLE_LINK_GET_USER_INFO;
//            apis = GOOGLE;
//        } else if (type.equals(FACEBOOK)) {
//            request = FACEBOOK_LINK_GET_TOKEN;
//            id = FACEBOOK_CLIENT_ID;
//            secret = FACEBOOK_CLIENT_SECRET;
//            uri = FACEBOOK_REDIRECT_URI;
//            link = FACEBOOK_LINK_GET_USER_INFO;
//            apis = FACEBOOK;
//        } else if (type.equals(DISCORD)) {
//            request = DISCORD_LINK_GET_TOKEN;
//            id = DISCORD_CLIENT_ID;
//            secret = DISCORD_CLIENT_SECRET;
//            uri = DISCORD_REDIRECT_URI;
//            link = DISCORD_LINK_GET_USER_INFO;
//            apis = DISCORD;
//        } else if (type.equals(TWITTER)) {
//            request = TWITTER_LINK_GET_TOKEN;
//            id = TWITTER_CLIENT_ID;
//            secret = TWITTER_CLIENT_SECRET;
//            uri = TWITTER_REDIRECT_URI;
//            link = TWITTER_LINK_GET_USER_INFO;
//            apis = TWITTER;
//        } else if (type.equals(GITHUB)) {
//            request = GITHUB_LINK_GET_TOKEN;
//            id = GITHUB_CLIENT_ID;
//            secret = GITHUB_CLIENT_SECRET;
//            link = GITHUB_LINK_GET_USER_INFO;
//            apis = GITHUB;
//        }
//        else return false;
//        return true;
//    }
//}
