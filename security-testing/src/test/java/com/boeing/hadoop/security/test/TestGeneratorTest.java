/**
 * 
 */
package com.boeing.hadoop.security.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

/**
 * @author tharter
 *
 */
public class TestGeneratorTest {

	TestGenerator uut;
	
	@Before
	public void setup() {
		TestFactory tf = new HadoopTestFactory();
		uut = new TestGenerator(tf);
	}
	
	/**
	 * Test method for {@link com.boeing.hadoop.security.test.TestGenerator#parseTestDescription(java.io.File)}.
	 */
	@Test
	public void testParseTestDescription() {
		File file = new File("src/test/resources/description.xml");
		try {
			Document result = uut.parseTestDescription(file);
			assertNotNull(result);
		} catch (TestGeneratorException e) {
			e.printStackTrace();
			fail("Failed to parse test description "+e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testRunTests() {
		try {
			uut.runTests("src/test/resources/description.xml","https://localhost:8443/gateway/sandbox");
		} catch (TestGeneratorException e) {
			e.printStackTrace();
			fail("Cannot run tests "+e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testGenerateTestMatrix() {
		File file = new File("src/test/resources/description.xml");
		try {
			Document result = uut.parseTestDescription(file);
			TestMatrix tm = uut.generateTestMatrix(result);
			long numSubjects = tm.subjects().count();
			assertEquals("There should be 3 subjects",3,numSubjects);
			long numResources = tm.resources().count();
			assertEquals("There should be one resource",1,numResources);
			long numActions = tm.actions().count();
			assertEquals("There should be three actions",3,numActions);

		} catch (TestGeneratorException e) {
			e.printStackTrace();
			fail("Failed to parse test description "+e.getLocalizedMessage());
		}
	}
}
