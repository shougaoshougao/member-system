package com.renaissance.core.handler.sms;

/**
 * @author Wilson
 */
public interface SmsHandler {

    void sendMessage(String mobile, String text);

}
