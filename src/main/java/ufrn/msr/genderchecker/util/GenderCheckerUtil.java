package ufrn.msr.genderchecker.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import ufrn.msr.genderchecker.exceptions.GenderCheckerException;

public class GenderCheckerUtil {
	
private static String KEY = "YOUR_GENDER_API_KEY";
	
	public static String checkGenderFromName(String name) throws GenderCheckerException{		
		
		try {
			URL url = new URL("https://gender-api.com/get?country=BR&key=" + KEY + "&name="+name);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			if (conn.getResponseCode() != 200) 
				throw new GenderCheckerException("Error: " + conn.getResponseCode());			

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			Gson gson = new Gson();
			JsonObject json = gson.fromJson(reader, JsonObject.class);			
			String gender = json.get("gender").getAsString();
						
			conn.disconnect();

			return gender;
			
		} catch (Exception e) {
			throw new GenderCheckerException(e.getMessage());
		}		
	}

}
