package com.jiangdg.poidemos.bean;

import java.util.List;

/** word文档内容实体
 *
 * Created by jiangdongguo on 2017/10/26.
 */

public class WordParagraphBean {
    private List<WordCharRunBean> charList;

    public List<WordCharRunBean> getCharList() {
        return charList;
    }

    public void setCharList(List<WordCharRunBean> charList) {
        this.charList = charList;
    }
}
