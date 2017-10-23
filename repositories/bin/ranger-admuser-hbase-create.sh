#!/bin/bash
echo "Copy file to /user/guest/test as admuser:admuser. Should succeed"
echo
java -jar /usr/hdp/current/knox-server/bin/shell.jar ranger-admuser-hbase-create.groovy
