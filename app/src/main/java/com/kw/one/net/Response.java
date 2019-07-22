package com.kw.one.net;

import androidx.annotation.IntDef;

/**
 * @author Kang Wei
 * @date 2019/7/20
 */
public class Response {
    public static final int SUCCESS = 1;
    public static final int ERROR = 2;

    public byte[] mData;
    @Status
    public int mStatus;
    public String errorMsg;

    private Response(byte[] data, int status, String errorMsg) {
        mData = data;
        mStatus = status;
        this.errorMsg = errorMsg;
    }

    public static Response success(byte[] data) {
        return new Response(data, SUCCESS, null);
    }

    public static Response error(String errorMsg) {
        return new Response(null, ERROR, errorMsg);
    }

    @IntDef({SUCCESS, ERROR})
    public @interface Status {
    }
}
