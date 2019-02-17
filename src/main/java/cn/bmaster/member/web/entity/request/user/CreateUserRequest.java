package cn.bmaster.member.web.entity.request.user;

import com.renaissance.core.utils.BeanUtils;
import cn.bmaster.member.entity.system.User;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class CreateUserRequest {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String name;

    @ApiModelProperty(value = "账号")
    @NotBlank(message = "账号不能为空")
    private String userName;

    @ApiModelProperty(value = "手机")
    @NotBlank(message = "手机不能为空")
    private String mobile;

//    @ApiModelProperty(value = "电话")
//    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String mail;

//    @ApiModelProperty(value = "备注")
//    private String description;

    @ApiModelProperty(value = "权限")
    private List<String> roleCodes = new ArrayList<>();

//    @ApiModelProperty(value = "公司id")
//    private Long companyId;

    @ApiModelProperty(value = "人事服务商id")
    private Long hrMerchantId;

    public User toUser(){
        User user = BeanUtils.copyPropertiesToNewBean(this, User.class);
        user.setPassword("e10adc3949ba59abbe56e057f20f883e"); // 初始化密码123456
        return user;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    public Long getHrMerchantId() {
        return hrMerchantId;
    }

    public void setHrMerchantId(Long hrMerchantId) {
        this.hrMerchantId = hrMerchantId;
    }
}
