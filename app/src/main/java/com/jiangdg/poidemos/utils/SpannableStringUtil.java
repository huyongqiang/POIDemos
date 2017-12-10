package com.jiangdg.poidemos.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;

/**
 * SpannableString工具类
 * <p>
 * Created by jiangdongguo on 2017/12/8.
 */

public class SpannableStringUtil {
    // 只包括起始下标
    public static final int INDEX_START = Spanned.SPAN_INCLUSIVE_EXCLUSIVE;
    // 从起始下标到终了下标，同时包括起始下标和终了下标
    public static final int INDEX_START_END = Spanned.SPAN_INCLUSIVE_INCLUSIVE;
    // 从起始下标到终了下标，但都不包括起始下标和终了下标
    public static final int INDEX_NO_START_END = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
    // 从起始下标到终了下标，包括终了下标
    public static final int INDEX_END = Spanned.SPAN_EXCLUSIVE_INCLUSIVE;

    private static SpannableStringUtil mStringUtil;
    private SpannableString spannableString;


    private void SpannableStringUtil() {
    }

    public static SpannableStringUtil getInstance() {
        if (mStringUtil == null) {
            mStringUtil = new SpannableStringUtil();
        }
        return mStringUtil;
    }

    public void setSpanString(String content) {
        this.spannableString = new SpannableString(content);
    }

    /**
     * 设置文本颜色
     * start 作用文本所在上标位置
     * end   作用文本所在下标位置
     * flags 下标包含规则
     * color 颜色
     */
    public void setFrontColor(int start, int end, int flags, String color) {
        if(TextUtils.isEmpty(color))
            return;
        ForegroundColorSpan colorSpan;
        if("#".equals(color.substring(0,1))) {
            colorSpan = new ForegroundColorSpan(Color.parseColor(color));
        } else {
            String tmp = "#"+color;
            colorSpan = new ForegroundColorSpan(Color.parseColor(tmp));
        }
        spannableString.setSpan(colorSpan, start, end, flags);
    }

    public void setFrontColor(String color) {
        if(spannableString == null)
            return;
        setFrontColor(0, spannableString.length(), SpannableStringUtil.INDEX_START, color);
    }

    /**
     * 设置文本背景颜色
     * start 作用文本所在上标位置
     * end   作用文本所在下标位置
     * flags 下标包含规则
     * color 颜色
     */
    public void setBgColor(int start, int end, int flags, String color) {
        if(TextUtils.isEmpty(color))
            return;
        BackgroundColorSpan colorSpan = new BackgroundColorSpan(Color.parseColor(color));
        spannableString.setSpan(colorSpan, start, end, flags);
    }

    public void setBgColor(String color) {
        if(spannableString == null)
            return;
        setBgColor(0, spannableString.length(), SpannableStringUtil.INDEX_START, color);
    }

    /**
     * 设置文本字体大小
     * start 作用文本所在上标位置
     * end   作用文本所在下标位置
     * flags 下标包含规则
     * frontSize 字体大小
     */
    public void setFrontSize(int start, int end, int flags, float frontSize,boolean isAbsoluteSizeSpan) {
        if(isAbsoluteSizeSpan) {
            // 设置字体大小，参数是绝对值，相当于Word的字体大小
            AbsoluteSizeSpan abSizeSpan = new AbsoluteSizeSpan((int)frontSize);
            spannableString.setSpan(abSizeSpan, start, end, flags);
        } else {
            // 设置字体大小，参数是相对于默认字体大小的倍数 x*proportion
            // proportion>1即放大；proportion<1即放小
            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(frontSize);
            spannableString.setSpan(sizeSpan, start, end, flags);
        }
    }

    public void setFrontSize(float frontSize,boolean isAbsoluteSizeSpan) {
        if(spannableString == null)
            return;
        setFrontSize(0, spannableString.length(), SpannableStringUtil.INDEX_START, frontSize,isAbsoluteSizeSpan);
    }

    /**
     * 设置删除线
     */
    public void setDeleteLine(int start, int end, int flags) {
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan, start, end, flags);
    }

    public void setDeleteLine() {
        if(spannableString == null)
            return;
        setDeleteLine(0, spannableString.length(), SpannableStringUtil.INDEX_START);
    }

    /**
     * 设置下划线
     */
    public void setUnderLine(int start, int end, int flags) {
        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableString.setSpan(underlineSpan, start, end, flags);
    }

    public void setUnderLine() {
        if(spannableString == null)
            return;
        setUnderLine(0, spannableString.length(), SpannableStringUtil.INDEX_START);
    }

    /**
     * 设置上标
     */
    public void setSuperScript(int start, int end, int flags) {
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        spannableString.setSpan(superscriptSpan, start, end, flags);
    }

    public void setSuperScript() {
        setSuperScript(0, spannableString.length(), SpannableStringUtil.INDEX_START);
    }

    /**
     * 设置文字下标
     */
    public void setSubscriptSpan(int start, int end, int flags) {
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        spannableString.setSpan(subscriptSpan, start, end, flags);
    }

    public void setSubscriptSpan() {
        setSubscriptSpan(0, spannableString.length(), SpannableStringUtil.INDEX_START);
    }

    /**
     * 设置文字字体为粗体
     */
    public void setBold(int start, int end, int flags) {
        StyleSpan styleSpanBold = new StyleSpan(Typeface.BOLD);//粗体
        spannableString.setSpan(styleSpanBold, start, end, flags);
    }

    public void setBold() {
        setBold(0, spannableString.length(), SpannableStringUtil.INDEX_START);
    }

    /**
     * 设置文字字体为斜体
     */
    public void setItalic(int start, int end, int flags) {
        StyleSpan styleSpanItalic = new StyleSpan(Typeface.ITALIC);//斜体
        spannableString.setSpan(styleSpanItalic, start, end, flags);
    }

    public void setItalic() {
        setItalic(0, spannableString.length(), SpannableStringUtil.INDEX_START);
    }

    /**
     * 设置文本图片
     */
    public void setImage(int start, int end, int flags, Bitmap bm) {
        ImageSpan imageSpan = new ImageSpan(bm);
        spannableString.setSpan(imageSpan, start, end, flags);
    }

    /**
     * 设置超链接文本
     */
    public void setURL(int start, int end, int flags, String url) {
        URLSpan urlSpan = new URLSpan(url);
        spannableString.setSpan(urlSpan, start, end, flags);
    }

    public void setURL(String url) {
        setURL(0, spannableString.length(), SpannableStringUtil.INDEX_START, url);
    }

    /**
     * 设置文字点击
     */
    public void setTextClick(int start, int end, int flags, ClickableSpan clickableSpan) {
        spannableString.setSpan(clickableSpan, start, end, flags);
    }

    public void setTextClick(ClickableSpan clickableSpan) {
        setTextClick(0, spannableString.length(), SpannableStringUtil.INDEX_START,clickableSpan);
    }

    // 返回带格式的string
    public SpannableString getSpanString() {
        return spannableString;
    }
}
