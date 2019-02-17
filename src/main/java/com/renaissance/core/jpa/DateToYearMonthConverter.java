package com.renaissance.core.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

@Converter
public class DateToYearMonthConverter implements AttributeConverter<YearMonth, java.sql.Date> {
 
    @Override
    public java.sql.Date convertToDatabaseColumn(YearMonth attribute) {
        if(attribute == null) {
            return null;
        }
        return java.sql.Date.valueOf(attribute.atDay(1));
    }
 
    @Override
    public YearMonth convertToEntityAttribute(java.sql.Date dbData) {
        if(dbData == null) {
            return null;
        }
        LocalDate localDate = Instant.ofEpochMilli(dbData.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return YearMonth.from(localDate);
    }
}