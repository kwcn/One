package com.kw.one.net;

import androidx.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

import static com.kw.one.net.Request.POST;

/**
 * @author Kang Wei
 * @date 2019/7/20
 */
public class BasicNetwork implements NetWork {
    @Override
    public Response performRequest(Request request) {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(request.mUrl).openConnection();
            urlConnection.setReadTimeout(request.mTimeoutMs);
            urlConnection.setConnectTimeout(request.mTimeoutMs);
            urlConnection.setDoInput(true);
            // 没有实现缓存的操作，暂时支持http的缓存
            urlConnection.setUseCaches(true);

            // 设置ssl
            if (urlConnection instanceof HttpsURLConnection) {
                if (request.mSSLContext != null) {
                    ((HttpsURLConnection) urlConnection).setSSLSocketFactory(request.mSSLContext.getSocketFactory());
                    ((HttpsURLConnection) urlConnection).setHostnameVerifier((hostname, session) -> true);
                }
            }
            // 设置请求头
            if (request.mHeader != null) {
                for (String headerName : request.mHeader.keySet()) {
                    urlConnection.addRequestProperty(headerName, request.mHeader.get(headerName));
                }
            }
            urlConnection.setRequestMethod(request.mMethod);
            // 设置请求body
            if (request.mMethod.equals(POST)) {
                // post请求不能设置缓存
                urlConnection.setUseCaches(false);
                urlConnection.setDoOutput(true);
                urlConnection.setChunkedStreamingMode(0);
                try (BufferedOutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream())) {
                    outputStream.write(request.mRequestBody);
                }
            }

            // 获取响应数据
            int responseCode = urlConnection.getResponseCode();
            if (responseCode >= 200 && responseCode < 300) {
                ByteArrayOutputStream outputStream = acquireDataStream(urlConnection);
                return Response.success(outputStream.toByteArray());
            } else if (responseCode == 301 || responseCode == 302) {
                // 重定向
                request.mUrl = urlConnection.getHeaderField("Location");
                return performRequest(request);
            } else {
                return Response.error("error responseCode:" + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private ByteArrayOutputStream acquireDataStream(@NonNull HttpURLConnection urlConnection) throws IOException {
        InputStream inputStream = null;
        // urlConnection.getInputStream()会开启连接请求网络数据，所以不用调用connect
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            String encodeName = urlConnection.getHeaderField("Content-Encoding");
            if (encodeName != null && encodeName.equals("gzip")) {
                inputStream = new GZIPInputStream(urlConnection.getInputStream());
            } else {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            }
            byte[] buffer = new byte[1024];
            int count;
            while ((count = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, count);
            }
            return outputStream;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
