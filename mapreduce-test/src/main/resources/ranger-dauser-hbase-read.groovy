
package org.apache.hadoop.gateway.shell.hbase
import org.apache.hadoop.gateway.shell.hbase.HBase
import org.apache.hadoop.gateway.shell.Hadoop
import static java.util.concurrent.TimeUnit.SECONDS


try{

session = Hadoop.login("https://sandbox.hortonworks.com:8443/gateway/knox_sample", "dauser", "dauser")
}
catch(Exception ex)
{
        print "erro is from here"
        println ex
}

try{

println HBase.session(session).table("atlas_titan").row().query()  \

    .now().string

}
catch (Exception ex)
{

        println "exception here"+ex.getMessage()
}

println "querried atlas_titan table"

