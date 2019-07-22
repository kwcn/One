package com.kw.one.net;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringDef;

/**
 * @author Kang Wei
 * @date 2019/7/20
 */
public class Request {
    public static final int DEFAULT_TIMEOUT_MS = 2500;

    public static final String GET = "GET";
    public static final String POST = "POST";
    @Method
    String mMethod;
    String mUrl;
    byte[] mRequestBody;
    int mTimeoutMs;
    public Request(@Method String method, @NonNull String url, @Nullable byte[] requestBody) {
        mMethod = method;
        mUrl = url;
        mRequestBody = requestBody;
        mTimeoutMs = DEFAULT_TIMEOUT_MS;
    }

    public Request(@Method String method, @NonNull String url) {
        this(method, url, null);
    }

    public int getTimeoutMs() {
        return mTimeoutMs;
    }

    public void setTimeoutMs(int timeoutMs) {
        mTimeoutMs = timeoutMs;
    }

    @StringDef({GET, POST})
    public @interface Method {
    }
}
