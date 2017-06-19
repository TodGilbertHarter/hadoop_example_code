#!/bin/bash
echo "trying to login as guest/baddpass - this will fail!"
echo
curl -i -k -s -u guest:baddpass 'https://localhost:8443/gateway/knox_sample/webhdfs/v1?op=LISTSTATUS'

