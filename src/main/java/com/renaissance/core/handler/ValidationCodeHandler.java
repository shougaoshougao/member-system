package com.renaissance.core.handler;

import com.renaissance.core.CoreConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * @author Wilson
 */
public class ValidationCodeHandler {

    @Autowired
    private CacheHandler cacheHandler;

    public boolean validateCode(String mobile, String code) {
        if(cacheHandler.exist(CoreConst.CACHE_VALIDATION_CODE, mobile)) {
            String realCode = cacheHandler.readCache(CoreConst.CACHE_VALIDATION_CODE, mobile, String.class);
            return StringUtils.equals(code, realCode);
        }
        return false;
    }

    public String generateCode(String mobile) {
        String code = StringUtils.leftPad(String.valueOf(new Random().nextInt(9999)), 4, '0');
        cacheHandler.writeCache(CoreConst.CACHE_VALIDATION_CODE, mobile, code);
        return code;
    }

}
