package dao;

import Service.UserService;
import bean.User;
import db.DBContext;
import db.JDBIConnector;
import log.AbsModel;
import log.AbstractDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class UserDAO extends AbstractDao<User> {
    private static UserDAO instance;
    public static UserDAO getInstance(){
        if(instance ==null) instance= new UserDAO();
        return instance;
    }

    public static boolean toggleUserStatus(int userID, boolean disable) {
        String query = "UPDATE users SET active = ? WHERE id = ?";
        String action ="";
        int status = disable ? 0 : 1;
        int rowsUpdated = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createUpdate(query)
                        .bind(0, status)
                        .bind(1, userID)
                        .execute()
        );
        return rowsUpdated > 0;
    }



    public int GetId() throws SQLException {
        List<User> users = JDBIConnector.getJdbi().withHandle((handle) -> {
            return handle.createQuery("SELECT * FROM users WHERE id = (SELECT MAX(id) FROM users)")
                    .mapToBean(User.class)
                    .collect(Collectors.toList());
        });
        return users.get(0).getId();
    }


    public String userChangeInfo(String surname, String lastname, String username, String phone,String email){
        Connection conn = DBContext.getConnection();
        String sql = "update users set sur_name=? , last_name=? , user_name=? , phone=? where email=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, surname);
            ps.setString(2, lastname);
            ps.setString(3, username);
            ps.setString(4, phone);
            ps.setString(5, email);

            int i = ps.executeUpdate();

            if(i > 0) return "success";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public String userChangePassword(String email ,String newPassword){
        Connection conn = DBContext.getConnection();
        String sql = "update users set password = ? where email = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setString(2, email);

            int i = ps.executeUpdate();
            if(i > 0) return "success";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    //1. lấy người dùng theo email. đã check
    public static User getUserByEmail(String email){
        Optional<User> user = JDBIConnector.getJdbi().withHandle((handle ->
                handle.createQuery("select id,user_name,password,phone,email,sur_name,last_name,role,hash,active from users where email = ?")
                        .bind(0,email)
                        .mapToBean(User.class).stream().findFirst()
        ));
        return user.isEmpty() ? null : user.get();
    }
    //2. lấy người dùng theo id. đã check
    public static User selectUser(int id){
        Optional<User> user = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery("select id,user_name,password,phone,email,sur_name,last_name,role,hash,active from users where id = ?")
                        .bind(0, id)
                        .mapToBean(User.class).stream().findFirst());
        return user.isEmpty() ? null : user.get();
    }

    //    //3. lấy ra all user. đã check
    public static List<User> dsUsers(){
        List<User> users = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery("select id,user_name,password,phone,email,sur_name,last_name,role,hash,active from users").mapToBean(User.class).collect(Collectors.toList()));
        return users;
    }
    //    //xóa ng dùng theo email.đã check
    public static void deleteUser(int id){// đã check
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate("DELETE FROM users WHERE id = ?")
                        .bind(0,id)
                        .execute());
    }
    //    // thêm người dùng.đẫ check
    public static void insertUser(String email, String pass, String username, int role, String surname, String lastname, String phone, String hash, int active) {
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate("INSERT INTO users(email, password, user_name, role, sur_name, last_name, phone, hash, active) VALUES (?,?,?,?,?,?,?,?,?)")
                        .bind(0, email)
                        .bind(1, pass)
                        .bind(2, username)
                        .bind(3, role)
                        .bind(4, surname)
                        .bind(5, lastname)
                        .bind(6, phone)
                        .bind(7, hash)
                        .bind(8, active)
                        .execute()
        );
    }
    //
