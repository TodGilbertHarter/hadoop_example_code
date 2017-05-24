/**
 * 
 */
package com.giantelectronicbrain.hadoop.mahout;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.evaluation.ClusterEvaluator;
import org.apache.mahout.math.Vector;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Evaluate some sequence files generated by our test script and try to find
 * clusters. This is an experiment to try to understand the basic HDFS based
 * Mahout on-disk data structures.
 * 
 * @author tharter
 *
 */
public class EvaluateCluster {
	private static final Log LOG = LogFactory.getLog(EvaluateCluster.class);

	private static ClusterEvaluator clusterEvaluator;
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/mahout-context.xml",EvaluateCluster.class);
		LOG.info("Cluster Evaluation Running");
		context.registerShutdownHook();
		Configuration config = context.getBean(Configuration.class);
		String ecpath = config.get("ec.path");
		LOG.info("Evaluating data from "+ecpath);
		Path path = new Path(ecpath);

		clusterEvaluator = new ClusterEvaluator(config,path);
		Double interClusterDensity = clusterEvaluator.interClusterDensity();
		LOG.info("Inter-cluster density is "+interClusterDensity);
		Map<Integer,Vector> vmap = clusterEvaluator.interClusterDistances();
		LOG.info("VMAP "+vmap);
//this part needs some work apparently
//		Double intraClusterDensity = clusterEvaluator.intraClusterDensity();
//		LOG.info("Intra-cluster average density is "+intraClusterDensity);
	}
}
