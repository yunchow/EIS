/**
 * fileName: EISPlatform/com.eis.platform.rep/EmployeeRep.java
 * copyright: EIS All rights reverved
 * author: zhouyun
 * date: 2013年8月3日 下午6:08:48
 */
package com.eis.core.web.conversion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author nick.chow
 * 
 */
public class DateConverterBase {
	private String datePattern = "yyyy-MM-dd";
	private String timePattern = "HH:mm:ss";
	private DateFormat dateFormat = new SimpleDateFormat(datePattern);
	private DateFormat dateTimeFormat = new SimpleDateFormat(datePattern + " " + timePattern);

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * @return the dateTimeFormat
	 */
	public DateFormat getDateTimeFormat() {
		return dateTimeFormat;
	}

	/**
	 * @param dateTimeFormat
	 *            the dateTimeFormat to set
	 */
	public void setDateTimeFormat(DateFormat dateTimeFormat) {
		this.dateTimeFormat = dateTimeFormat;
	}

}
