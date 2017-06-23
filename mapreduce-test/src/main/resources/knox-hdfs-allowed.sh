#!/bin/bash
echo "Getting directory listing for /user/guest HDFS directory"
echo
java -jar /usr/hdp/current/knox-server/bin/shell.jar knox-hdfs-allowed.groovy

