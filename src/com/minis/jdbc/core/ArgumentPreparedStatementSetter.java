package com.minis.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @description: 参数设置
 * @author: luguilin
 * @date: 2023-08-21 21:55
 */
public class ArgumentPreparedStatementSetter {

    /**
     * 参数数组
     */
    private final Object[] args;

    public ArgumentPreparedStatementSetter(Object[] args) {
        this.args = args;
    }

    /**
     * 设置参数
     * @param pstmt
     * @throws SQLException
     */
    public void setValues(PreparedStatement pstmt) throws SQLException {
        if (this.args != null) {
            for (int i = 0; i < this.args.length; i++) {
                Object arg = this.args[i];
                doSetValue(pstmt, i + 1, arg);
            }
        }
    }

    /**
     * 设置参数
     *
     * @param pstmt
     * @param position
     * @param argValue
     * @throws SQLException
     */
    protected void doSetValue(PreparedStatement pstmt, int position, Object argValue) throws SQLException {
        if (argValue instanceof String) {
            pstmt.setString(position, (String)argValue);
        } else if (argValue instanceof Integer) {
            pstmt.setInt(position, (int)argValue);
        } else if (argValue instanceof java.util.Date) {
            pstmt.setDate(position, new java.sql.Date(((java.util.Date)argValue).getTime()));
        }
    }

}
