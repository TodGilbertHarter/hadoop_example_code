import org.apache.hadoop.gateway.shell.Hadoop
import org.apache.hadoop.gateway.shell.hdfs.Hdfs
import org.apache.hadoop.gateway.shell.job.Job


hadoop = Hadoop.login("https://sandbox.hortonworks.com:8443/gateway/knox_sample","guest","guest-foo")
println Hdfs.rm(hadoop).file('/user/guest/wordcount.pig').now().string
println Hdfs.put(hadoop).file('wordcount.pig').to('/user/guest/wordcount.pig').now().string
