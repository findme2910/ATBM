package bean;


import log.AbsModel;
import log.IModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
@Data
public class User extends AbsModel implements Serializable {
    private int id, role,active ,loginBy, loginTimes;;
    private String username, password, phone, email, surname, lastname,hash,picture;
    private Date createAt,updateAt;
    private LocalDateTime lastActiveTime;
    public int getLoginBy() {
        return loginBy;
    }

    public void setLoginBy(int loginBy) {
        this.loginBy = loginBy;
    }

    public int getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(int loginTimes) {
        this.loginTimes = loginTimes;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }



    public User() {
    }

    public User(int role, int active, String username, String password, String phone, String email, String surname, String lastname) {
        this.role = role;
        this.active = active;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.surname = surname;
        this.lastname = lastname;
    }
    //email, password, user_name, role, sur_name, last_name, phone, hash, active


    public User(int role, int active, String username, String password, String phone, String email, String surname, String lastname, String hash) {
        this.role = role;
        this.active = active;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.surname = surname;
        this.lastname = lastname;
        this.hash = hash;
    }

    public User(int id, int role, String username, String password, String phone, String email, String surname, String lastname, String hash, int active) {
        this.id = id;
        this.role = role;
        this.active = active;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.surname = surname;
        this.lastname = lastname;
        this.hash = hash;
    }

    public User(int id, String username, String password, String phone, String email, String surname, String lastname, int role, String hash) {

        this.id = id;
        this.role = role;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.surname = surname;
        this.lastname = lastname;
        this.hash = hash;
        this.active = active;
    }
    //email, password, user_name, role, sur_name, last_name, phone, hash, active

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }


    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurName() {
        return surname;
    }

    public void setSurName(String surname) {
        this.surname = surname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }


    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    // Phương thức dưới để xác định vai trò của người theo role
    public String roleString(){
        if(this.role ==0){
            return "User";
        }else{
            return "Admin";
        }
    }
    // phương thức xác định trạng thái tài khoản.
    public String activeString(){
        if(this.active==1){
            return "Kích hoạt tài khoản";
        }else{
            return "Khóa tài khoản";
        }
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +

                ", active=" + active +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", surname='" + surname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", hash='" + hash + '\'' +
                ", picture='" + picture + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }

    @Override
    public String getTable() {
        return "User";
    }

    @Override
    public String beforeData() {
        return this.toString();
    }

    @Override
    public String afterData() {
        return this.toString();
    }

    public static void main(String[] args) {
        String email= "hiho@gmail.com";
        String pass= "4297f44b13955235245b2497399d7a93";
        User a = new User();
        a.setEmail(email);
        a.setPassword(pass);
        System.out.println(a);
    }
}