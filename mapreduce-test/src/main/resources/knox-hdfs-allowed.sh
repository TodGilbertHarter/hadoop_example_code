#!/bin/bash
echo "Getting directory listing for /user/guest HDFS directory"
echo
curl -i -k -s -u guest:guest-foo 'https://localhost:8443/gateway/knox_sample/webhdfs/v1/user/guest?op=LISTSTATUS'

