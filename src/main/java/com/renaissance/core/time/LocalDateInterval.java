package com.renaissance.core.time;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

/**
 * localDate 时间区间
 * @author Wilson
 */
public class LocalDateInterval {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public LocalDateInterval() {}

    public LocalDateInterval(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * 是否包含今天
     * @return
     */
    @JsonIgnore
    public boolean isTodayBouned() {
        LocalDate today = LocalDate.now();
        return startDate != null && endDate != null && !today.isBefore(startDate) && !today.isAfter(endDate);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
