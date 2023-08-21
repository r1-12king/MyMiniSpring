package com.minis.jdbc.core;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 结果映射类
 * @author: luguilin
 * @date: 2023-08-21 22:11
 **/
public class RowMapperResultSetExtractor<T> implements ResultSetExtractor<List<T>>{

    private final RowMapper<T> rowMapper;

    public RowMapperResultSetExtractor(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    @Override
    public List<T> extractData(ResultSet rs) throws Exception {
        List<T> result = new ArrayList<>();
        int rowNum = 0;
        while(rs.next()){
            result.add(this.rowMapper.mapRow(rs, rowNum++));
        }
        return result;
    }
}
