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
import com.jiangdg.poidemos.bean.WordParagraphBean;
import com.jiangdg.poidemos.utils.WordReadUtil;
import com.jiangdg.poidemos.utils.WordReadUtil2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 笔记：
 * XWPFWordExtractor
 * XWPFDocument
 */
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
    public void onViewClick(View view) {
        int vId = view.getId();
        switch (vId) {
            case R.id.btn_read_word:
//                WordReadUtil2 wu = new WordReadUtil2(path);
//                mWebView.loadUrl("file:///" + wu.htmlPath);
                WordReadUtil wordReadUtil = new WordReadUtil();
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                        + "test.doc";
                WordBean wordBean = wordReadUtil.readDoc2Html(path);

                mWebView.loadUrl("file:///" +WordReadUtil.htmlPath);

                List<WordParagraphBean> paragraphList = wordBean.getParagraphList();
                if (paragraphList == null || paragraphList.size() == 0) {
                    Log.i("dd", "解析doc失败");
                    return;
                }

                int paragraphSize = paragraphList.size();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < paragraphSize; i++) {
                    List<WordCharRunBean> charList = paragraphList.get(i).getCharList();
                    for(WordCharRunBean bean: charList){
                        if(bean.getText() != null) {
                            if(bean.getText() == "\r") {
                                sb.append("\n");
                            } else {
                                sb.append(bean.getText());
                            }
                        }
                    }

                }
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsoluteFile()+File.separator+"123.txt");
                    fos.write(sb.toString().getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(fos != null){
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Log.i("ddd",sb.toString());
                break;
            default:
                break;
        }
    }
}
