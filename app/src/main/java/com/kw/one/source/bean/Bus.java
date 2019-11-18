package com.kw.one.source.bean;

import java.util.List;

/**
 * @author Kang Wei
 * @date 2019/8/6
 */
public class Bus {

    public JsonrBean jsonr;

    public static class JsonrBean {
        public DataBean data;
        public Object errmsg;
        public String status;
        public Object sversion;

        public static class DataBean {
            public int targetOrder;
            public LineBean line;
            public int depTable;
            public int isStnAdsExpand;
            public int busDisMaxCount;
            public int notify;
            public int isFlowExpand;
            public String toast;
            public int feed;
            public String preArrivalTime;
            public String depDesc;
            public int fav;
            public TipBean tip;
            public int isAdsExpand;
            public int traffic;
            public List<?> roads;
            public List<StationsBean> stations;
            public List<BusesBean> buses;
            public List<OtherlinesBean> otherlines;

            public static class LineBean {
                public String assistDesc;
                public String desc;
                public int direction;
                public String endSn;
                public String firstTime;
                public String lastTime;
                public String lineId;
                public String lineNo;
                public String name;
                public String price;
                public String priceExpansion;
                public String shortDesc;
                public String sortPolicy;
                public String startSn;
                public int state;
                public int stationsNum;
                public Object stopDistances;
                public int type;
                public String version;
            }

            public static class TipBean {
                public String desc;
                public int destType;
            }

            public static class StationsBean {
                public int distanceToSp;
                public double lat;
                public double lng;
                public int order;
                public String sId;
                public String sn;
                public List<?> metros;
            }

            public static class BusesBean {
                public int acBus;
                public String activityLink;
                public String assistDesc;
                public int beforeBaseIndex;
                public double beforeLat;
                public double beforeLng;
                public int busBaseIndex;
                public String busDesc;
                public String busId;
                public int datasource;
                public int delay;
                public int display;
                public int distanceToSc;
                public int distanceToWaitStn;
                public double lat;
                public String licence;
                public String link;
                public double lng;
                public int mTicket;
                public int order;
                public int rType;
                public String shareId;
                public double speed;
                public int state;
                public int syncTime;
                public String userName;
                public String userPhoto;
                public List<?> busDescList;
                public List<TravelsBean> travels;

                public static class TravelsBean {
                    public long arrivalTime;
                    public int debusCost;
                    public int debusTime;
                    public int distance;
                    public long optArrivalTime;
                    public int optimisticTime;
                    public int order;
                    public double pRate;
                    public String recommTip;
                    public int travelTime;
                }
            }

            public static class OtherlinesBean {
                public String endSn;
                public String firstTime;
                public String lastTime;
                public String lineId;
                public String price;
                public String startSn;
            }
        }
    }
}
