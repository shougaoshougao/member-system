package cn.bmaster.member.web.entity.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.renaissance.core.utils.BeanUtils;
import cn.bmaster.member.entity.system.User;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserResponse {

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "服务商id")
    private Long hrMerchantId;

    @ApiModelProperty(value = "最后登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "账号")
    private String userName;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String mail;

    @ApiModelProperty(value = "备注")
    private String description;

    public UserResponse () {

    }

    public UserResponse(User user) {
        BeanUtils.copyProperties(this, user);
    }

    public Long getHrMerchantId() {
        return hrMerchantId;
    }

    public void setHrMerchantId(Long hrMerchantId) {
        this.hrMerchantId = hrMerchantId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
