package com.jiangdg.poidemos.bean;

/** 使用WordExtractor读取word文档的相关属性
 *
 * Created by jiangdongguo on 2017/10/26.
 */

public class WordPropertyBean {
    // 作者
    private String author;
    // 字符统计
    private int charCount;
    // 单词统计
    private int wordConnt;
    // 页数
    private int pageCount;
    // 标题
    private String title;
    // 主题
    private String subject;
    // 分类
    private String category;
    // 公司
    private String company;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCharCount() {
        return charCount;
    }

    public void setCharCount(int charCount) {
        this.charCount = charCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWordConnt() {
        return wordConnt;
    }

    public void setWordConnt(int wordConnt) {
        this.wordConnt = wordConnt;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
