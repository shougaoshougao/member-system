package com.renaissance.core.jpa;

import com.renaissance.core.utils.JsonUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter
public class JsonToListConverter implements AttributeConverter<List, String> {

    @Override
    public List convertToEntityAttribute(String attribute) {
        if (attribute == null) {
           return null;
        }
        return JsonUtils.parse(attribute, List.class);
    }

    @Override
    public String convertToDatabaseColumn(List dbData) {
        return JsonUtils.toJson(dbData);
    }
}