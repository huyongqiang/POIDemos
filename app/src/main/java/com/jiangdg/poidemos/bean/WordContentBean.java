package com.jiangdg.poidemos.bean;

/** word文档内容实体
 *
 * Created by jiangdongguo on 2017/10/26.
 */

public class WordContentBean {
    // 文档的所有内容
    private String wordText;
    // 文档所有段落的内容
    private String[] paragraphTexts;
    // 页眉内容
    private String headerText;
    // 页脚内容
    private String footerText;

    public String getWordText() {
        return wordText;
    }

    public void setWordText(String wordText) {
        this.wordText = wordText;
    }

    public String[] getParagraphTexts() {
        return paragraphTexts;
    }

    public void setParagraphTexts(String[] paragraphTexts) {
        this.paragraphTexts = paragraphTexts;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public String getFooterText() {
        return footerText;
    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
    }
}
