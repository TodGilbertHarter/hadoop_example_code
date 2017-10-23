#!/bin/bash
echo "trying to login as hdfsrwuser/bad-password - this will fail!"
echo
java -jar /usr/hdp/current/knox-server/bin/shell.jar knox-bad-password.groovy

