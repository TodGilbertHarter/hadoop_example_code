package org.apache.hadoop.gateway.shell.job



import com.jayway.jsonpath.JsonPath

import org.apache.hadoop.gateway.shell.Hadoop

import org.apache.hadoop.gateway.shell.hdfs.Hdfs as hdfs

import org.apache.hadoop.gateway.shell.job.Job as job





session = Hadoop.login("https://sandbox.hortonworks.com:8443/gateway/knox_sample", "admuser", "admuser")






jobId = job.submitHive(session).file("script.hive").arg("-v").statusDir("output").now().jobId

println "Job=" + jobId



println job.queryQueue(hadoop).now().string



done = false;

while(!done) {

  json = job.queryStatus(hadoop).jobId(jobId).now().string

  done = JsonPath.read( json, "\$.status.jobComplete" )

  sleep( 1000 )

  print "."

}

println "done"
