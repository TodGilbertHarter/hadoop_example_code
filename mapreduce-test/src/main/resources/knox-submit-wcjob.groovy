import com.jayway.jsonpath.JsonPath
import org.apache.hadoop.gateway.shell.Hadoop
import org.apache.hadoop.gateway.shell.hdfs.Hdfs
import org.apache.hadoop.gateway.shell.job.Job
 
import static java.util.concurrent.TimeUnit.SECONDS
 
gateway = "https://sandbox.hortonworks.com:8443/gateway/knox_sample"
username = "guest"
password = "guest-foo"
dataFile = "../../test/resources/input/itinerary.txt"

jarFile = "/usr/hdp/2.6.0.3-8/knox/samples/hadoop-examples.jar"
 
hadoop = Hadoop.login( gateway, username, password )
 
println "Delete /user/guest/test " + Hdfs.rm(hadoop).file( "/user/guest/test" ).recursive().now().statusCode
println "Create /user/guest/test " + Hdfs.mkdir(hadoop).dir( "/user/guest/test").now().statusCode
 
putData = Hdfs.put(hadoop).file( dataFile ).to( "/user/guest/test/input/FILE" ).later() {
    println "Put /user/guest/test/input/FILE " + it.statusCode }
putJar = Hdfs.put(hadoop).file( jarFile ).to( "/user/guest/test/hadoop-examples.jar" ).later() {
     println "Put /user/guest/test/hadoop-examples.jar " + it.statusCode }
hadoop.waitFor( putData, putJar )
 
jobId = Job.submitJava(hadoop) \
    .jar( "/user/guest/test/hadoop-examples.jar" ) \
    .app( "wordcount" ) \
    .input( "/user/guest/test/input" ) \
    .output( "/user/guest/test/output" ) \
    .now().jobId
println "Submitted job " + jobId
 
done = false
count = 0
while( !done && count++ < 60 ) {
    sleep( 1000 )
    json = Job.queryStatus(hadoop).jobId(jobId).now().string
// println "STATUS IS: "+json
    done = JsonPath.read( json, "\$.status.jobComplete" )
}
println "Done " + done
 
println "Shutdown " + hadoop.shutdown( 10, SECONDS )
 
// exit
