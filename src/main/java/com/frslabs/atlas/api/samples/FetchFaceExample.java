package com.frslabs.atlas.api.samples;

import com.frslabs.atlas.api.utils.AppConfig;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by PKarthik on 7/10/2017.
 */
public class FetchFaceExample {

    private final String USER_AGENT = "Mozilla/5.0";
    private SimpleDateFormat sdfClientSecretFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
    private String FACE_ID = "359919838_1";

    public static void main(String args[]) {

        try {
            new FetchFaceExample().sendGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendGet() throws Exception {

        String url = AppConfig.API_BASE_URL + "/face/fetch";
        String timeStamp = sdfClientSecretFormat.format(new Date());

        String params = "face_id=***"; //-- enrolled face id

        URL obj = new URL(url + "?" + params);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Authorization", "key_id=" + AppConfig.API_KEY_ID + ",api_key=" + AppConfig.getApiKey(timeStamp) + ",timestamp=" + timeStamp);
        con.setRequestProperty("request_id", timeStamp); //-- any unique id - unique per request.

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        saveImageToDisk(con.getInputStream());

    }

    private void saveImageToDisk(InputStream isr) {

        File file = new File(AppConfig.PATH_IMAGE_SAVE_LOCATION + "/" + FACE_ID + "_" + UUID.randomUUID() + ".jpg");

        try {
            FileUtils.copyInputStreamToFile(isr, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("==> image saved to " + file.getAbsolutePath());

    }

}