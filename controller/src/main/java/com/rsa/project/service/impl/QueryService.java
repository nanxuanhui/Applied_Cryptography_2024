package com.rsa.project.service.impl;

import com.rsa.project.mapper.QueryMapper;
import com.rsa.project.model.entity.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {
    private final QueryMapper queryMapper;

    @Autowired
    public QueryService(QueryMapper queryMapper) {
        this.queryMapper = queryMapper;
    }

    public List<QueryResult> getQueryResult(int number, String label) {
        return queryMapper.executeQuery(number, label);
    }
}
