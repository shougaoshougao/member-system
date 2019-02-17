package cn.bmaster.member.entity.system;

import com.renaissance.core.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 系统用户
 * @author Wilson
 */
@Entity
@Table(name = "user")
public class User extends BaseEntity<User> {

    /** 最后登录时间 */
    private LocalDateTime lastLoginTime;

    /** 用户名 */
    private String name;

    /** 账号 */
    private String userName;

    /** 密码 */
    private String password;

    /** 手机 */
    private String mobile;

    /** 电话 */
    private String phone;

    /** 邮箱 */
    private String mail;

    /** 备注 */
    private String description;

    public User(){}

    public User(Long id){
        this.id = id;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
