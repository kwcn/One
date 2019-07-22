package com.kw.one.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.kw.one.net.Request.POST;

/**
 * @author Kang Wei
 * @date 2019/7/20
 */
public class BasicNetwork implements NetWork {
    @Override
    public Response performRequest(Request request) {
        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) new URL(request.mUrl).openConnection();
            urlConnection.setReadTimeout(request.getTimeoutMs());
            urlConnection.setConnectTimeout(request.getTimeoutMs());
            urlConnection.setDoInput(true);
            // 没有实现缓存的操作，暂时支持http的缓存
            urlConnection.setUseCaches(true);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }
        try {
            urlConnection.setRequestMethod(request.mMethod);
            if (request.mMethod.equals(POST)) {
                urlConnection.setDoOutput(true);
                urlConnection.setChunkedStreamingMode(0);
                try (BufferedOutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream())) {
                    outputStream.write(request.mRequestBody);
                }
            }
            int responseCode = urlConnection.getResponseCode();
            if (responseCode >= 200 && responseCode < 300) {
                // getInputStream会开启连接请求网络数据，所以不用调用connect
                try (BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                    byte[] buffer = new byte[1024];
                    int count;
                    while ((count = inputStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, count);
                    }
                    return Response.success(byteArrayOutputStream.toByteArray());
                }
            } else {
                return Response.error("error responseCode:" + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        } finally {
            urlConnection.disconnect();
        }
    }
}
