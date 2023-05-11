package com.java.coursework.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Person {
    private String nameSurname;
    private LocalDate date;
    private Integer leavingDayCount;
    private Integer perDiemDays;
    private Double travelSumValue;
    private Double othersSumValue;
    private Double costPerDayField;
    private Double value;
    private Double perDiemValue;

}
