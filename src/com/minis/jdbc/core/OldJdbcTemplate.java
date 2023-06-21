package com.minis.jdbc.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public abstract class OldJdbcTemplate {
	public OldJdbcTemplate() {
	}
	
	public Object query(String sql) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Object rtnObj = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "271828lgl");
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			rtnObj = doInStatement(rs);
		}
		catch (Exception e) {
				e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (Exception e) {
				
			}
		}
		
		return rtnObj;

	}
	protected abstract  Object doInStatement(ResultSet rs);
}
