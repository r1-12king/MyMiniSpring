package com.minis.http.converter;


/**
 * @description:
 * @author: luguilin
 * @date: 2023-06-14 16:35
 **/
public interface ObjectMapper {
	/**
	 *
	 * @param dateFormat
	 */
	void setDateFormat(String dateFormat);

	/**
	 *
	 * @param decimalFormat
	 */
	void setDecimalFormat(String decimalFormat);

	/**
	 *
	 * @param obj
	 * @return
	 */
	String writeValuesAsString(Object obj) throws IllegalAccessException;
}
