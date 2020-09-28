package com.frslabs.atlas.api.samples;

import com.frslabs.atlas.api.utils.AppConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PKarthik on 7/10/2017.
 */
public class DeleteFaceExample {

    private final String USER_AGENT = "Mozilla/5.0";
    SimpleDateFormat sdfClientSecretFormat = new SimpleDateFormat("ddMMyyyyHHmmss");

    public  static void main(String args[]) {

        try {
            new DeleteFaceExample().sendGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendGet() throws Exception {

        String url = AppConfig.API_BASE_URL + "/face/delete";

        String timeStamp = sdfClientSecretFormat.format(new Date());

        String params = "face_id=00b85b02_477a300c-5290-4d25-ad4b-e523327d174b"; //-- enrolled face id
        URL obj = new URL(url + "?" + params);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Authorization", "key_id=" + AppConfig.API_KEY_ID + ",api_key=" + AppConfig.getApiKey(timeStamp) + ",timestamp=" + timeStamp);
        con.setRequestProperty("request_id", timeStamp); //-- any unique id - unique per request.

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }
}