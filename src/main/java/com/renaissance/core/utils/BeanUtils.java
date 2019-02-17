package com.renaissance.core.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wilson
 */
public class BeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    public static String getProperty(final Object bean, final String name) {
        try {
            return org.apache.commons.beanutils.BeanUtils.getProperty(bean, name);
        } catch (Exception e) {
            logger.error("BeanUtils#getProperty()", e);
        }
        return null;
    }

    /**
     * copyProperties by org.apache.commons.beanutils.PropertyUtils.copyProperties without checked exception
     *
     * @param dest
     * @param source
     */
    public static void copyProperties(Object dest, final Object source) {
        try {
            PropertyUtils.copyProperties(dest, source);
        } catch (Exception e) {
            logger.error("BeanUtils#copyProperties()", e);
        }
    }

    /**
     * create a new bean by newBeanClass, copy all properties to the new bean
     *
     * @param source
     * @param newBeanClass
     * @param <T>
     * @return
     */
    public static <T> T copyPropertiesToNewBean(final Object source, Class<T> newBeanClass) {
        try {
            T newBean = newBeanClass.getConstructor().newInstance();
            copyProperties(newBean, source);
            return newBean;
        } catch (Exception e) {
            logger.error("BeanUtils#copyPropertiesToNewBean()", e);
        }
        return null;
    }

    /**
     * get names of  all properties of a given class
     *
     * @param clazz
     * @return
     */
    public static List<String> getAllPropertyNames(Class clazz) {
        List<String> fieldNameLIst = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            fieldNameLIst.add(field.getName());
        }
        return fieldNameLIst;
    }


}
