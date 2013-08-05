package common;

	import java.io.*;
	import java.net.*;
	import java.security.*;
	import java.security.cert.*;
	import java.util.*;
	import javax.net.ssl.*;


	public class HttpSsl
	{


	    String url="";
	    myX509TrustManager xtm = new myX509TrustManager();
	    myHostnameVerifier hnv = new myHostnameVerifier();

	    public HttpSsl(String HttpsUrl) {
	    this.url = HttpsUrl;
	       SSLContext sslContext = null;
	      try {
	          sslContext = SSLContext.getInstance("TLS");
	        X509TrustManager[] xtmArray = new X509TrustManager[] { xtm };
	        sslContext.init( null,
	                          xtmArray,
	                          new java.security.SecureRandom() );
	      } catch( GeneralSecurityException gse ) {
	      }
	      if( sslContext != null ) {
	         HttpsURLConnection.setDefaultSSLSocketFactory(
	                     sslContext.getSocketFactory() );
	      }
	      HttpsURLConnection.setDefaultHostnameVerifier( hnv );
	    }

	 

	    public String getResult() {
	    String res = "";
	      try {

	            URLConnection urlCon = (new URL(url)).openConnection();

	            BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null)
	            {
	                //System.out.println(line);
	                res += line;
	            }

	           //  增加自己的代码
	        } catch( MalformedURLException mue ) {
	            mue.printStackTrace();
	        } catch( IOException ioe ) {
	            ioe.printStackTrace();
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        return res;


	  }
	}

	 

	 


	class myX509TrustManager implements X509TrustManager{
	  public myX509TrustManager(){}
	  public void checkClientTrusted(X509Certificate[] chain,  String authType) {}
	     public void checkServerTrusted(X509Certificate[] chain,String authType) {
	      //System.out.println("cert: " + chain[0].toString() + ", authType: " + authType);
	     }
	     public X509Certificate[] getAcceptedIssuers() {
	      return null;
	     }
	}


	class myHostnameVerifier implements HostnameVerifier{
	 public myHostnameVerifier(){}
	  public boolean verify(String hostname,SSLSession session) {
	   //System.out.println("hostname: " + hostname);
	   return true;
	  }
	}

