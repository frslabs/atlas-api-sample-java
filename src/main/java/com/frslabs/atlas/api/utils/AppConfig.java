package com.frslabs.atlas.api.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PK
 */
public class AppConfig {

    public static String API_BASE_URL = "https://api.atlaskyc.com/";
    public static String API_KEY_ID = "***";
    public static String API_KEY_SECRET = "***";
    public static String PATH_IMAGE_SAVE_LOCATION = "/home/";

    private static SimpleDateFormat sdfClientSecretFormat = new SimpleDateFormat("ddMMyyyyHHmmss");

    public static String getApiKey(String timeStamp) {
        String serverSecret = API_KEY_ID+ "|" + API_KEY_SECRET + "|"  + timeStamp;

        String encryptedServerSecret = null;
        try {
            encryptedServerSecret = EncryptionUtils.encrypt(serverSecret, API_KEY_SECRET);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encryptedServerSecret;
    }

}
