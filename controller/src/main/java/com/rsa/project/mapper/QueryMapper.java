package com.rsa.project.mapper;

import com.rsa.project.model.entity.QueryResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QueryMapper {
    @Select("SELECT" +
        "    csm.CAH3_Label AS title," +
        "    csm.CAH1_Label AS CAHSubject," +
        "    uec3.Academic_Year AS year," +
        "    uec3.Number AS studentEnrolled," +
        "    gp.Median AS medianGraduateSalary," +
        "    gp.Annual_percentage_change_median AS medianMidLevelSalary," +
        "    v1.SIC_Description AS SICIndustry," +
        "    v1.Vacancy_time AS sic_2022," +
        "    v1.Vacancy_number * 1000 AS sic_2022_num," +
        "    v2.Vacancy_time AS sic_2023," +
        "    v2.Vacancy_number * 1000 AS sic_2023_num," +
        "    jb.SIC_Description AS SicDivision," +
        "    jb.Job_Numbers_Time AS employer_date," +
        "    jb.Job_Numbers * 1000 AS employerNum" +
        " FROM" +
        "    university_enrollment_cah3 AS uec3" +
        "        JOIN cah_sic_mapping AS csm ON uec3.CAH3_Code = csm.CAH3_Code" +
        "        JOIN gross_pay AS gp ON gp.SIC_Code = csm.SIC_Section" +
        "        JOIN vacancy AS v1 ON v1.SIC_Section_Code = csm.SIC_Section AND v1.Vacancy_time = 'Oct-Dec 2022'" +
        "        JOIN vacancy AS v2 ON v2.SIC_Section_Code = csm.SIC_Section AND v2.Vacancy_time = 'Jan-Mar 2023'" +
        "        JOIN job_number AS jb ON jb.SIC_Division_Code = csm.SIC_Division AND jb.Job_Numbers_Time = 'Dec 22'" +
        " WHERE" +
        "        uec3.Number = #{number} and csm.CAH1_Label = #{label}")
    List<QueryResult> executeQuery(@Param("number") int number, @Param("label") String label);
}
