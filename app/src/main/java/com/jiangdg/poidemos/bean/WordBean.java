package com.jiangdg.poidemos.bean;

import java.util.List;

/** 实体父类
 *
 * Created by jiangdongguo on 2017/10/26.
 */

public class WordBean {
    private WordPropertyBean propertyBean;
    private WordContentBean contentBean;
    private List<WordBMarkBean> markList;
    private List<WordCharRunBean> charList;
    private List<WordTableBean> tableList;

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

    public WordContentBean getContentBean() {
        return contentBean;
    }

    public void setContentBean(WordContentBean contentBean) {
        this.contentBean = contentBean;
    }

    public List<WordCharRunBean> getCharList() {
        return charList;
    }

    public void setCharList(List<WordCharRunBean> charList) {
        this.charList = charList;
    }

    public List<WordTableBean> getTableList() {
        return tableList;
    }

    public void setTableList(List<WordTableBean> tableList) {
        this.tableList = tableList;
    }
}
