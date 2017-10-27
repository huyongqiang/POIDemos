package com.jiangdg.poidemos;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.jiangdg.poidemos.bean.WordBean;
import com.jiangdg.poidemos.bean.WordCharRunBean;
import com.jiangdg.poidemos.utils.WordReadUtil;
import com.jiangdg.poidemos.utils.WordReadUtil2;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** 笔记：
 *  XWPFWordExtractor
 *  XWPFDocument
 * */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_read_word)
    Button mBtnReadDoc;
    @BindView(R.id.webview_display_result)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_read_word})
    public void onViewClick(View view){
        int vId = view.getId();
        switch (vId) {
            case R.id.btn_read_word:
//                WordReadUtil2 wu = new WordReadUtil2(path);
//                mWebView.loadUrl("file:///" + wu.htmlPath);
                 WordReadUtil wordReadUtil = new WordReadUtil();
                 String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator
                                +"test.doc";
                 WordBean wordBean = wordReadUtil.readDocDocument(path);
                 List<WordCharRunBean> beanList = wordBean.getCharList();
                 if(beanList == null || beanList.size()==0){
                     Log.i("dd","解析doc失败");
                     return;
                 }
                int size = beanList.size();
                Log.i("dd","大小："+size);
                for (WordCharRunBean bean:beanList) {
                    Log.i("dd","内容："+bean.getText()+"\n");
                }
                break;
            default:
                break;
        }
    }
}
