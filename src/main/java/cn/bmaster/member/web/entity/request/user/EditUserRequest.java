package cn.bmaster.member.web.entity.request.user;

import com.renaissance.core.utils.BeanUtils;
import cn.bmaster.member.entity.system.User;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wilson
 */
public class EditUserRequest {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    @NotNull
    private String name;

    @ApiModelProperty(value = "账号")
    @NotNull
    private String userName;

    @ApiModelProperty(value = "手机")
    @NotNull
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

    public User editUser(User user){
        BeanUtils.copyProperties(user, this);
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getHrMerchantId() {
        return hrMerchantId;
    }

    public void setHrMerchantId(Long hrMerchantId) {
        this.hrMerchantId = hrMerchantId;
    }
}
