import org.apache.hadoop.gateway.shell.Hadoop
import org.apache.hadoop.gateway.shell.hdfs.Hdfs
import org.apache.hadoop.gateway.shell.job.Job

try {
	hadoop = Hadoop.login("https://sandbox.hortonworks.com:8443/gateway/knox_sample","sam","sam-password")
	println Hdfs.ls(hadoop).dir('/user/guest').now().string
} catch (Exception e) {
	println("Access denied "+e.getMessage())
}
