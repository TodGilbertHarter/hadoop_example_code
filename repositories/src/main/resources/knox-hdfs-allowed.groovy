import org.apache.hadoop.gateway.shell.Hadoop
import org.apache.hadoop.gateway.shell.hdfs.Hdfs
import org.apache.hadoop.gateway.shell.job.Job


hadoop = Hadoop.login("https://sandbox.hortonworks.com:8443/gateway/knox_sample","hdfsrwuser","hdfsrwuser")
println Hdfs.ls(hadoop).dir('/user/guest').now().string
