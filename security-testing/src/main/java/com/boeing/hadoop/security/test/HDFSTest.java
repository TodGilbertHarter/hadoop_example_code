/**
 * 
 */
package com.boeing.hadoop.security.test;

import java.net.URISyntaxException;

import org.apache.hadoop.gateway.shell.Hadoop;
import org.apache.hadoop.gateway.shell.hdfs.*;
import org.apache.hadoop.gateway.shell.hdfs.Hdfs;

/**
 * @author oracle
 *
 */
public class HDFSTest extends Test {

	public HDFSTest(Rule rule) {
		super(rule);
	}

	/* (non-Javadoc)
	 * @see com.boeing.hadoop.security.test.Test#runTest()
	 */
	@Override
	public void runTest(String endPoint) throws TestGeneratorException {
		System.out.println("Running Test for "+getRule().toString());
		try {
			Hadoop session = Hadoop.login(endPoint, "guest","guest-password");
			String rPath = getRule().getResource().getName();
			
			org.apache.hadoop.gateway.shell.BasicResponse response = Hdfs.ls(session).dir(rPath).now();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw new TestGeneratorException("Test Failed ",e);
		}
	}

}
