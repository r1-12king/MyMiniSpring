package com.minis.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @description: 行映射
 * @author: luguilin
 * @date: 2023-08-21 22:08
 */
public interface RowMapper<T> {
    T mapRow(ResultSet rs, int rowNum) throws SQLException;
}
