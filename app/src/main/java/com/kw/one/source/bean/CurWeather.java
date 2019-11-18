package com.kw.one.source.bean;

/**
 * @author Kang Wei
 * @date 2019/7/23
 */
public class CurWeather {
    public DataBean data;

    public static class DataBean {
        public String address;
        public String cityCode;
        public String temp;
        public String weather;
        public String windDirection;
        public String windPower;
        public String humidity;
        public String reportTime;
    }
}
