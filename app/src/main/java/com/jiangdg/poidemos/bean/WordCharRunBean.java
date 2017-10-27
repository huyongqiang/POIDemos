package com.jiangdg.poidemos.bean;

/** 一段具有相同属性内容实体
 *
 * Created by jiangdongguo on 2017/10/27.
 */

public class WordCharRunBean {
    private int whichParagraph;
    private int whichRunOfPara;
    private byte[] spaceBytes;
    private byte[] picBytes;
    private String text;
    private int textFrontSize;
    private String textFrontName;
    private int textColor;
    private byte textHlightedColor;
    private boolean isBold;
    private boolean isItalic;
    private boolean isData;
    private boolean isHighlighted;

    public byte[] getPicBytes() {
        return picBytes;
    }

    public void setPicBytes(byte[] picBytes) {
        this.picBytes = picBytes;
    }

    public byte[] getSpaceBytes() {
        return spaceBytes;
    }

    public void setSpaceBytes(byte[] spaceBytes) {
        this.spaceBytes = spaceBytes;
    }

    public int getWhichParagraph() {
        return whichParagraph;
    }

    public void setWhichParagraph(int whichParagraph) {
        this.whichParagraph = whichParagraph;
    }

    public int getWhichRunOfPara() {
        return whichRunOfPara;
    }

    public void setWhichRunOfPara(int whichRunOfPara) {
        this.whichRunOfPara = whichRunOfPara;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextFrontSize() {
        return textFrontSize;
    }

    public void setTextFrontSize(int textFrontSize) {
        this.textFrontSize = textFrontSize;
    }

    public String getTextFrontName() {
        return textFrontName;
    }

    public void setTextFrontName(String textFrontName) {
        this.textFrontName = textFrontName;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public byte getTextHlightedColor() {
        return textHlightedColor;
    }

    public void setTextHlightedColor(byte textHlightedColor) {
        this.textHlightedColor = textHlightedColor;
    }

    public boolean isBold() {
        return isBold;
    }

    public void setBold(boolean bold) {
        isBold = bold;
    }

    public boolean isItalic() {
        return isItalic;
    }

    public void setItalic(boolean italic) {
        isItalic = italic;
    }

    public boolean isData() {
        return isData;
    }

    public void setData(boolean data) {
        isData = data;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }
}
