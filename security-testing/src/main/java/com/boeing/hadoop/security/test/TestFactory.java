/**
 * 
 */
package com.boeing.hadoop.security.test;

/**
 * @author tharter
 *
 */
public abstract class TestFactory {

	/**
	 * 
	 */
	public TestFactory() {
	}

	public abstract Test generateTest(Rule r);
}
