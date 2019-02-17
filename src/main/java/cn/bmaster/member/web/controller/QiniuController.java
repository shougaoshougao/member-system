package cn.bmaster.member.web.controller;

import com.renaissance.core.Result;
import com.renaissance.core.handler.QiniuHandler;
import com.renaissance.core.web.interceptor.HttpAuthentication;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "七牛服务 1.0")
@RestController
@RequestMapping("/qiniu")
public class QiniuController {

    @Autowired
    private QiniuHandler qiniuHandler;

    @ApiOperation(value = "获取七牛token 1.0")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "文件名, 不传则默认实用哈希值作为文件名", dataType = "String"),
            @ApiImplicitParam(name = "prefix", value = "文件名前缀", dataType = "String")
    })
    @HttpAuthentication(ignore = true)
    @GetMapping("/upload-token")
    public Result<String> getUploadToken(@RequestParam(name = "key", required = false) String key, @RequestParam(name = "prefix", required = false) String prefix) {

        Result result = new Result();

        String uploadToken = qiniuHandler.getUploadToken(key, prefix);

        return result.success().data(uploadToken);
    }

}
