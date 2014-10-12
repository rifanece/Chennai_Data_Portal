package example.chennaidata.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class ApiDataAccess {

	public void postjson(String Json, String url) {

		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is
		// established.
		// The default value is zero, that means the timeout is not
		// used.
		int timeoutConnection = 10000;
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for
		// data.
		int timeoutSocket = 10000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		// 1. create HttpClient
		HttpClient httpclient = new DefaultHttpClient();

		// 2. make POST request to the given URL
		System.out.println("URL is " + url);
		HttpPost httpPost = new HttpPost(url);
		String json = "{\"Message\" : {\"Data\": \"Nothing Added\" } }";
		HttpResponse httpResponse = null;
		StringEntity se = null;

		try {
			se = new StringEntity(json);
			System.out.println("Sent json is " + json);
			// 6. set httpPost Entity
			httpPost.setEntity(se);

			// 7. Set some headers to inform server about the
			// type of the content
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type",
					"application/json; charset=utf-8");

			// 8. Execute POST request to the given URL

			httpResponse = httpclient.execute(httpPost);
			httpResponse = httpclient.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 9. receive response as inputStream
		// inputStream = httpResponse.getEntity().getContent();
	}

	public String postMethod(String url) {

		String value = null;
		HttpClient httpClient = new DefaultHttpClient();

		// In a POST request, we don't pass the values in the URL.
		// Therefore we use only the web page URL as the parameter of the
		// HttpPost argument
		HttpPost httpPost = new HttpPost(url);

		// Because we are not passing values over the URL, we should have a
		// mechanism to pass the values that can be
		// uniquely separate by the other end.
		// To achieve that we use BasicNameValuePair
		// Things we need to pass with the POST request
		BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair(
				"paramUsername", "");
		BasicNameValuePair passwordBasicNameValuePAir = new BasicNameValuePair(
				"paramPassword", "");

		// We add the content that we want to pass with the POST request to as
		// name-value pairs
		// Now we put those sending details to an ArrayList with type safe of
		// NameValuePair
		List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
		nameValuePairList.add(usernameBasicNameValuePair);
		nameValuePairList.add(passwordBasicNameValuePAir);

		try {
			// UrlEncodedFormEntity is an entity composed of a list of
			// url-encoded pairs.
			// This is typically useful while sending an HTTP POST request.
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(
					nameValuePairList);

			// setEntity() hands the entity (here it is urlEncodedFormEntity) to
			// the request.
			httpPost.setEntity(urlEncodedFormEntity);

			try {
				// HttpResponse is an interface just like HttpPost.
				// Therefore we can't initialize them
				HttpResponse httpResponse = httpClient.execute(httpPost);

				// According to the JAVA API, InputStream constructor do
				// nothing.
				// So we can't initialize InputStream although it is not an
				// interface
				InputStream inputStream = httpResponse.getEntity().getContent();

				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);

				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);

				StringBuilder stringBuilder = new StringBuilder();

				String bufferedStrChunk = null;

				while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
					stringBuilder.append(bufferedStrChunk);
				}

				value = stringBuilder.toString();

			} catch (ClientProtocolException cpe) {
				System.out.println("First Exception caz of HttpResponese :"
						+ cpe);
				cpe.printStackTrace();
			} catch (IOException ioe) {
				System.out.println("Second Exception caz of HttpResponse :"
						+ ioe);
				ioe.printStackTrace();
			}

		} catch (UnsupportedEncodingException uee) {
			System.out
					.println("An Exception given because of UrlEncodedFormEntity argument :"
							+ uee);
			uee.printStackTrace();
		}

		return value;
	}

	// HTTP GET request
	public String sendGet(String url) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		// con.setRequestProperty("User-Agent", "");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());
		return response.toString();

	}
}
