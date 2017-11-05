package com.jiangdg.poidemos.view;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.jiangdg.poidemos.R;
import com.jiangdg.poidemos.bean.word.WordCharRunBean;
import com.jiangdg.poidemos.presenter.MainPresenter;
import com.jiangdg.poidemos.utils.WordReadUtil;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 笔记：
 * XWPFWordExtractor
 * XWPFDocument
 */
public class PoiMainActivity extends AppCompatActivity implements IMainView {
    @BindView(R.id.btn_read_word)
    Button mBtnReadDoc;
    @BindView(R.id.webview_display_result)
    WebView mWebView;
    private MainPresenter mPresenter;
    String wordPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + "test.docx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mPresenter = new MainPresenter(this);
    }

    @OnClick({R.id.btn_read_word})
    public void onViewClick(View view) {
        int vId = view.getId();
        switch (vId) {
            case R.id.btn_read_word:
                if (mPresenter != null) {
                    mPresenter.fetchData();
                }
//                WordReadUtil wordReadUtil = new WordReadUtil();
//                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
//                        + "test.docx";
//                WordBean wordBean = wordReadUtil.readDoc2Html(path);
//                mWebView.loadUrl("file:///" +WordReadUtil.path);

//                WordBean wordBean = wordReadUtil.readDocDocument(path);
//                List<WordParagraphBean> paragraphList = wordBean.getParagraphList();
//                if (paragraphList == null || paragraphList.size() == 0) {
//                    Log.i("dd", "解析doc失败");
//                    return;
//                }
//
//                int paragraphSize = paragraphList.size();
//                StringBuilder sb = new StringBuilder();
//                for (int i = 0; i < paragraphSize; i++) {
//                    List<WordCharRunBean> charList = paragraphList.get(i).getCharList();
//                    for(WordCharRunBean bean: charList){
//                        if(bean.getText() != null) {
//                            if(bean.getText() == "\r") {
//                                sb.append("\n");
//                            } else {
//                                sb.append(bean.getText());
//                            }
//                        }
//                    }
//
//                }
//                FileOutputStream fos = null;
//                try {
//                    fos = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsoluteFile()+File.separator+"123.txt");
//                    fos.write(sb.toString().getBytes());
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }finally {
//                    if(fos != null){
//                        try {
//                            fos.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                Log.i("ddd",sb.toString());
                break;
            default:
                break;
        }
    }

    @Override
    public String getDocumentPath() {
        return wordPath;
    }

    @Override
    public void onParseResult(List<WordCharRunBean> results) {
        StringBuilder sb = new StringBuilder();
        for (WordCharRunBean bean : results) {
            if (bean == null || bean.getText() == null)
                continue;
            String text = bean.getText();
            // 一条文献开始
            if (isReferenceTag(text)) {
                sb.append("\n");
            }
            sb.append(text);
        }
        Log.d("tag", sb.toString());
    }

    @Override
    public void showShortToast(String msg) {
        Toast.makeText(PoiMainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    // 判断文献开始，标志[num]
    private boolean isReferenceTag(String tag) {
        char[] content = tag.toCharArray();
        // 字符数组长度
        int len = content.length;
        int start=0,end=0;
        // 遍历整个字符数组，寻找'['和']'所在位置
        for(int i=0; i<len; i++) {
            if(content[i] =='[') {
                start = i;
            }
            if(content[i] == ']') {
                end = i;
                // 已确定标记，退出循环
                break;
            }
        }
        // 截取中间字符串
        // 使用正则表达式，判断是否为数字
        if(content[start]=='[' && content[end]==']') {
            String mid = tag.substring(start+1,end);
            if("".equals(mid))
                return false;
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(mid);
            if(isNum.matches()) {
               return true;
            }
        }
        return false;
    }
}
