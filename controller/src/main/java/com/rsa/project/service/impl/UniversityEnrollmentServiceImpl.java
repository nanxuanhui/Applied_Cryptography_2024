package com.rsa.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rsa.project.mapper.UniversityEnrollmentMapper;
import com.rsa.project.model.entity.UniversityEnrollment;
import com.rsa.project.service.UniversityEnrollmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityEnrollmentServiceImpl extends ServiceImpl<UniversityEnrollmentMapper, UniversityEnrollment> implements UniversityEnrollmentService {
    @Override
    public List<UniversityEnrollment> fuzzySearchBySubject(String subject) {
        QueryWrapper<UniversityEnrollment> wrapper = new QueryWrapper<>();
        wrapper.like("CAH3_Subject", subject);
        return list(wrapper);
    }
}
