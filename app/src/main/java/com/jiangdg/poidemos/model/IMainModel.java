package com.jiangdg.poidemos.model;

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
        void onParseResult(List<WordCharRunBean> result);
    }

    List<WordReferenceBean> getWordReference(String wordPath);

    void getAllCharRuns(String wordPath, MainModelImpl.OnParseResultListener listener);
}
