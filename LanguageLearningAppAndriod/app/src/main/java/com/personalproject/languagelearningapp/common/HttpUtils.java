package com.personalproject.languagelearningapp.common;

import android.util.Log;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    private static OkHttpClient httpClient;
    public static final Gson gson = new Gson();
    private static final String host = "http://10.0.2.2:8080";

    public static String getRequest(String url){
        if (httpClient == null) {
            httpClient = new OkHttpClient();
        }
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .get()
                    .url(host + url)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static <T> String postRequest(String url, T data){
        if (httpClient == null) {
            httpClient = new OkHttpClient();
        }
        try{
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), gson.toJson(data));
            Log.d("Request Body", requestBody.toString());
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .post(requestBody)
                    .url(host + url)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

