/**
 * 
 */
package com.boeing.hadoop.security.test;

/**
 * @author oracle
 *
 */
public class HadoopTestFactory extends TestFactory {

	/* (non-Javadoc)
	 * @see com.boeing.hadoop.security.test.TestFactory#generateTest(com.boeing.hadoop.security.test.Rule)
	 */
	@Override
	public Test generateTest(Rule r) {
		return new HDFSTest(r);
	}

}
