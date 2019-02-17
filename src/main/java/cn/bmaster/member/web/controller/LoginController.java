package cn.bmaster.member.web.controller;

import com.renaissance.core.Result;
import com.renaissance.core.handler.ValidationCodeHandler;
import com.renaissance.core.handler.sms.DahanSmsHandler;
import com.renaissance.core.web.interceptor.HttpAuthentication;
import com.renaissance.core.web.resolver.Secure;
import cn.bmaster.member.Constants;
import cn.bmaster.member.entity.system.Token;
import cn.bmaster.member.entity.system.User;
import cn.bmaster.member.service.TokenService;
import cn.bmaster.member.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Api(tags = "登录验证接口 1.0")
@RestController
public class LoginController {

    @Value("${project.token.expire:120}")
    private int expireDuration;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private DahanSmsHandler dahanSmsHandler;

    @Autowired
    private ValidationCodeHandler validationCodeHandler;

    @ApiOperation(value = "密码登录 1.0")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", required = true)
    })
    @HttpAuthentication(ignore = true)
    @PostMapping("/login")
    public Result<Token> login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {

        Result result = new Result();
        // 1.验证登录
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return result.fail().message("用户名或密码不能为空");
        }
        User user = userService.findByUserName(username);
        if (user == null){
            return result.fail().message("用户名不存在，请重新输入");
        } else if(user.getPassword() == null || !user.getPassword().equalsIgnoreCase(password)) {
            return result.fail().message("账户或密码错误");
        }

        // 2、获取token
        Token token = tokenService.load(user.getId(), Constants.MEMBER_SYSTEM);
        if (token != null){
            // 如果token存在则重新刷新token
            token = token.refresh(expireDuration);
        } else {
            // 创建新的token
            token = tokenService.create(new Token(user.getId(), Constants.MEMBER_SYSTEM, expireDuration));
        }

        // 3、更新用户的最后登录时间
        user.setLastLoginTime(LocalDateTime.now());
        userService.editUser(user);
        return result.success()
                .data(token);
    }

    @ApiOperation(value = "验证码登录 1.0")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "电话号码", dataType = "String", required = true),
            @ApiImplicitParam(name = "code", value = "验证码", dataType = "String", required = true)
    })
    @HttpAuthentication(ignore = true)
    @PostMapping("/login-mobile")
    public Result<Token> loginMobile(@RequestParam(name = "mobile") String mobile, @RequestParam(name = "code") String code) {

        Result<Token> result = new Result<>();
        User user = userService.findByMobile(mobile);
        if(user == null){
            return result.fail().message("您输入的电话不是本系统用户，请确认");
        }
        while(validationCodeHandler.validateCode(mobile, code)) {

            // 2、获取token
            Token token = tokenService.load(user.getId(), Constants.MEMBER_SYSTEM);
            if (token != null){
                // 如果token存在则重新刷新token
                token = token.refresh(expireDuration);
            } else {
                // 创建新的token
                token = tokenService.create(new Token(user.getId(), Constants.MEMBER_SYSTEM, expireDuration));
            }

            // 3、更新用户的最后登录时间
            user.setLastLoginTime(LocalDateTime.now());
            userService.editUser(user);
            return result.success()
                    .data(token);
        }
        return result.fail().message("验证码错误");
    }

    @ApiOperation(value = "获取验证码 1.0")
    @ApiImplicitParams({@ApiImplicitParam(name = "mobile", value = "电话号码", dataType = "String", required = true)})
    @HttpAuthentication(ignore = true)
    @GetMapping("/get-code")
    public Result getCode(@RequestParam(name = "mobile") String mobile) {

        Result result = new Result();
        User user = userService.findByMobile(mobile);
        if(user == null){
            return result.fail().message("您输入的电话不是本系统用户，请确认");
        }
        String code = validationCodeHandler.generateCode(mobile); // 生成4位短信验证码
        dahanSmsHandler.sendValidationCode(mobile, code);

        return result.success();
    }

    @ApiOperation(value = "修改密码 1.0")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "旧密码", dataType = "String", required = true),
            @ApiImplicitParam(name = "newPassword", value = "新密码", dataType = "String", required = true)
    })
    @PostMapping("/change-password")
    public Result changePassword(@Secure Long userId, @RequestParam(name = "oldPassword") String oldPassword, @RequestParam(name = "newPassword") String newPassword) {

        Result result = new Result();
        User user = userService.load(userId);
        if(!user.getPassword().equalsIgnoreCase(oldPassword)) {
            return result.fail().message("原始密码输入错误");
        }
        user.setPassword(newPassword);
        userService.editUser(user.recordEdit(userId));
        return result.success().message("密码修改成功");
    }

    @ApiOperation(value = "验证码校验 1.0")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "电话号码", dataType = "String", required = true),
            @ApiImplicitParam(name = "code", value = "验证码", dataType = "String", required = true)
    })
    @HttpAuthentication(ignore = true)
    @PostMapping("/check-mobile-code")
    public Result<Token> checkMobileAndCode(@RequestParam(name = "mobile") String mobile, @RequestParam(name = "code") String code) {

        Result<Token> result = new Result<>();
        User user = userService.findByMobile(mobile);
        if(user == null){
            return result.fail().message("您输入的电话不是本系统用户，请确认");
        }
        while(validationCodeHandler.validateCode(mobile, code)) {

            // 2、获取token
            Token token = tokenService.load(user.getId(), Constants.MEMBER_SYSTEM);
            if (token != null){
                // 如果token存在则重新刷新token
                token = token.refresh(expireDuration);
            } else {
                // 创建新的token
                token = tokenService.create(new Token(user.getId(), Constants.MEMBER_SYSTEM, expireDuration));
            }
            return result.success()
                    .data(token);
        }
        return result.fail().message("验证码错误");
    }

    @ApiOperation(value = "重置密码 1.0")
    @ApiImplicitParams({@ApiImplicitParam(name = "newPassword", value = "新密码", dataType = "String", required = true)})
    @PostMapping("/reset-password")
    public Result resetPassword(@Secure Long userId, @RequestParam(name = "newPassword") String newPassword) {

        Result result = new Result();
        User user = userService.load(userId);
        user.setPassword(newPassword);
        userService.editUser(user.recordEdit(userId));
        return result.success().message("密码修改成功");
    }
}
