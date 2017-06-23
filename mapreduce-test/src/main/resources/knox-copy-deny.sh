#!/bin/bash
echo "Attempt to copy file to /user/guest as 'sam/sam-password' -- should fail"
echo
java -jar /usr/hdp/current/knox-server/bin/shell.jar knox-copy-deny.groovy
