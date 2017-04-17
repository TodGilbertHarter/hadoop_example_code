/**
 * 
 */
package com.giantelectronicbrain.hadoop.pig;

import java.io.IOException;

import org.apache.pig.pigunit.PigTest;
import org.apache.pig.tools.parameters.ParseException;
import org.junit.Test;

/**
 * Simple example PigUnit test for wordcount.pig example script. This simply
 * runs the script in a local mode, not against a hadoop cluster, it should be
 * runnable from anywhere.
 * 
 * @author tharter
 *
 */
public class WordCountTest {

	@Test
	public void test() throws IOException, ParseException {
		String[] args = {
				"inputfile=src/test/resources/input/itinerary.txt",
				"outputfile=build"
		};
		
		String[] expected = {
				"(4,Go)","(1,in)","(5,to)","(1,1st)","(1,8th)","(4,Feb)","(1,11th)","(1,14th)","(1,16th)","(1,20th)",
				"(1,26th)","(1,Come)","(1,Emei)","(1,back)","(3,March)","(1,Xi'an)","(1,Arrive)","(1,Depart)",
				"(1,Chengdu)","(1,Mountain)","(1,Chongqing)","(2,Guangzhou)","(0,)"
		};
		
		PigTest test = new PigTest("src/main/resources/wordcount.pig",args);
		test.assertOutput("D",expected);
	}

}
