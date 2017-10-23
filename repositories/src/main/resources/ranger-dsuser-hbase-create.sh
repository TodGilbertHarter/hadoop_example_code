#!/bin/bash
echo "Copy file to /user/guest/test as dsuser:dsuser. Should succeed"
echo
java -jar /usr/hdp/current/knox-server/bin/shell.jar ranger-dsuser-hbase-create.groovy
