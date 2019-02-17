package com.renaissance.core;

import com.renaissance.core.exception.BusinessException;

import java.time.format.DateTimeFormatter;

public class CoreConst {

    public final static String CACHE_VALIDATION_CODE = "CACHE_VALIDATION_CODE";

    // 大陆身份证
    public final static String RE_ID_CARD = "^(([1][1-5])|([2][1-3])|([3][1-7])|([4][1-6])|([5][0-4])|([6][1-5])|([7][1])|([8][1-2]))\\d{4}(([1][9]\\d{2})|([2]\\d{3}))(([0][1-9])|([1][0-2]))(([0][1-9])|([1-2][0-9])|([3][0-1]))\\d{3}[0-9xX]$";
    // 港澳通行证
    public final static String RE_GANGAO_ID = "^([A-Z]\\d{6,10}(\\(\\w{1}\\))?)$";
    // 台湾通行证
    public final static String RE_TAIWAN_ID = "^\\d{8}|^[a-zA-Z0-9]{10}|^\\d{18}$";
    // 护照
    public final static String RE_PASSWORD = "^([a-zA-z]|[0-9]){5,17}$";
    // 军官证
    public final static String RE_OFFICER_ID = "^[\\u4E00-\\u9FA5](字第)([0-9a-zA-Z]{4,8})(号?)$";
    // 户口本
    public final static String RE_RECIDENCE_BOOKLET = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";

    public final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public final static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    public final static DateTimeFormatter yearMonthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");

    public static BusinessException idNotFoundException() {
        return BusinessException.codeAndMessageOf("101", "该id找不到对应的记录");
    }

    public static BusinessException smsException() {
        return BusinessException.codeAndMessageOf("102", "短信发送失败");
    }

    public static BusinessException fileNotMatchException() {
        return BusinessException.codeAndMessageOf("103", "文件类型不匹配");
    }

    public static BusinessException excelColumnsNotValidException() {
        return BusinessException.codeAndMessageOf("104", "表格列数不匹配");
    }
}
