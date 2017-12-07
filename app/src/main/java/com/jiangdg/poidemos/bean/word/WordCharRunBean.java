package com.jiangdg.poidemos.bean.word;

/** 具有相同属性的一段内容
 *
 * Created by jianddongguo on 2017/11/5.
 */

public class WordCharRunBean {
    private String text;      // 文本内容
    private byte[] boldBegin;
    private byte[] boldEnd;
    private byte[] italicBegin;
    private byte[] italicEnd;
    private byte[] underlineBegin;
    private byte[] underlineEnd;
//    private boolean isTable ; // 表格
//    private int textSize ; // 文字大小
//    private String textColor; // 文字颜色
//    private boolean isCenter; // 居中对齐
//    private boolean isRight; // 靠右对齐
//    private boolean isItalic; // 斜体
//    private boolean isUnderline; // 下划线
//    private boolean isBold; // 加粗
//    private boolean isRegion; // 在那个区域中

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getBoldBegin() {
        return boldBegin;
    }

    public void setBoldBegin(byte[] boldBegin) {
        this.boldBegin = boldBegin;
    }

    public byte[] getBoldEnd() {
        return boldEnd;
    }

    public void setBoldEnd(byte[] boldEnd) {
        this.boldEnd = boldEnd;
    }

    public byte[] getItalicBegin() {
        return italicBegin;
    }

    public void setItalicBegin(byte[] italicBegin) {
        this.italicBegin = italicBegin;
    }

    public byte[] getItalicEnd() {
        return italicEnd;
    }

    public void setItalicEnd(byte[] italicEnd) {
        this.italicEnd = italicEnd;
    }

    public byte[] getUnderlineBegin() {
        return underlineBegin;
    }

    public void setUnderlineBegin(byte[] underlineBegin) {
        this.underlineBegin = underlineBegin;
    }

    public byte[] getUnderlineEnd() {
        return underlineEnd;
    }

    public void setUnderlineEnd(byte[] underlineEnd) {
        this.underlineEnd = underlineEnd;
    }
}
