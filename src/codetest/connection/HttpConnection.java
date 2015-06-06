/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codetest.connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 *
 * @author Yvonne Elsie
 */
public class HttpConnection {
    public String URL_CONNECTION;
    public HttpConnection(String URL_CONNECTION){
        this.URL_CONNECTION = URL_CONNECTION;
    }
    
    public static String excutePost(String targetURL, int timeout)
    {
        System.out.println("The requested URL:" + targetURL);
		StringBuilder sb = new StringBuilder();
		URLConnection urlConnection = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(targetURL);
			urlConnection = url.openConnection();
			if (urlConnection != null)
				urlConnection.setReadTimeout(timeout * 1000);
			if (urlConnection != null && urlConnection.getInputStream() != null) {
				in = new InputStreamReader(urlConnection.getInputStream(),
						Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
		in.close();
		} catch (Exception e) {
			throw new RuntimeException("Exception while fetching Infrastructure Resources:"+ targetURL, e);
		} 
 
		return sb.toString();
	}
    
}
