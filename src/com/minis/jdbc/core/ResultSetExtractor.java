package com.minis.jdbc.core;

import java.sql.ResultSet;

/**
 * @description: 将ResultSet数据集映射为结果集
 * @author: luguilin
 * @date: 2023-08-21 22:09
 */
public interface ResultSetExtractor<T> {
    T extractData(ResultSet rs) throws Exception;
}
