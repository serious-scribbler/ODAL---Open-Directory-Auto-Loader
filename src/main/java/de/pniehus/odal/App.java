package de.pniehus.odal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import de.pniehus.odal.GUI.OdalGui;
import de.pniehus.odal.utils.Filter;
import de.pniehus.odal.utils.filters.BlacklistFilter;
import de.pniehus.odal.utils.filters.FileTypeFilter;
import de.pniehus.odal.utils.filters.KeywordFilter;
import de.pniehus.odal.utils.filters.RegexFilter;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws IOException {
		unsignedSSLSetup();
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new RegexFilter());
		filters.add(new FileTypeFilter());
		filters.add(new KeywordFilter());
		filters.add(new BlacklistFilter());
		@SuppressWarnings("unused")
		OdalGui ogui = new OdalGui(args, filters);
	}

	public static void unsignedSSLSetup() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };
		try{
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch(Exception e){
			// TODO Handle
		}
	}
}
