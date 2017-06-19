/**
 * 
 */
package com.boeing.aims.vertxdemo.config;

/**
 * @author tharter
 *
 */
public interface IConfigurator {

	public abstract Object get(Object configKey);
	public abstract void set(Object configKey, Object configValue);
}
