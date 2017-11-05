package com.jiangdg.poidemos.bean.word;

import java.util.List;

/** word文档内容实体
 *
 * Created by jiangdongguo on 2017/10/26.
 */

public class WordParagraphBean {
    private List<WordCharRunBean2> charList;
    private boolean isTable;

    public List<WordCharRunBean2> getCharList() {
        return charList;
    }

    public void setCharList(List<WordCharRunBean2> charList) {
        this.charList = charList;
    }

    public boolean isTable() {
        return isTable;
    }

    public void setTable(boolean table) {
        isTable = table;
    }
}
