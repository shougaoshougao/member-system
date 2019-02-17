package com.renaissance.core.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Wilson
 */
public class EnumUtils {

    private static final Logger logger = LoggerFactory.getLogger(EnumUtils.class);

    /**
     * 扫描某个报名下面的所有枚举，返回map<枚举类名, 枚举值数组>
     *
     * @param packageName
     * @return
     */
    public static Map<String, Enum[]> scan(String packageName) {
        Map<String, Enum[]> enums = new HashMap<>();
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends Enum>> allClasses = reflections.getSubTypesOf(Enum.class);
        for(Class clazz : allClasses) {
            String key = clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1);
            try {
                enums.put(key, (Enum[])clazz.getMethod("values").invoke(clazz));
            } catch (Exception e) {
                logger.error("EnumUtils#scan()", e);
            }
        }
        return enums;
    }

    /**
     * 描述枚举值的所有字段，返回map<字段名, 字段值>
     *
     * @param enumeration
     * @return
     */
    public static Map<String, Object> describe(Enum<?> enumeration) {
        try {
             Map<String, Object> properties = PropertyUtils.describe(enumeration);

             // remove class property
            if(properties.containsKey("class")) {
                properties.remove("class");
            }
            if(properties.containsKey("declaringClass")) {
                properties.remove("declaringClass");
            }

            // add name and ordinal
            if(!properties.containsKey("name")) {
                properties.put("name", enumeration.name());
            }
            if(!properties.containsKey("ordinal")) {
                properties.put("ordinal", enumeration.ordinal());
            }
            return properties;
        } catch (Exception e) {
            logger.error("EnumUtils#describe()", e);
        }
        return null;
    }

    /**
     * 通过枚举class和descrition获取对应的枚举值
     *
     * @param enumClass
     * @param description
     * @param <E>
     * @return
     */
    public static <E extends Enum<E>> E descriptionOf(Class<E> enumClass, String description) {
        try {
            E[] enums = (E[])enumClass.getMethod("values").invoke(enumClass);
            for(E theEnum : enums) {
                if(PropertyUtils.getProperty(theEnum, "description").equals(description)) {
                    return theEnum;
                }
            }
        } catch (Exception e) {
            logger.error("EnumUtils#nameOf()", e);
        }
        return null;
    }

}
