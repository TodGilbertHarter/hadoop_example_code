package com.giantelectronicbrain.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.config.annotation.SpringHadoopConfigurerAdapter;
import org.springframework.data.hadoop.config.annotation.builders.HadoopConfigConfigurer;

@Configuration
public class HadoopConfiguration extends SpringHadoopConfigurerAdapter {

	

	@Value("${hd.fs}")
	private String  fileSystem;
	
	@Value("${hd.rm}")
	private String  resourceManager;
	
	@Value("${hd.jh}")
	private String  jobHistory;
		
	
	
	@Override
	public void configure(HadoopConfigConfigurer config) throws Exception {
		config.fileSystemUri(fileSystem).resourceManagerAddress(resourceManager)
				.jobHistoryAddress(jobHistory);
	}
	
	 
	
}
