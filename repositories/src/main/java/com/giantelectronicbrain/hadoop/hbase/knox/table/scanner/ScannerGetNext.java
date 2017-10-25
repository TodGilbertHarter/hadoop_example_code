/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.giantelectronicbrain.hadoop.hbase.knox.table.scanner;

import java.util.concurrent.Callable;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import com.giantelectronicbrain.hadoop.hbase.knox.AbstractRequest;
import com.giantelectronicbrain.hadoop.hbase.knox.BasicResponse;
import com.giantelectronicbrain.hadoop.hbase.knox.HBase;
import com.giantelectronicbrain.hadoop.hbase.knox.Hadoop;

public class ScannerGetNext {

  public static class Request extends AbstractRequest<Response> {

    private String scannerId;
    private String tableName;

    public Request( Hadoop session, String scannerId, String tableName ) {
      super( session );
      this.scannerId = scannerId;
      this.tableName = tableName;
    }

    protected Callable<Response> callable() {
      return new Callable<Response>() {
        @Override
        public Response call() throws Exception {
          URIBuilder uri = uri( HBase.SERVICE_PATH, "/", tableName, "/scanner/", scannerId );
          HttpGet get = new HttpGet( uri.build() );
          get.setHeader( "Accept", "application/json" );
          return new Response( execute( get ) );
        }
      };
    }
  }

  public static class Response extends BasicResponse {

    Response( HttpResponse response ) {
      super( response );
    }
  }
}