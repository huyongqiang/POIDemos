package com.jiangdg.poidemos.model;

import com.jiangdg.poidemos.bean.word.WordReferenceBean;

import java.util.List;

/** M层父接口
 *
 * Created by jiangdongguo on 2017/11/2.
 */

public interface IMainModel {
    List<WordReferenceBean> getWordReference(String wordPath);
}
