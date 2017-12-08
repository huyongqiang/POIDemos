package com.jiangdg.poidemos.view;

import com.jiangdg.poidemos.bean.word.ParseResultBean;
import com.jiangdg.poidemos.bean.word.WordCharRunBean;

import java.util.List;

/** V层父接口
 *
 * Created by jiangdongguo on 2017/11/2.
 */

public interface IMainView {

    // 返回文件路径给P层
    String getDocumentPath();

    // 从P层获取解析结果
//    void onParseResult(List<WordCharRunBean> result);
    void onParseResult(ParseResultBean result);

    // 打印消息
    void showShortToast(String msg);
}
