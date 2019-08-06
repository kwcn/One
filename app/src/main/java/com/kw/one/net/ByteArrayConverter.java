package com.kw.one.net;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Kang Wei
 * @date 2019/7/20
 */
public class ByteArrayConverter {
    public static String ToString(@NonNull byte[] bytes) {
        return ToString(bytes, StandardCharsets.UTF_8);
    }

    public static String ToString(@NonNull byte[] bytes, @NonNull Charset encoding) {
        return new String(bytes, encoding);
    }

    public static byte[] StringTo(@NonNull String data, @NonNull Charset encoding) {
        return data.getBytes(encoding);
    }

    public static byte[] MapTo(@NonNull Map<String, String> params) {
        return MapTo(params, StandardCharsets.UTF_8);
    }

    public static byte[] MapTo(@NonNull Map<String, String> params, @NonNull Charset encoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), String.valueOf(encoding)));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), String.valueOf(encoding)));
                encodedParams.append('&');
            }
            return StringTo(encodedParams.toString(), encoding);
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
