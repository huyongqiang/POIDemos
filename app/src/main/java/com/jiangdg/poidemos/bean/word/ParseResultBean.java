package com.jiangdg.poidemos.bean.word;

import android.text.SpannableString;

import java.util.List;

/** 解析文档结果
 * Created by jiangdongguo on 2017/12/8.
 */

public class ParseResultBean {
    private List<SpannableString> spanStrings;

    public List<SpannableString> getSpanStrings() {
        return spanStrings;
    }

    public void setSpanStrings(List<SpannableString> spanStrings) {
        this.spanStrings = spanStrings;
    }
}
