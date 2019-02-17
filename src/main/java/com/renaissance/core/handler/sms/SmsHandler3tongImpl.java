package com.renaissance.core.handler.sms;

import com.renaissance.core.utils.JsonUtils;
import com.renaissance.core.CoreConst;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 大汉三通的短信实现
 * @author Wilson
 */
public class SmsHandler3tongImpl implements SmsHandler {

    private static final Logger logger = LoggerFactory.getLogger(SmsHandler3tongImpl.class);

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final String SUBMIT_URL = "http://wt.3tong.net/json/sms/Submit";

    @Autowired
    private OkHttpClient okHttpClient;

    @Value("${3tong.api.account}")
    private String account;

    @Value("${3tong.api.password}")
    private String password;

    public void sendMessage(String mobile, String text) {

        // 请求参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("account", account);
        paramMap.put("password", password);
        paramMap.put("phones", mobile);
        paramMap.put("content", text);
        paramMap.put("sign", "【秦汉胡同】");

        // 构造请求
        RequestBody body = RequestBody.create(JSON, JsonUtils.toJson(paramMap));
        Request request = new Request.Builder()
                .url(SUBMIT_URL)
                .post(body)
                .build();

        // 发送请求
        try(Response response = okHttpClient.newCall(request).execute()) {
            if(response.isSuccessful()) {
                String responseString = response.body().string();
                Map<String, Object> responseMap = JsonUtils.parse(responseString, Map.class);
                String resultCode = responseMap.get("result").toString();
                if("0".equals(resultCode)) { // 短信发送成功
                    return;
                } else { // 短信发送失败
                    logger.error("SmsHandler3tongImpl#sendMessage(): 大汉三通发短信失败 - 原因: {}", responseString);
                }
            } else { // http返回错误
                logger.error("SmsHandler3tongImpl#sendMessage(): http请求大汉三通网络失败 - 原因: [{}]{}", response.code(), response.message());
            }
        } catch (IOException e) {
            logger.error("SmsHandler3tongImpl#sendMessage(): ", e);
        }
        throw CoreConst.smsException();
    }
}
