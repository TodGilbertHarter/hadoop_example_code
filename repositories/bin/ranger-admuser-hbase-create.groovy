
package org.apache.hadoop.gateway.shell.hbase
import org.apache.hadoop.gateway.shell.hbase.HBase
import org.apache.hadoop.gateway.shell.Hadoop
import static java.util.concurrent.TimeUnit.SECONDS

tableName="test_table"

try{

session = Hadoop.login("https://sandbox.hortonworks.com:8443/gateway/knox_sample", "admuser", "admuser")
}
catch(Exception ex)
{
        print "error is from here"
        println ex
}

try{
/*
	
#HBase.session(session).table("newdata").create()  \
#	.attribute("tb_attr1","value1") \
#	.attribute("tb_attr2","value2")
#      .family("wheels")  \
#        .attribute("upstream", "aaaaaa")  \
#        .attribute("downstream", "bbbbbb")  \
#`    .endFamilyDef()  \
#      .now()
*/
HBase.session(session).table(tableName).create()  \
    .attribute("tb_attr1", "value1")  \
    .attribute("tb_attr2", "value2")  \
    .family("family1")  \
        .attribute("fm_attr1", "value3")  \
        .attribute("fm_attr2", "value4")  \
    .endFamilyDef()  \
    .family("family2")  \
    .family("family3")  \
    .endFamilyDef()  \
    .attribute("tb_attr3", "value5")  \
    .now()
}
catch (Exception ex)
{

        println "exception here"+ex.getMessage()
}

println "created a table into HBase"

