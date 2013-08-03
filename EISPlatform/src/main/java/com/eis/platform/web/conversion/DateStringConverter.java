/**
 * fileName: EISPlatform/com.eis.platform.rep/EmployeeRep.java
 * copyright: EIS All rights reverved
 * author: zhouyun
 * date: 2013年8月3日 下午6:08:48
 */
package com.eis.platform.web.conversion;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * @author nick.chow
 *
 */
public class DateStringConverter extends DateConverterBase implements Converter<Date, String>{

	@Override
	public String convert(Date source) {
		if (source == null) {
			return "";
		}
		return getDateFormat().format(source);
	}

}
