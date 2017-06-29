#!/bin/bash
echo "Copy file to /user/guest/test as dauser:dauser. Should succeed"
echo
java -jar /usr/hdp/current/knox-server/bin/shell.jar ranger-dauser-hbase-read.groovy
