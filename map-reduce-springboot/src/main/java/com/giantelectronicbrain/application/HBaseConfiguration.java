package com.giantelectronicbrain.application;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class HBaseConfiguration {

	@Value("${hbase.zk.host}")
	private String zkQuoram;

	@Value("${hbase.zk.port}")
	private String zkPort;

	@Bean
	public org.apache.hadoop.conf.Configuration configuration() {
		org.apache.hadoop.conf.Configuration create = org.apache.hadoop.hbase.HBaseConfiguration.create();
		create.set("zk-quorum", zkQuoram);
		create.set("zk-port", zkPort);
		return create;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
		dataSource.setUsername("sa");
		dataSource.setPassword("sa");

		return dataSource;
	}

	@Bean(name = "hbaseTemplate")
	public HbaseTemplate hbaseTemplate() {
		return new HbaseTemplate(configuration());
	}
}
