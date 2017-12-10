package com.jiangdg.poidemos.model;

import com.jiangdg.poidemos.bean.word.ParseResultBean;
import com.jiangdg.poidemos.bean.word.WordCharRunBean;
import com.jiangdg.poidemos.bean.word.WordReferenceBean;

import java.util.List;

/**
 * M层父接口
 * <p>
 * Created by jiangdongguo on 2017/11/2.
 */

public interface IMainModel {
    // 对外回调结果
    public interface OnParseResultListener {
        void onParseResult(ParseResultBean result);
    }

    void getWordReference(String wordPath, MainModelImpl.OnParseResultListener listener);
}
