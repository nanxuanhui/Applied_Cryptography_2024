package com.rsa.project.model.entity;

import lombok.Data;

@Data
public class QueryResult {
    private String title;
    private String CAHSubject;
    private String year;
    private int studentEnrolled;
    private String medianGraduateSalary;
    private String medianMidLevelSalary;
    private String SICIndustry;
    private String sic_2022;
    private int sic_2022_num;
    private String sic_2023;
    private int sic_2023_num;
    private String SicDivision;
    private String employer_date;
    private int employerNum;

    // getters and setters omitted for brevity
}
