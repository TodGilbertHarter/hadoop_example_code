/**
 * 
 */
package com.giantelectronicbrain.hadoop.springrepo;

import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.giantelectronicbrain.hadoop.RepositoryException;

/**
 * Reducer which does the summarizing word count step and puts the output into a JPA repository.
 * 
 * @author tharter
 *
 */
public class JpaWordReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	private static final Log LOG = LogFactory.getLog(JpaWordReducer.class);
	private static AbstractApplicationContext context;
	private WordRepository wordRepository;
	
	/**
	 * <b>Note:</b> We must manually instantiate our own Spring context and inject beans by hand in
	 * this Reducer. This is also the case for Mappers, both are instantiated by Hadoop framework
	 * mechanisms that are outside of the Spring world. Thus you <em>cannot use Spring annotations</em> etc
	 * within MapReduce code!
	 */
	public JpaWordReducer() {
		super();
		context = new ClassPathXmlApplicationContext("/META-INF/spring/application-context.xml",Driver.class);
		wordRepository = context.getBean(WordRepository.class);
	}
	
	/**
	 * Save the given key/value into HBase using our WordRepository.
	 * 
	 * @param word
	 * @param sum
	 * @throws RepositoryException 
	 */
	private void jpaOutputter(String word, int sum) throws RepositoryException {
		LOG.trace("Outputting word to Hive repo "+word+"="+sum);
		wordRepository.save(word,sum);

	}
	
	/**
	 * Perform the basic reduce step logic. Called by hadoop.
	 */
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
		int sum = 0;
		Iterator<IntWritable> vItr = values.iterator();
		while(vItr.hasNext()) {
			sum += vItr.next().get();
		}
		try {
			jpaOutputter(key.toString(),sum);
		} catch (RepositoryException e) {
			LOG.error("failed to write to hive repository",e);
		}		
	}
	

}
