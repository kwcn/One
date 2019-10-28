package com.kw.one.net;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;

import java.util.Map;

import javax.net.ssl.SSLContext;

/**
 * @author Kang Wei
 * @date 2019/7/20
 */
public class Request {
    public static final int DEFAULT_TIMEOUT_MS = 2500;

    public static final String GET = "GET";
    public static final String POST = "POST";

    String mMethod;
    String mUrl;
    byte[] mRequestBody;
    int mTimeoutMs;
    SSLContext mSSLContext;
    Map<String, String> mHeader;

    private Request(String method, String url, byte[] requestBody, int timeoutMs, SSLContext SSLContext, Map<String, String> header) {
        if (TextUtils.isEmpty(method) || TextUtils.isEmpty(url)) {
            throw new NullPointerException("must set no null method and url");
        }
        if (method.equals(POST) && requestBody == null) {
            throw new NullPointerException("must set requestBody when use post getRequest");
        }
        mMethod = method;
        mUrl = url;
        mRequestBody = requestBody;
        mHeader = header;
        if (timeoutMs == 0) {
            mTimeoutMs = DEFAULT_TIMEOUT_MS;
        }
        if (mSSLContext == null && url.substring(0, 5).equals("https")) {
            mSSLContext = getDefaultSLLContext();
        }
    }

    private SSLContext getDefaultSLLContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslContext;
    }

    public static class Builder {
        private String mMethod;
        private String mUrl;
        private byte[] mRequestBody;
        private Map<String, String> mHeader;
        private int mTimeoutMs;
        private SSLContext mSSLContext;

        public Builder setMethod(@Method String method) {
            mMethod = method;
            return this;
        }

        public Builder setUrl(@NonNull String url) {
            mUrl = url;
            return this;
        }

        public Builder setRequestBody(@NonNull byte[] requestBody) {
            mRequestBody = requestBody;
            return this;
        }

        public Builder setHeader(@NonNull Map<String, String> header) {
            mHeader = header;
            return this;
        }

        public Builder setTimeoutMs(int timeoutMs) {
            mTimeoutMs = timeoutMs;
            return this;
        }

        public Builder setSSLContext(@NonNull SSLContext SSLContext) {
            mSSLContext = SSLContext;
            return this;
        }

        public Request build() {
            return new Request(mMethod, mUrl, mRequestBody, mTimeoutMs, mSSLContext, mHeader);
        }
    }

    @StringDef({GET, POST})
    public @interface Method {
    }
}
