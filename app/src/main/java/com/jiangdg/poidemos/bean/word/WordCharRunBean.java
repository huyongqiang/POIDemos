package com.jiangdg.poidemos.bean.word;

/** 具有相同属性的一段内容
 *
 * Created by jianddongguo on 2017/11/5.
 */

public class WordCharRunBean {
    private String text;      // 文本内容
    private boolean isTable ; // 表格
    private int textSize ; // 文字大小
    private String textColor; // 文字颜色
    private boolean isCenter; // 居中对齐
    private boolean isRight; // 靠右对齐
    private boolean isItalic; // 斜体
    private boolean isUnderline; // 下划线
    private boolean isBold; // 加粗
    private boolean isRegion; // 在那个区域中

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isTable() {
        return isTable;
    }

    public void setTable(boolean table) {
        isTable = table;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public boolean isCenter() {
        return isCenter;
    }

    public void setCenter(boolean center) {
        isCenter = center;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public boolean isItalic() {
        return isItalic;
    }

    public void setItalic(boolean italic) {
        isItalic = italic;
    }

    public boolean isUnderline() {
        return isUnderline;
    }

    public void setUnderline(boolean underline) {
        isUnderline = underline;
    }

    public boolean isBold() {
        return isBold;
    }

    public void setBold(boolean bold) {
        isBold = bold;
    }

    public boolean isRegion() {
        return isRegion;
    }

    public void setRegion(boolean region) {
        isRegion = region;
    }
}
