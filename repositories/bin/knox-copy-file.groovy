import org.apache.hadoop.gateway.shell.Hadoop
import org.apache.hadoop.gateway.shell.hdfs.Hdfs
import org.apache.hadoop.gateway.shell.job.Job

hadoop = Hadoop.login("https://sandbox.hortonworks.com:8443/gateway/knox_sample","hdfsrwuser","hdfsrwuser")
Hdfs.rm(hadoop).file('/user/guest/wordcount.pig').now().string
Hdfs.put(hadoop).file('wordcount.pig').to('/user/guest/wordcount.pig').now().string
println "Copied file wordcount.pig to /user/guest/wordcount.pig"
