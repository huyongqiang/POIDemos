package com.jiangdg.poidemos.bean.word;

import java.util.List;

/** 实体父类
 *
 * Created by jiangdongguo on 2017/10/26.
 */

public class WordBean {
    private List<WordParagraphBean> contentList;
    private String htmlPath;

    public String getHtmlPath() {
        return htmlPath;
    }

    public void setHtmlPath(String htmlPath) {
        this.htmlPath = htmlPath;
    }

    public List<WordParagraphBean> getParagraphList() {
        return contentList;
    }

    public void setParagraphList(List<WordParagraphBean> contentList) {
        this.contentList = contentList;
    }
}
