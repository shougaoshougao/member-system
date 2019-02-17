package com.renaissance.core.handler.sms;

import com.renaissance.core.utils.JsonUtils;
import com.renaissance.core.CoreConst;
import okhttp3.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 大汉通信云的短信实现（由于必须实用模板，不兼容其他短信接口）
 * @author Wilson
 */
public class DahanSmsHandler {

    private static final Logger logger = LoggerFactory.getLogger(DahanSmsHandler.class);

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final String SUBMIT_URL = " https://sms.dahancloud.com/API/sendMessage";

    @Autowired
    private OkHttpClient okHttpClient;

    @Value("${dahan.api.account_sid}")
    private String accountSid;

    @Value("${dahan.api.auth_token}")
    private String authToken;

    @Value("${dahan.api.app_id}")
    private String appId;

    @Value("${dahan.api.template_id}")
    private String templateId;

    public void sendValidationCode(String mobile, String code) {

        // 请求参数
        String[] datas = new String[] {code};
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("to", mobile);
        paramMap.put("appId", appId);
        paramMap.put("templateId", templateId); // 对应的模板为您的验证码为{1}
        paramMap.put("datas", datas);

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // 两个签名
        String sigParam = StringUtils.upperCase(DigestUtils.md5Hex(accountSid + authToken + time));
        String authorization = Base64.encodeBase64String((accountSid + ":" + time).getBytes());

        // 构造请求
        RequestBody body = RequestBody.create(JSON, JsonUtils.toJson(paramMap));
        Request request = new Request.Builder()
                .url(HttpUrl.parse(SUBMIT_URL).newBuilder().addQueryParameter("sig", sigParam).build())
                .post(body)
                .header("Authorization", authorization)
                .build();

        // 发送请求
        try(Response response = okHttpClient.newCall(request).execute()) {

            if(response.isSuccessful()) {
                String responseString = response.body().string();
                Map<String, Object> responseMap = JsonUtils.parse(responseString, Map.class);
                String resultCode = responseMap.get("statusCode").toString();
                if("000000".equals(resultCode)) { // 短信发送成功
                    return;
                } else { // 短信发送失败
                    logger.error("DahanSmsHandler#sendValidationCode(): 大汉通信云短信失败 - 原因: {}", responseString);
                }
            } else { // http返回错误
                logger.error("DahanSmsHandler#sendValidationCode(): http请求大汉通信云网络失败 - 原因: [{}]{}", response.code(), response.message());
            }
        } catch (IOException e) {
            logger.error("DahanSmsHandler#sendValidationCode(): ", e);
        }

        throw CoreConst.smsException();
    }
}
