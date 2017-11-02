package com.jiangdg.poidemos.bean.word;

import java.util.List;

/** 期刊文献信息
 *
 * Created by jiangdongguo on 2017/11/2.
 */

public class WordReferenceBean {
    // 文献序号
    private int orderNum;

    // 一条文献可包含多个相同属性的文本
    private List<WordCharRunBean>  charBeanList;

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public List<WordCharRunBean> getCharBeanList() {
        return charBeanList;
    }

    public void setCharBeanList(List<WordCharRunBean> charBeanList) {
        this.charBeanList = charBeanList;
    }
}
