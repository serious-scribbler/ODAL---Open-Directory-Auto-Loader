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
		untrustedSSLSetup();
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new RegexFilter());
		filters.add(new FileTypeFilter());
		filters.add(new KeywordFilter());
		filters.add(new BlacklistFilter());
		@SuppressWarnings("unused")
		OdalGui ogui = new OdalGui(args, filters);
	}

	/**
	 * This method initializes a Trustmanager that accepts self signed ssl certificates
	 * 
	 * This code of this method has been taken from
	 * @see <a href="https://stackoverflow.com/a/4453908">this Stackoverflow post</a>
	 * and is licensed under the MIT License
	 * 
	 * Copyright (c) 2010 nogudnik
	 * 
	 * Permission is hereby granted, free of charge, to any person obtaining a
	 * copy of this software and associated documentation files (the
	 * "Software"), to deal in the Software without restriction, including
	 * without limitation the rights to use, copy, modify, merge, publish,
	 * distribute, sublicense, and/or sell copies of the Software, and to permit
	 * persons to whom the Software is furnished to do so, subject to the
	 * following conditions:
	 * 
	 * The above copyright notice and this permission notice shall be included
	 * in all copies or substantial portions of the Software.
	 * 
	 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
	 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
	 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
	 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
	 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
	 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
	 * USE OR OTHER DEALINGS IN THE SOFTWARE.
	 * 
	 */
	public static void untrustedSSLSetup() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			// TODO Handle
		}
	}
}
