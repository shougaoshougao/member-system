package com.renaissance.core.handler.sms;

import com.renaissance.core.CoreConst;
import com.renaissance.core.utils.JsonUtils;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Map;

/**
 * 云片网的短信实现
 * @author Wilson
 */
public class SmsHandlerYunpianImpl implements SmsHandler {

    private static final Logger logger = LoggerFactory.getLogger(SmsHandlerYunpianImpl.class);

    private static final String SUBMIT_URL = "http://sms.yunpian.com/v2/sms/single_send.json";

    @Autowired
    private OkHttpClient okHttpClient;

    @Value("${yunpian.api.key}")
    private String apiKey;

    public void sendMessage(String mobile, String text) {

        // 构造请求
        RequestBody formBody = new FormBody.Builder()
                .add("apikey", apiKey)
                .add("mobile", mobile)
                .add("text", text)
                .build();
        Request request = new Request.Builder()
                .url(SUBMIT_URL)
                .post(formBody)
                .build();
        // 发送请求
        try(Response response = okHttpClient.newCall(request).execute()) {
            if(response.isSuccessful()) {
                String responseString = response.body().string();
                Map<String, Object> responseMap = JsonUtils.parse(responseString, Map.class);
                String resultCode = responseMap.get("code").toString();
                if("0".equals(resultCode)) { // 短信发送成功
                    return;
                } else { // 短信发送失败
                    logger.error("SmsHandlerYunpianImpl#sendMessage(): 云片短信失败 - 原因: {}", responseString);
                }
            } else { // http返回错误
                logger.error("SmsHandlerYunpianImpl#sendMessage(): http请求云片网络失败 - 原因: [{}]{}", response.code(), response.message());
            }
        } catch (IOException e) {
            logger.error("SmsHandlerYunpianImpl#sendMessage(): ", e);
        }
        throw CoreConst.smsException();

    }
}
