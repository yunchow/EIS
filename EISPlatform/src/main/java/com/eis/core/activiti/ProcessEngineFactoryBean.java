/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 24, 2013
 */
package com.eis.core.activiti;

import java.io.InputStream;

import org.activiti.engine.impl.util.ReflectUtil;
import org.activiti.spring.SpringProcessEngineConfiguration;

/**
 * <p>
 * Please comment here
 * 
 * @author nick.chow
 * @date: Sep 24, 2013
 */
public class ProcessEngineFactoryBean extends SpringProcessEngineConfiguration {
	
	private String mybatisMappingFile = DEFAULT_MYBATIS_MAPPING_FILE;

	protected InputStream getMyBatisXmlConfigurationSteam() {
		return ReflectUtil.getResourceAsStream(getMybatisMappingFile());
	}

	public String getMybatisMappingFile() {
		return mybatisMappingFile;
	}

	public void setMybatisMappingFile(String mybatisMappingFile) {
		this.mybatisMappingFile = mybatisMappingFile;
	}
	
}
