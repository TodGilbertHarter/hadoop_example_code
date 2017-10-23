import org.apache.hadoop.gateway.shell.Hadoop
import org.apache.hadoop.gateway.shell.hdfs.Hdfs
import org.apache.hadoop.gateway.shell.job.Job

try {
	hadoop = Hadoop.login("https://sandbox.hortonworks.com:8443/gateway/knox_sample","sam","sam-password")
	println Hdfs.put(hadoop).file('wordcount.pig').to('/user/guest/wordcount.pig').now().string
} catch (org.apache.hadoop.gateway.shell.HadoopException e) {
	println "Access denied: "+e.getMessage()
}
