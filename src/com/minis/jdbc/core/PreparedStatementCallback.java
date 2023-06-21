package com.minis.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * @description: StatementCallback，这就是需要回调的方法
 * 函数式接口
 * @author: luguilin
 * @date: 2023-06-20 11:30
 **/
public interface PreparedStatementCallback {
    /**
     *
     * @param stmt
     * @return
     * @throws SQLException
     */
    Object doInPreparedStatement(PreparedStatement stmt) throws SQLException;
}