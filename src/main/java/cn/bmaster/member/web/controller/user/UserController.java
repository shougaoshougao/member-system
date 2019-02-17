package cn.bmaster.member.web.controller.user;

import cn.bmaster.member.web.entity.request.user.CreateUserRequest;
import cn.bmaster.member.web.entity.request.user.EditUserRequest;
import cn.bmaster.member.web.entity.response.user.UserResponse;
import com.renaissance.core.Result;
import com.renaissance.core.web.resolver.Secure;
import com.renaissance.core.web.response.IdNameResponse;
import cn.bmaster.member.entity.system.User;
import cn.bmaster.member.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "用户管理相关接口 1.0")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "创建用户 2.1")
    @PostMapping("/create-user")
    public Result createUser(@Secure Long operatorId, @RequestBody @Valid CreateUserRequest createUserRequest) {
        Result result = new Result();
        User oldUser = userService.findTopByUserName(createUserRequest.getUserName());
        if(oldUser != null){
            return result.fail().message("用户名已存在！");
        }
        oldUser = userService.findByMobile(createUserRequest.getMobile());
        if(oldUser != null){
            return result.fail().message("手机号已存在！");
        }
        userService.createUser(createUserRequest, operatorId);
        return result.success();
    }

}
