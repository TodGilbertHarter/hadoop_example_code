#!/bin/bash
echo "Copy file to /user/guest/test as hdfsrwuser:hdfsrwuser. Should succeed"
echo
java -jar /usr/hdp/current/knox-server/bin/shell.jar knox-copy-file.groovy
