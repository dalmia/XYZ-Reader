package com.example.xyzreader.remote;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class RemoteEndpointUtil {
    private static final String TAG = "RemoteEndpointUtil";

    private RemoteEndpointUtil() {
    }

    public static JSONArray fetchJsonArray() {
        String itemsJson;
        try {
            itemsJson = fetchPlainText(Config.BASE_URL);
        } catch (IOException e) {
            Log.e(TAG, "Error fetching items JSON", e);
            return null;
        }

        // Parse JSON
        try {
            JSONTokener tokener = new JSONTokener(itemsJson);
            Object val = tokener.nextValue();
            if (!(val instanceof JSONArray)) {
                throw new JSONException("Expected JSONArray");
            }
            return (JSONArray) val;
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing items JSON", e);
        }

        return null;
    }

    /**
     * @param url - url from which the data is to be fetched
     * @return the response JSON String received
     * @throws IOException
     */
    static String fetchPlainText(URL url) throws IOException {
        return new String(fetch(url), "UTF-8");
    }

    /**
     * fetch the response from the url and convert it to a byte array
     * @param url - url from which the data is to be fetched
     * @return byte array to be converted to a string using the proper charset
     * @throws IOException
     */
    static byte[] fetch(URL url) throws IOException{
        InputStream in = null;
        try {
            OkHttpClient client = new OkHttpClient();
            HttpURLConnection conn = new OkUrlFactory(client).open(url);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            in = conn.getInputStream();
            byte[] buffer = new byte[256];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            return out.toByteArray();
        }finally {
            if(in != null){
                in.close();
            }
        }
    }

}
