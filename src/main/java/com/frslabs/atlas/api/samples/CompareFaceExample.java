package com.frslabs.atlas.api.samples;

import com.frslabs.atlas.api.utils.AppConfig;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PK
 */
public class CompareFaceExample {

    private final String USER_AGENT = "Mozilla/5.0";
    SimpleDateFormat sdfClientSecretFormat = new SimpleDateFormat("ddMMyyyyHHmmss");

    public  static void main(String args[]) {

        try {
            new CompareFaceExample().sendPost();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendPost() throws Exception {

        String charset = "UTF-8";
        String requestURL = AppConfig.API_BASE_URL + "/face/compare";
        String timeStamp = sdfClientSecretFormat.format(new Date());
        CloseableHttpClient httpclient = null;
        String strApiResponse = "";

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);

        String params = "face_id=00b85b02_477a300c-5290-4d25-ad4b-e523327d174b"; //-- enrolled face id
        HttpPost post = new HttpPost(requestURL + "?" + params);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        File faceImage = new File("D:\\Temp\\temp\\00b85b02_23ac0b4a-3516-4461-b995-59642f58aee2.jpg");

        builder.addBinaryBody("face_image_1", faceImage, ContentType.parse("image/jpeg"), faceImage.getName());
        builder.addBinaryBody("face_image_2", faceImage, ContentType.parse("image/jpeg"), faceImage.getName());

        builder.setBoundary("---Content Boundary");

        httpclient = HttpClients.custom().setConnectionManager(cm).build();

        HttpEntity entity = builder.build();
        post.addHeader("Authorization", "key_id="+AppConfig.API_KEY_ID+",api_key="+AppConfig.getApiKey(timeStamp)+",timestamp=" + timeStamp);
        post.addHeader("request_id", timeStamp); //-- any unique id - unique per request.
        post.setEntity(entity);

        CloseableHttpResponse response = httpclient.execute(post);
        try {
            if (response != null) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    // Read the response string if required
                    InputStream responseStream = responseEntity.getContent();
                    if (responseStream != null) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(responseStream));
                        String responseLine = br.readLine();
                        String tempResponseString = "";
                        while (responseLine != null) {
                            tempResponseString = tempResponseString + responseLine + System.getProperty("line.separator");
                            responseLine = br.readLine();
                        }
                        br.close();
                        if (tempResponseString.length() > 0) {
                            strApiResponse = tempResponseString;
                            System.out.println(tempResponseString);
                        }
                    }
                    responseStream.close();
                }
            }
        } finally {
            response.close();
        }

        System.out.println("==> strApiResponse: ");
        System.out.println(strApiResponse);
    }

}