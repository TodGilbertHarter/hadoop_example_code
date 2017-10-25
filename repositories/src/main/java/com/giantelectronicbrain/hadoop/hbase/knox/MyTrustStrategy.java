/**
 * 
 */
package com.giantelectronicbrain.hadoop.hbase.knox;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.conn.ssl.TrustStrategy;

/**
 * @author kf203e
 *
 * TrustStrategy which trusts ANYTHING.
 */
public class MyTrustStrategy implements TrustStrategy {

	/* (non-Javadoc)
	 * @see org.apache.http.conn.ssl.TrustStrategy#isTrusted(java.security.cert.X509Certificate[], java.lang.String)
	 */
	@Override
	public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		return true;
	}

}
