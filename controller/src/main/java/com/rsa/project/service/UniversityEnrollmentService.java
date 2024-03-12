package com.rsa.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rsa.project.model.entity.UniversityEnrollment;

import java.util.List;

public interface UniversityEnrollmentService extends IService<UniversityEnrollment> {
    List<UniversityEnrollment> fuzzySearchBySubject(String subject);
}
