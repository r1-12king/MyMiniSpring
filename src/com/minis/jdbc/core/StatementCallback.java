package com.minis.jdbc.core;

import java.sql.SQLException;
import java.sql.Statement;


/**
 * @description: StatementCallback，这就是需要回调的方法
 *                函数式接口
 * @author: luguilin
 * @date: 2023-06-20 10:44
 **/
public interface StatementCallback {
    Object doInStatement(Statement stmt) throws SQLException;
}