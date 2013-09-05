/**
 * fileName: EISPlatform/com.eis.platform.rep/EmployeeRep.java
 * copyright: EIS All rights reverved
 * author: zhouyun
 * date: 2013年8月3日 下午6:08:48
 */
package com.eis.core.web.conversion;

import java.text.ParseException;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import com.eis.core.web.conversion.DateConverterBase;

/**
 * @author nick.chow
 * 
 */
public class StringDateConverter extends DateConverterBase implements
		Converter<String, Date> {

	@Override
	public Date convert(String source) {
		if (source == null)
			return null;
		String trim = source.trim();
		if (trim.length() == 0)
			return null;
		try {
			return source.contains(":") ? getDateTimeFormat().parse(trim)
					: getDateFormat().parse(trim);
		} catch (ParseException e) {
			throw new IllegalArgumentException("无效的日期格式：" + trim);
		}
	}

}
