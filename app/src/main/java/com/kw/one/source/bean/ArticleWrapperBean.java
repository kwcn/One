package com.kw.one.source.bean;

import java.util.List;

/**
 * @author Kang Wei
 * @date 2019/11/14
 */
public class ArticleWrapperBean {

    public DataBean data;
    public int errorCode;
    public String errorMsg;

    public static class DataBean {
        public int curPage;
        public int offset;
        public boolean over;
        public int pageCount;
        public int size;
        public int total;
        public List<ArticleBean> datas;

        public static class ArticleBean {
            public String apkLink;
            public int audit;
            public String author;
            public int chapterId;
            public String chapterName;
            public boolean collect;
            public int courseId;
            public String desc;
            public String envelopePic;
            public boolean fresh;
            public int id;
            public String link;
            public String niceDate;
            public String niceShareDate;
            public String origin;
            public String prefix;
            public String projectLink;
            public long publishTime;
            public int selfVisible;
            public long shareDate;
            public String shareUser;
            public int superChapterId;
            public String superChapterName;
            public String title;
            public int type;
            public int userId;
            public int visible;
            public int zan;
            public List<?> tags;
        }
    }
}
