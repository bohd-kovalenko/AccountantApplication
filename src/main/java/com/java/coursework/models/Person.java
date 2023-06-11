package com.java.coursework.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Person {
    private int index;
    private String nameSurname;
    private LocalDate date;
    private BigDecimal leavingDayCount;
    private BigDecimal perDiemDays;
    private BigDecimal travelSumValue;
    private BigDecimal othersSumValue;
    private BigDecimal costPerDayField;
    private BigDecimal value;
    private BigDecimal perDiemValue;
    private BigDecimal leavingSumValue;
}
