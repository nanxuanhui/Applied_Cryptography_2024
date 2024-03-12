package com.rsa.project.controller;

import com.rsa.project.model.entity.QueryResult;
import com.rsa.project.service.impl.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/detail")
public class QueryController {
    private final QueryService queryService;

    @Autowired
    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/data")
    public ResponseEntity<List<QueryResult>> getQueryResult(@RequestParam("number") int number, @RequestParam("label") String label) {
        List<QueryResult> result = queryService.getQueryResult(number, label.trim());
        return ResponseEntity.ok(result);
    }
}
