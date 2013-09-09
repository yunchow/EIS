/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 9, 2013
 */
package com.eis.oa.interfaces.assembler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eis.oa.application.dto.LeaveFormDTO;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 9, 2013
 */
public abstract class LeaveMapAssembler {

	private LeaveMapAssembler () {
		
	}
	
	public static Map<String, Object> asMap(LeaveFormDTO requestParam, List<LeaveFormDTO> resultDtoList) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put("page", requestParam.getPage());
		result.put("rows", resultDtoList);
		return result;
	}
}
