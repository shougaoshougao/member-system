package com.renaissance.core.jpa;

import com.renaissance.core.utils.JsonUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.LinkedHashMap;
import java.util.Map;

@Converter
public class JsonToMapConverter implements AttributeConverter<Map<String, Object>, String> {

    @Override
    public Map<String, Object> convertToEntityAttribute(String attribute) {
        if (attribute == null) {
           return null;
        }
        return JsonUtils.parse(attribute, LinkedHashMap.class);
    }

    @Override
    public String convertToDatabaseColumn(Map<String, Object> dbData) {
        return JsonUtils.toJson(dbData);
    }
}