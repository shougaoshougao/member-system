package com.renaissance.core.handler;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Wilson
 */
public class QiniuHandler {

    @Value("${qiniu.access.key}")
    private String accessKey;

    @Value("${qiniu.secret.key}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    /** token默认有效时间为7200秒，即2小时 */
    @Value("${qiniu.token.expires:7200}")
    private Long tokenExpires;

    public String getUploadToken() {
        String key = null;
        String prefix = null;
        return getUploadToken(key, prefix);
    }

    public String getUploadToken(String prefix) {
        String key = null; // key取null，用policy定义文件名为哈希值
        return getUploadToken(key, prefix);
    }

    public String getUploadToken(String key, String prefix) {
        StringMap policy = new StringMap();
        policy.put("saveKey", (prefix == null ? "":prefix)+"$(etag)$(ext)");
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucket, key, tokenExpires, policy);
    }

}
