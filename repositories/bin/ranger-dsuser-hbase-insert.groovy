package org.apache.hadoop.gateway.shell.hbase
import org.apache.hadoop.gateway.shell.hbase.HBase
import org.apache.hadoop.gateway.shell.Hadoop
import static java.util.concurrent.TimeUnit.SECONDS

try{

session = Hadoop.login("https://sandbox.hortonworks.com:8443/gateway/knox_sample", "dsuser", "dsuser")
}
catch(Exception ex)
{
        print "erro is from here"
        println ex
}

try{

//println HBase.session(session).status().now().string
HBase.session(session).table("iemployee").row("6").store()  \
    .column("insurance", "dental", "\$999")  \
    .now()

}
catch (Exception ex)
{

        println "exception here"+ex.getMessage()
}

println "Access denied"

