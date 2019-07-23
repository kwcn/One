package com.kw.one.net;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author Kang Wei
 * @date 2019/7/20
 */
public class ByteArrayConverter {
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

    public static String ToString(@NonNull byte[] bytes) {
        return new String(bytes);
    }

    public static byte[] StringTo(@NonNull String data, @NonNull String encoding) {
        try {
            return data.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not supported: " + encoding, e);
        }
    }

    public static byte[] MapTo(@NonNull Map<String, String> params) {
        return MapTo(params, DEFAULT_PARAMS_ENCODING);
    }

    public static byte[] MapTo(@NonNull Map<String, String> params, @NonNull String encoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), encoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), encoding));
                encodedParams.append('&');
            }
            return encodedParams.toString().getBytes(encoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + encoding, uee);
        }
    }

    public static <T> T ToObject(@NonNull byte[] bytes, @NonNull Class<T> t) {
        String s = ToString(bytes);
        Gson gson = new Gson();
        return gson.fromJson(s, t);
    }
}
