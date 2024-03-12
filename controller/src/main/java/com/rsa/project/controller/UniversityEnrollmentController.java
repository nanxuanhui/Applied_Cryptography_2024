package com.rsa.project.controller;

import com.rsa.project.model.entity.UniversityEnrollment;
import com.rsa.project.service.UniversityEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/enrollment")
public class UniversityEnrollmentController {

    @Autowired
    private UniversityEnrollmentService service;

    @GetMapping("/search")
    public List<UniversityEnrollment> fuzzySearchBySubject(@RequestParam String subject) {
        System.out.println("--------");
        System.out.println(subject.trim());
        System.out.println("--------");
        return service.fuzzySearchBySubject(subject.trim());
    }
}
