/**
 * 
 */
package com.boeing.aims.vertxdemo;

import com.boeing.aims.vertxdemo.config.ConfiguratorImpl;
import com.boeing.aims.vertxdemo.config.IConfigurator;

/**
 * Static configuration factory to construct the configuration for the VertexDemo. This will instantiate
 * and populate a Configurator object. Everything else can work with that. Any component which needs to
 * depend on items which could be configured should pull them from this store.
 * 
 * @author tharter
 *
 */
public class ConfiguratorFactory {
	public static String HADOOP_SERVICE_URL = "com.boeing.aims.vertxdemo.serviceverticle.SERVICEURL";
	public static String GUI_SERVICE_URL = "com.boeing.aims.vertxdemo.gui.SERVICEURL";
	public static String GUI_SERVICE_ROOT = "com.boeing.aims.vertxdemo.gui.WEBROOT";
	
	private static volatile IConfigurator configuratorInstance;
	
	/**
	 * Get the system global configuration. If it doesn't exist, create it. This is
	 * thread safe.
	 * 
	 * @return Configurator containing system's global configuration.
	 */
	public static synchronized IConfigurator getConfiguration() {
		if(configuratorInstance == null) {
			IConfigurator conf = createConfiguration();
			configuratorInstance = initializeConfiguration(conf);
		}
		return configuratorInstance;
	}
	
	/**
	 * Create an empty Configurator.
	 * 
	 * @return empty configurator
	 */
	private static IConfigurator createConfiguration() {
		ConfiguratorImpl config = new ConfiguratorImpl();
		return config;
	}
	
	/**
	 * Populate a configurator with our system configuration.
	 * 
	 * @param config empty configurator
	 * @return populated configurator
	 */
	private static IConfigurator initializeConfiguration(IConfigurator config) {
		config.set(HADOOP_SERVICE_URL, "/hadoop/");
		config.set(GUI_SERVICE_URL, "/gui/");
		config.set(GUI_SERVICE_ROOT, "src/main/webapp");
		//TODO: add actual configuration of stuff here.
		return config;
	}
}
