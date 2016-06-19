package es.udc.tfg.trainticketsapp.web.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.StringTokenizer;


public class PaypalManager {
	
	private static String API_USERNAME = "mytrainexpress-facilitator_api1.gmail.com";
	private static String API_PASSWORD = "3MU4Z6J9JRCMSG6A";
	private static String API_SIGNATURE = "AFcWxV21C7fd0v3bYYYRCpSSRl31A0Ubcwscq7pjHoilYJeQWGNPcABL";
	private static String RETURN_URL="http://localhost:8080/traintickets-app/purchase/confirmpurchase";
	private static String CANCEL_URL="http://localhost:8080/traintickets-app/";
	private static String API_URL="https://api-3t.sandbox.paypal.com/nvp";
	public static String startPurchase(float price) {
		String data = 
				"METHOD=SetExpressCheckout" +
				"&USER="+API_USERNAME +
				"&PWD="+API_PASSWORD +
				"&SIGNATURE="+API_SIGNATURE+
				"&VERSION=93" +
				"&PAYMENTREQUEST_0_PAYMENTACTION=SALE" +
				"&PAYMENTREQUEST_0_AMT="+price + 
				"&PAYMENTREQUEST_0_CURRENCYCODE=EUR" + 
				"&RETURNURL=" + RETURN_URL + 
				"&CANCELURL=" + CANCEL_URL +
				"";
		HashMap<String, String> results = doPost(data);
			String token = (String)results.get("TOKEN");
			return "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token="+token;


	}
	public static String purchaseDetails(String token)
	{
		String data = 
			"METHOD=GetExpressCheckoutDetails" +
			"&USER="+API_USERNAME +
			"&PWD="+API_PASSWORD +
			"&VERSION=93" +			
			"&SIGNATURE="+API_SIGNATURE+
			"&TOKEN=" + encodeValue(token) +
			"";
		HashMap<String, String> results = doPost(data);		
		String payerId =(String) results.get("PAYERID");
		return payerId;
	}
	public static void finishPurchase(String token, String payerId, float price)
		{
			try {
				String data = 
					"METHOD=DoExpressCheckoutPayment" +
					"&USER="+API_USERNAME +
					"&PWD="+API_PASSWORD +
					"&SIGNATURE="+API_SIGNATURE+
					"&VERSION=93" +
					"&TOKEN=" + encodeValue(token) +
					"&PAYERID=" + encodeValue(payerId) +
					"&PAYMENTREQUEST_0_PAYMENTACTION=SALE" +
					"&PAYMENTREQUEST_0_AMT="+price + 
					"&PAYMENTREQUEST_0_CURRENCYCODE=EUR" + 
					"";
				HashMap<String, String> results = doPost(data);
			} catch (RuntimeException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
	
	
	protected static HashMap<String, String> doPost(String data)
	{
	
		String response = "";
		try {
			URL postURL = new URL(API_URL);
			HttpURLConnection conn = (HttpURLConnection)postURL.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(7000);
			conn.setRequestMethod("POST");
			
			DataOutputStream output = new DataOutputStream(conn.getOutputStream());
			output.writeBytes(data);
			output.flush();
			output.close();

			// Read input from the input stream.
			int responseCode = conn.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Error " + responseCode + ": " + conn.getResponseMessage());
			}
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while(((line = reader.readLine()) !=null)) {
				response = response + line;
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		if(response.length() <= 0) {
			throw new RuntimeException("Received empty response");
		}
		HashMap<String, String> results = parsePaypalResponse(response);
		
		
		return results;
	}	
	
	private static HashMap<String, String> parsePaypalResponse (String data)
	{
		HashMap<String, String> results = new HashMap<String, String>();
		StringTokenizer tokenizer = new StringTokenizer(data, "&");
		while (tokenizer.hasMoreTokens()) {
			StringTokenizer tokenizer2 = new StringTokenizer(tokenizer.nextToken(), "=");
			if (tokenizer2.countTokens() != 2) {
				continue;
			}
			String key = decodeValue(tokenizer2.nextToken());
			String value = decodeValue(tokenizer2.nextToken());
			results.put(key.toUpperCase(), value);
		}
		return results;
	}
	protected static String decodeValue(String value)
	{
		try {
			return URLDecoder.decode(value, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	protected static String encodeValue(String value)
	{
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
