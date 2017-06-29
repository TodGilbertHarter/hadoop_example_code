import org.apache.hadoop.gateway.shell.Hadoop
import org.apache.hadoop.gateway.shell.hdfs.Hdfs
import org.apache.hadoop.gateway.shell.job.Job

try {
	hadoop = Hadoop.login("https://sandbox.hortonworks.com:8443/gateway/knox_sample","hdfsrwuser","bad-password")
	Hdfs.rm(hadoop).file('/user/guest/wordcount.pig').now()
} catch (Exception e) {
	println("Failed to log in "+e.getMessage())
}
