package com.jiangdg.poidemos.bean;

import java.util.List;

/** 实体父类
 *
 * Created by jiangdongguo on 2017/10/26.
 */

public class WordBean {
    private WordPropertyBean propertyBean;
    private List<WordBMarkBean> markList;
    private List<WordTableBean> tableList;
    private List<WordParagraphBean> contentList;
    private String htmlPath;

    // 文档的所有内容
    private String wordText;
    // 文档所有段落的内容
    private String[] paragraphTexts;
    // 页眉内容
    private String headerText;
    // 页脚内容
    private String footerText;

    // 书签数量
    private int bookMarkCount;

    public int getBookMarkCount() {
        return bookMarkCount;
    }

    public void setBookMarkCount(int bookMarkCount) {
        this.bookMarkCount = bookMarkCount;
    }

    public List<WordBMarkBean> getMarkList() {
        return markList;
    }

    public void setMarkList(List<WordBMarkBean> markList) {
        this.markList = markList;
    }

    public WordPropertyBean getPropertyBean() {
        return propertyBean;
    }

    public void setPropertyBean(WordPropertyBean propertyBean) {
        this.propertyBean = propertyBean;
    }

    public List<WordTableBean> getTableList() {
        return tableList;
    }

    public void setTableList(List<WordTableBean> tableList) {
        this.tableList = tableList;
    }

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
