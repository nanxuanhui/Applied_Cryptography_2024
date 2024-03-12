package com.rsa.project.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("university_enrollment_cah1")
public class UniversityEnrollment {
    private String CAH1_Code;
    private String CAH3_Subject;
    private String Level_of_study;
    private String Academic_Year;
    private Integer Number;

    // getters and setters
}