////    UPDATE `users` SET `email`='dinhvu@gmail.com',`pass`='123dc',`name`='Dinh Vu',`role`=0 WHERE`id`=2;
    // thay đổi thông tin người dùng.
        public static void updateUser(String surname,String lastname,String username,String phone,int active,int id) {
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate("UPDATE users SET sur_name=?,last_name=?,user_name=?,phone=?,active=? WHERE id=?")
                        .bind(0,surname)
                        .bind(1,lastname)
                        .bind(2,username)
                        .bind(3,phone)
                        .bind(4,active)
                        .bind(5,id)
                        .execute()
        );
    }
    public void LockUser(String email) {
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate("UPDATE users SET active=2 WHERE email=?")
                        .bind(0, email)
                        .execute()
        );
    }

    //// kiểm tra người dùng tồn tại.nếu người dùng ko tồn tại false và ngc lại
    public boolean isUserExists(String email) {
        User a= UserDAO.getUserByEmail(email);
        return a !=null;
    }
    //
    // lấy ra số lượng của của từng vai trò
    public static int numOfRole(int role,String search){
        Integer integer = JDBIConnector.getJdbi().withHandle(handle ->

                handle.createQuery("SELECT COUNT(*)  FROM users where role=? AND (last_name LIKE ? OR user_name LIKE ?)")

                        .bind(0,role)
                        .bind(1, "%" + search + "%")
                        .bind(2, "%" + search + "%")
                        .mapTo(Integer.class)
                        .one());
        return integer != null ?integer :0;
    }
    //    // Lấy ra 10 người .
    public static List<User>selectTen(int index){
        List<User> users = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT id,user_name,password,phone,email,sur_name,last_name,role,hash,active FROM users\n" +
                                "ORDER BY id\n" +
                                "LIMIT 5 OFFSET ?")
                        .bind(0,(index - 1) * 5)
                        .mapToBean(User.class)
                        .collect(Collectors.toList()));
        return users;
    }
    // lấy ra 5 người theo role.
    public static List<User>listOfRole(int role,int index){
        List<User> users = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT id,role,user_name,password,phone,email" +
                                ",sur_name,last_name,hash\n" +
                                "FROM users\n" +
                                "WHERE role = ?\n" +
                                "ORDER BY id\n" +
                                "LIMIT 5 OFFSET ? ")
                        .bind(0,role)
                        .bind(1,(index-1)*5)
                        .mapToBean(User.class)
                        .collect(Collectors.toList()));
        return users;
    }
    public static List<User> listOfRoleWithSearch(int role) {
        List<User> users = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT id, role,active, user_name, password, phone, email, sur_name, last_name, hash " +
                                "FROM users " +
                                "WHERE role = ? " +
                                "ORDER BY id")
                        .bind(0, role)
                        .mapToBean(User.class)
                        .collect(Collectors.toList()));
        return users;
    }


    @Override
    public boolean selectModel(int id) {
        super.selectModel(id);
        return true;
    }
    //INSERT INTO users(email, password, user_name, role, sur_name, last_name, phone, hash, active) VALUES (?,?,?,?,?,?,?,?,?)

    @Override
    public boolean insertModel(AbsModel model, String ip, int level, String address) {
        User user= (User) model;
        Integer i =JDBIConnector.getJdbi().withHandle(handle ->
                handle.createUpdate("INSERT INTO users(email, password, user_name, role, sur_name, last_name, phone, hash, active) VALUES (?,?,?,?,?,?,?,?,?)")
                        .bind(0,user.getEmail())
                        .bind(1,user.getPassword())
                        .bind(2,user.getUsername())
                        .bind(3,user.getRole())
                        .bind(4,user.getSurName())
                        .bind(5,user.getLastName())
                        .bind(6,user.getPhone())
                        .bind(7,user.getHash())
                        .bind(8,user.getActive())
                        .execute()
        );
        User newUser = UserDAO.getUserByEmail(user.getEmail());
        super.insertModel(newUser,ip,level,address);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteModel(AbsModel model, String ip, int level, String address) {
        super.deleteModel(model,ip,level,address);
        return true;
    }
    @Override
    public boolean updateModel(AbsModel modelBf,AbsModel modelAt, String ip, int level, String address) {
        User user= (User) modelBf;
        Integer i = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createUpdate("UPDATE users SET sur_name=?,last_name=?,user_name=?,phone=?,active=? WHERE id=?")
                        .bind(0,user.getSurName())
                        .bind(1,user.getLastName())
                        .bind(2,user.getUsername())
                        .bind(3,user.getPhone())
                        .bind(4,user.getActive())
                        .bind(5,user.getId())
                        .execute()
        );
        super.updateModel(modelBf,modelAt,ip,level,address);
        if(i==1) return true;
        return true;
    }

    //int id, int active, String username, String phone, String surname, String lastname
    public static void main(String[] args) {
    //"INSERT INTO users(email, password, user_name, role, sur_name, last_name, phone, hash, active) VALUES (?,?,?,?,?,?,?,?,?)"
        User a = new User(1,1,"dfgd","123123","0182989283","hiho@gmail.com","dfshdj","dfjgsdh");
        UserDAO.getInstance().insertModel(a,"010001",1,"1");
    }
}