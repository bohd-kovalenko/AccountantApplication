package com.java.coursework.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class Person {
    private String nameSurname;
    private LocalDate date;
    private Integer leavingDayCount;
    private Integer perDiemDays;
    private Double travelSumValue;
    private Double othersSumValue;
    private Double value;

}
