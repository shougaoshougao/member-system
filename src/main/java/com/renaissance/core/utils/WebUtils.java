package com.renaissance.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Wilson
 */
public class WebUtils {

    private static final Logger logger = LoggerFactory.getLogger(WebUtils.class);

    public static String toQueryString(Map<String, String> params) {
        return toQueryString(params, false);
    }

    public static String toQueryString(Map<String, String> params, boolean needsURLEncode) {
        List<String> queries = new ArrayList<String>();
        for(Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if (needsURLEncode) {
                try {
                    value = URLEncoder.encode(value, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    logger.error("WebUtils#toQueryString - URL encodes [{}] encounters UnsupportedEncodingException.", value);
                }
            }
            queries.add(key + "=" + value);
        }
        return org.apache.commons.lang3.StringUtils.join(queries, "&");
    }

}
