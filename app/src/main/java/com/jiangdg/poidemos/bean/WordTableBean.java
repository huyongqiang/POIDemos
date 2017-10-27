package com.jiangdg.poidemos.bean;

/** 一个表格实体,通常一个表格占一段
 *
 * Created by jiangdongguo on 2017/10/27.
 */

public class WordTableBean {
    private int whichParagraph;
    private int whichTableOfParagraph;

    public int getWhichParagraph() {
        return whichParagraph;
    }

    public void setWhichParagraph(int whichParagraph) {
        this.whichParagraph = whichParagraph;
    }

    public int getWhichTableOfParagraph() {
        return whichTableOfParagraph;
    }

    public void setWhichTableOfParagraph(int whichTableOfParagraph) {
        this.whichTableOfParagraph = whichTableOfParagraph;
    }
}
