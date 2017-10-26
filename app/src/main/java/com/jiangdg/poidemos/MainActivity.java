package com.jiangdg.poidemos;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.jiangdg.poidemos.utils.WordReadUtil2;

import java.io.File;

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
                String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator
                        +"主流APP保活分析.docx";

                WordReadUtil2 wu = new WordReadUtil2(path);
                mWebView.loadUrl("file:///" + wu.htmlPath);
                break;
            default:
                break;
        }
    }
}
