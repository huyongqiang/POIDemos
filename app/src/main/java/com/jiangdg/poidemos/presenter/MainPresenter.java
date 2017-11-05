package com.jiangdg.poidemos.presenter;

import android.os.Environment;

import com.jiangdg.poidemos.bean.word.WordCharRunBean;
import com.jiangdg.poidemos.model.IMainModel;
import com.jiangdg.poidemos.model.MainModelImpl;
import com.jiangdg.poidemos.view.IMainView;
import com.jiangdg.poidemos.view.PoiMainActivity;

import java.io.File;
import java.util.List;

/** P层
 *
 * Created by jiangdongguo on 2017/11/2.
 */

public class MainPresenter {
    private IMainView mMainView;
    private IMainModel mMainModel;

    public MainPresenter(PoiMainActivity mView) {
        mMainView = mView;
        mMainModel = new MainModelImpl();
    }

    public void fetchData() {
        if(mMainModel == null || mMainView == null)
            return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                mMainModel.getAllCharRuns(mMainView.getDocumentPath(), new IMainModel.OnParseResultListener() {
                    @Override
                    public void onParseResult(List<WordCharRunBean> result) {
                        if(result==null || result.size()==0) {
                            mMainView.showShortToast("解析失败，判断是否文件为空");
                            return;
                        }
                        // 将解析结果传递给V层显示
                        mMainView.onParseResult(result);
                    }
                });
            }
        }).start();
    }
}
