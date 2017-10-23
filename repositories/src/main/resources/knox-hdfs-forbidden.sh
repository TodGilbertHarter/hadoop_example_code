#!/bin/bash
echo "Getting directory listing for /user/guest HDFS directory as 'sam/sam-password'. Operation should fail!"
echo
java -jar /usr/hdp/current/knox-server/bin/shell.jar knox-hdfs-forbidden.groovy

