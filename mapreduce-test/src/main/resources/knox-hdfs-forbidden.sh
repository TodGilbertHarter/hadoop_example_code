#!/bin/bash
echo "Trying to get HDFS directory listing for /user/guest as user 'sam/sam-password' -- This will be denied!"
echo
curl -i -k -s -u sam:sam-password 'https://localhost:8443/gateway/knox_sample/webhdfs/v1/user/guest?op=LISTSTATUS'

