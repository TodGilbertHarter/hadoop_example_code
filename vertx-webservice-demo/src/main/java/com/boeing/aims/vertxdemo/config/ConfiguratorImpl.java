/**
 * 
 */
package com.boeing.aims.vertxdemo.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author oracle
 *
 */
public class ConfiguratorImpl implements IConfigurator {
	Map<Object,Object> configMap = new HashMap<Object,Object>();
		
	/* (non-Javadoc)
	 * @see com.boeing.aims.vertxdemo.config.IConfigurator#get(java.lang.Object)
	 */
	@Override
	public Object get(Object configKey) {
		return configMap.get(configKey);
	}

	/* (non-Javadoc)
	 * @see com.boeing.aims.vertxdemo.config.IConfigurator#set(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void set(Object configKey, Object configValue) {
		configMap.put(configKey, configValue);
	}

}
