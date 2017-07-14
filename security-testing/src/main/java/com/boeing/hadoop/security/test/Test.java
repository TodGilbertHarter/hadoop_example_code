/**
 * 
 */
package com.boeing.hadoop.security.test;

/**
 * @author tharter
 *
 */
public abstract class Test {
	private final Rule rule;
	
	/**
	 * 
	 */
	public Test(Rule rule) {
		this.rule = rule;
	}

	protected Rule getRule() {
		return rule;
	}
	
	public abstract void runTest(String endPoint) throws TestGeneratorException;
}
