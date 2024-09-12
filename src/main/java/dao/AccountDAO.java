package dao;

import bean.User;
import db.DBContext;

import db.JDBIConnector;
import log.AbsModel;
import log.AbstractDao;
import utils.PasswordUtils;

import javax.persistence.criteria.CriteriaBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


public class AccountDAO extends AbstractDao<User> {
    private static AccountDAO instance;

    public static AccountDAO getInstance() {
        if(instance ==null) instance = new AccountDAO();
        return instance;
    }


    //Phương thức lấy ra id cao nhất.
    public int GetId() throws SQLException {
        String sql = "SELECT * FROM users WHERE id = (SELECT MAX(id) FROM users)";


        Connection conn = DBContext.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return rs.getInt("id");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public User loginAccount(AbsModel model, String ip, int level, String address) {
        User user = (User) model;
        User u = null;
        String sql = "SELECT id, role, user_name, password, phone, email, sur_name, last_name, hash, active,picture,create_at FROM users WHERE email = ? AND active = 1";
        List<User> users = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql).bind(0, user.getEmail()).mapToBean(User.class).stream().collect(Collectors.toList())
        );
        System.out.println("Number of users found: " + users.size()); // Log số lượng người dùng tìm thấy
        if (users.size() != 1) {
            super.login(user, "Login failed!", ip, level, address);
        } else {
            u = users.get(0);
            System.out.println(u);
            if(PasswordUtils.verifyPassword(user.getPassword(), u.getPassword())){
                System.out.println("success!");

                super.login(u,"Login success!",ip,level,address);
            } else {
                super.login(user,"Login failed!",ip,level,address);
                u = null;
            }
        }
        return u;
    }

    public User checkAccountExist(String email){
        String sql = "select id, role,user_name, password, phone, email, sur_name, last_name, hash, active from users where email = ?";
        Connection conn = DBContext.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return new User(
                        rs.getInt("id"),
                        rs.getInt("role"),
                        rs.getString("user_name"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("sur_name"),
                        rs.getString("last_name"),
                        rs.getString("hash"),
                        rs.getInt("active")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public String signUp(String email,String pass,String username,String surname,String lastname,String phone,String hash,String ip, int level, String address) {
        String sql = "insert into users(user_name, password, phone, email, sur_name, last_name, hash, role, active,login_by) values (?,?,?,?,?,?,?,0,0,0)";
        Connection conn = DBContext.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, pass);
            ps.setString(3, phone);
            ps.setString(4, email);
            ps.setString(5, surname);
            ps.setString(6, lastname);
            ps.setString(7, hash);
            int i = ps.executeUpdate();
            if(i != 0){
                super.signUp(email, "insert user success !", ip, level, address);
                return "success";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        super.signUp(email, "insert user failed !", ip, level, address);
        return null;
    }



    public String signUp2(String email, String pass, String username, String surname, String lastname, String phone, String hash, Integer login_By){
        String sql = "insert into users(user_name, password, phone, email, sur_name, last_name, hash, role, active, login_by) values (?,?,?,?,?,?,?,0,1,?)";
        Connection conn = DBContext.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, pass);
            ps.setString(3, phone);
            ps.setString(4, email);
            ps.setString(5, surname);
            ps.setString(6, lastname);
            ps.setString(7, hash);
            ps.setInt(8, login_By);
            int i = ps.executeUpdate();
            if(i != 0){
                return "success";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }



    public String activeAccount(String email, String hash){
        Connection con = DBContext.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("select email, hash, active from users where email = ? and hash = ? and active = 0 and login_by = 0");
            ps.setString(1, email);
            ps.setString(2, hash);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                PreparedStatement ps1 = con.prepareStatement("update users set active = 1 where email = ? and hash = ?");
                ps1.setString(1, email);
                ps1.setString(2, hash);
                int i = ps1.executeUpdate();
                if(i == 1){
                    return "success";
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public String forgetPassword(String email, String newPassword) {
        Connection con = DBContext.getConnection();
        String sql = "update users set password = ? where email = ? and login_by = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            // Trong câu lệnh SQL UPDATE, chúng ta cần set password = ? và email = ?
            ps.setString(1, newPassword); // Chỗ này sử dụng newPassword thay vì email
            ps.setString(2, email);
            ps.setInt(3, 0);// Chỗ này sử dụng email thay vì newPassword
            int i = ps.executeUpdate();
            if (i > 0) {
                return "Success";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Nên đóng kết nối sau khi sử dụng xong
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public boolean updateLoginFail(String email, int i) {
        String sql = "update users set lock_fail = ? where email = ?";
        int exe = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createUpdate(sql).bind(0, i).bind(1, email).execute());
        return exe==1?true:false;
    }

    public int getLoginFail(String email) {
        String sql = "SELECT lock_fail FROM users WHERE email = ?";
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql).bind(0, email).mapTo(Integer.class).first());
    }


    public static void main(String[] args) {
        //System.out.println(AccountDAO.getInstance().getLoginFail("21130526@st.hcmuaf.edu.vn"));
//        System.out.println(AccountDAO.getInstance().updateLoginFail("21130526@st.hcmuaf.edu.vn", 1));
        String email= "21130514@st.hcmuaf.edu.vn";
        String pass= "123123";
        User a = new User();
        a.setEmail(email);
        a.setPassword(pass);
        User b =new User(1,1,"Son","4297f44b13955235245b2497399d7a93","0123456789","Son@gmail.com","Son","dsf");

//        System.out.println( AccountDAO.getInstance().loginAccount(a,"00999",1,"asgdhg"));
//        AccountDAO.getInstance().signUp2(email,pass, "hihoo","hihoo","hihoo","hihoo","hihodsfo",1 );

        System.out.println( AccountDAO.getInstance().activeAccount("21130526@st.hcmuaf.edu.vn","399c59d7969e572156e23a7a3799b270"));
    }


}